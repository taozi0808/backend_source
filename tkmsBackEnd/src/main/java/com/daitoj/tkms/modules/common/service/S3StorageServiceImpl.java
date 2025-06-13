package com.daitoj.tkms.modules.common.service;

import com.daitoj.tkms.config.CloudStorageProperties;
import com.daitoj.tkms.domain.MSystemConfig;
import com.daitoj.tkms.domain.TFifle;
import com.daitoj.tkms.modules.common.constants.SystemConfig;
import com.daitoj.tkms.modules.common.repository.MSystemConfigRepository;
import com.daitoj.tkms.modules.common.repository.TFifleRepository;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

/** AWS S3 ファイルサービス実装 */
@Service("s3StorageService")
@Primary
@ConditionalOnProperty(name = "cloud.type", havingValue = "aws")
public class S3StorageServiceImpl implements CloudStorageService {

  private static final Logger LOG = LoggerFactory.getLogger(S3StorageServiceImpl.class);
  private static final String DEFAULT_S3_PATH = "upload/";

  private final S3Client s3Client;
  private final String bucketName;
  private final String accessKey;
  private final String secretKey;
  private final String regionStatic;
  private final String endpoint;
  private final long expiration;
  private final TFifleRepository tfifleRepository;
  private final MSystemConfigRepository msystemConfigRepository;

  public S3StorageServiceImpl(
      CloudStorageProperties properties,
      TFifleRepository tfifleRepository,
      MSystemConfigRepository msystemConfigRepository) {

    this.bucketName = properties.getBucketName();
    this.accessKey = properties.getAccessKey();
    this.secretKey = properties.getSecretKey();
    this.tfifleRepository = tfifleRepository;
    this.regionStatic = properties.getRegionStatic();
    this.endpoint = properties.getEndpoint();
    this.expiration = properties.getExpiration();
    this.msystemConfigRepository = msystemConfigRepository;

    // 初期化 S3Client（Linode などの S3 互換サーバー用にエンドポイントを設定）
    this.s3Client =
        S3Client.builder()
            .region(Region.of(properties.getRegionStatic()))
            .endpointOverride(URI.create(properties.getEndpoint()))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        properties.getAccessKey(), properties.getSecretKey())))
            .serviceConfiguration(
                S3Configuration.builder()
                    .pathStyleAccessEnabled(properties.isPathStyleAccessEnabled())
                    .build())
            .build();
  }

  /** ファイルアップロード */
  @Override
  public List<UUID> upload(MultipartFile[] files) {
    List<UUID> ret = new ArrayList<>();
    String s3Path = getS3Path();

    for (MultipartFile file : files) {
      if (file.isEmpty()) continue;
      try {
        // ファイルメタデータ保存
        TFifle fileEntity = saveFileMetadata(file, s3Path);
        String objectKey = buildObjectKey(fileEntity);

        // S3 にファイルをアップロード
        uploadToS3(file, objectKey);
        ret.add(fileEntity.getId());
      } catch (Exception e) {
        LOG.error("ファイルアップロード失敗: {}", file.getOriginalFilename(), e);
        throw new SystemException("ファイルアップロード失敗", e);
      }
    }
    return ret;
  }

  /** ファイルダウンロード */
  @Override
  public InputStream download(String fileId) {
    try {
      String objectKey = getObjectNameFromUuid(fileId);
      GetObjectRequest request =
          GetObjectRequest.builder().bucket(bucketName).key(objectKey).build();
      return s3Client.getObject(request);
    } catch (Exception e) {
      LOG.error("ファイルダウンロード失敗: {}", fileId, e);
      throw new SystemException("ファイルダウンロード失敗", e);
    }
  }

  /** ファイル URL 生成（プレサインド URL） */
  @SuppressWarnings("checkstyle:Indentation")
  @Override
  public String createUrl(String fileId) {

      AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
      StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

      S3Presigner presigner = S3Presigner.builder()
          .region(Region.of(regionStatic))
          .credentialsProvider(credentialsProvider)
          .endpointOverride(URI.create(endpoint))
          .build();
      try {
      String objectKey = getObjectNameFromUuid(fileId);
      GetObjectRequest getObjectRequest = GetObjectRequest.builder()
          .bucket(bucketName)
          .key(objectKey)
          .build();

      GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
          .signatureDuration(Duration.ofSeconds(expiration))
          .getObjectRequest(getObjectRequest)
          .build();

      PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
      String presignedUrl = presignedRequest.url().toString();
      return presignedUrl;
      }  catch (Exception e) {
          LOG.error("ファイルURL生成失敗: {}", e.getMessage());
          throw new RuntimeException("creat PresignedGetObjectRequest failure", e);
      } finally {
          if (presigner != null) {
              presigner.close();
          }
      }

  }

  /** ファイル削除 */
  @Override
  public boolean delete(String objectName) {
    try {
      DeleteObjectRequest request =
          DeleteObjectRequest.builder().bucket(bucketName).key(objectName).build();
      s3Client.deleteObject(request);
      return true;
    } catch (Exception e) {
      LOG.error("ファイル削除失敗: {}", objectName, e);
      return false;
    }
  }

  // ==================== 内部メソッド ====================

  private String getS3Path() {
    return msystemConfigRepository
        .findById_SysCdAndId_ConfigKey(SystemConfig.SYS_CD_AWS, SystemConfig.SYS_S3_S3_UPLOAD_LOC)
        .map(MSystemConfig::getConfigValue)
        .orElse(DEFAULT_S3_PATH);
  }

  private TFifle saveFileMetadata(MultipartFile file, String s3Path) {
    TFifle fileEntity = new TFifle();
    fileEntity.setFilePath(s3Path);
    fileEntity.setFileNm(file.getOriginalFilename());
    fileEntity.setFileSize((int) file.getSize());
    fileEntity.setFileExt(FilenameUtils.getExtension(file.getOriginalFilename()));
    return tfifleRepository.save(fileEntity);
  }

  private String buildObjectKey(TFifle fileEntity) {
    return fileEntity.getFilePath() + fileEntity.getId() + "." + fileEntity.getFileExt();
  }

  private void uploadToS3(MultipartFile file, String objectKey) throws IOException {
    try (InputStream inputStream = file.getInputStream()) {
      PutObjectRequest request =
          PutObjectRequest.builder()
              .bucket(bucketName)
              .key(objectKey)
              .contentType(file.getContentType())
              .build();

      s3Client.putObject(request, RequestBody.fromInputStream(inputStream, file.getSize()));

    } catch (Exception e) {
        LOG.error(e.toString(), e);
        throw new IOException("S3 upload file failure", e);
    }
  }

  private String getObjectNameFromUuid(String uuid) throws IOException {
    TFifle fileEntity =
        tfifleRepository
            .findById(UUID.fromString(uuid))
            .orElseThrow(() -> new IOException("ファイルが見つかりません: " + uuid));
    return fileEntity.getFilePath() + fileEntity.getId() + "." + fileEntity.getFileExt();
  }
}
