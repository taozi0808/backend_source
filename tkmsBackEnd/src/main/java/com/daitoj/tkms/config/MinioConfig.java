package com.daitoj.tkms.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Minio設定 */
@Configuration
public class MinioConfig {

  @Value("${fileStorage.endpoint}")
  private String endpoint;

  @Value("${fileStorage.accessKey}")
  private String accessKey;

  @Value("${fileStorage.secretKey}")
  private String secretKey;

  @Value("${fileStorage.bucketName}")
  private String bucketName;

  @Value("${fileStorage.accessPath}")
  private String accessPath;

  @Value("${fileStorage.expiration}")
  private Integer expiration;

  /**
   * MinioClient生成
   *
   * @return MinioClient
   */
  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
  }

  /**
   * エンドポイント
   *
   * @return endpoint
   */
  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  /**
   * アクセスキー
   *
   * @return accessKey
   */
  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  /**
   * secretキー
   *
   * @return secretKey
   */
  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  /**
   * bucket名
   *
   * @return bucketName
   */
  public String getBucketName() {
    return bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  /**
   * アクセスパス
   *
   * @return accessPath
   */
  public String getAccessPath() {
    return accessPath;
  }

  public void setAccessPath(String accessPath) {
    this.accessPath = accessPath;
  }

  /**
   * Url期限
   *
   * @return expiration
   */
  public Integer getExpiration() {
    return expiration;
  }

  public void setExpiration(Integer expiration) {
    this.expiration = expiration;
  }
}
