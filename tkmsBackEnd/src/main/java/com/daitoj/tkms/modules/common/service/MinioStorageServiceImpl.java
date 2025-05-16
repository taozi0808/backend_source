package com.daitoj.tkms.modules.common.service;

import com.daitoj.tkms.config.MinioConfig;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import java.io.InputStream;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** minioファイルサービス */
@Service("minioStorageService")
@Primary
@ConditionalOnProperty(name = "fileStorage.type", havingValue = "minio")
public class MinioStorageServiceImpl implements CloudStorageService {

  private static final Logger LOG = LoggerFactory.getLogger(MinioStorageServiceImpl.class);

  private final MinioConfig minioConfig;

  private final MinioClient minioClient;

  /** コンストラクタ */
  public MinioStorageServiceImpl(MinioConfig minioConfig, MinioClient minioClient) {
    this.minioConfig = minioConfig;
    this.minioClient = minioClient;
  }

  /**
   * ファイルアップロード
   *
   * @param file ファイル
   * @return オブジェクト名
   */
  @Override
  public String upload(MultipartFile file) {

    try {
      // ファイル名
      String fileName = file.getOriginalFilename();
      // ファイルパス
      String filePath = UUID.randomUUID().toString(); // TODO userid + uuid

      try (InputStream inputStream = file.getInputStream()) {
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(filePath + "/" + fileName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        return filePath + "/" + fileName;
      }
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
