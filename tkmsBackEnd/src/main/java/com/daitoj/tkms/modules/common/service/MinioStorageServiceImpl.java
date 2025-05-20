package com.daitoj.tkms.modules.common.service;

import com.daitoj.tkms.config.MinioConfig;
import com.daitoj.tkms.domain.MSystemConfig;
import com.daitoj.tkms.domain.TFifle;
import com.daitoj.tkms.modules.common.constants.SystemConfig;
import com.daitoj.tkms.modules.common.repository.MSystemConfigRepository;
import com.daitoj.tkms.modules.common.repository.TFifleRepository;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/** minioファイルサービス */
@Service("minioStorageService")
@Primary
@ConditionalOnProperty(name = "cloud.type", havingValue = "minio")
public class MinioStorageServiceImpl implements CloudStorageService {

  private static final Logger LOG = LoggerFactory.getLogger(MinioStorageServiceImpl.class);

  /** Minio設定 */
  private final MinioConfig minioConfig;

  private final MinioClient minioClient;

  /** システム設定情報リポジトリ */
  private final MSystemConfigRepository msystemConfigRepository;

  /** 添付ファイルリポジトリ */
  private final TFifleRepository tfifleRepository;

  private static final String S3_PATH = "upload/";

  /** コンストラクタ */
  public MinioStorageServiceImpl(
      MinioConfig minioConfig,
      MinioClient minioClient,
      TFifleRepository tfifleRepository,
      MSystemConfigRepository msystemConfigRepository) {
    this.minioConfig = minioConfig;
    this.minioClient = minioClient;
    this.tfifleRepository = tfifleRepository;
    this.msystemConfigRepository = msystemConfigRepository;
  }

  /**
   * ファイルアップロード
   *
   * @param files ファイル
   * @return オブジェクト名
   */
  @Override
  @Transactional
  public List<UUID> upload(MultipartFile[] files) {
    // 戻り値
    List<UUID> ret = new ArrayList<>();

    try {
      // S3ファイル格納パス
      Optional<MSystemConfig> config =
          msystemConfigRepository.findById_SysCdAndId_ConfigKey(
              SystemConfig.SYS_CD_S3, SystemConfig.SYS_S3_PATH);

      // ファイルパス
      String filePath = S3_PATH;
      if (config.isPresent()) {
        filePath = config.get().getConfigValue();
      }

      for (MultipartFile file : files) {
        // ファイル名
        String fileName = file.getOriginalFilename();

        // 拡張子
        String ext = FilenameUtils.getExtension(fileName);

        // 添付ファイル情報
        TFifle fileEntity = new TFifle();
        // ファイル保存パス
        fileEntity.setFilePath(filePath);
        // ファイル名：ULファイル対象から取得ファイル名
        fileEntity.setFileNm(fileName);
        // ファイルサイズ：ULファイル対象から取得ファイル名
        fileEntity.setFileSize((int) file.getSize());
        // ファイル拡張子：ULファイル対象から取得ファイル拡張子
        fileEntity.setFileExt(ext);

        TFifle newfileEntity = tfifleRepository.save(fileEntity);

        // ファイルID
        String fileId = newfileEntity.getId().toString();
        // S3ファイル名
        String s3FileName = fileId + "." + ext;

        try (InputStream inputStream = file.getInputStream()) {
          minioClient.putObject(
              PutObjectArgs.builder()
                  .bucket(minioConfig.getBucketName())
                  .object(filePath + s3FileName)
                  .stream(inputStream, file.getSize(), -1)
                  .contentType(file.getContentType())
                  .build());

          ret.add(newfileEntity.getId());
        }
      }

      return ret;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * ファイルをダウンロードする
   *
   * @param objectName オブジェクト名
   * @return ファイル
   */
  @Override
  public InputStream download(String objectName) {
    try {
      return minioClient.getObject(
          GetObjectArgs.builder().bucket(minioConfig.getBucketName()).object(objectName).build());
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * ファイルUrlを取得する
   *
   * @param objectName オブジェクト名
   * @return ファイルUrl
   */
  @Override
  public String createUrl(String objectName) {

    try {
      return minioClient.getPresignedObjectUrl(
          GetPresignedObjectUrlArgs.builder()
              .method(Method.GET)
              .bucket(minioConfig.getBucketName())
              .object(objectName)
              .expiry(minioConfig.getExpiration())
              .build());
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * ファイルを削除する
   *
   * @param objectName オブジェクト名
   * @return 削除結果
   */
  @Override
  public boolean delete(String objectName) {
    // TODO

    return false;
  }
}
