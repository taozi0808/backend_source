package com.daitoj.tkms.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Minio設定 */
@Configuration
@lombok.Getter
@lombok.Setter
@ConditionalOnProperty(name = "cloud.type", havingValue = "minio")
public class MinioConfig {

  /** エンドポイント */
  @Value("${cloud.region.static}")
  private String endpoint;

  /** アクセスキー */
  @Value("${cloud.credentials.accessKey}")
  private String accessKey;

  /** secretキー */
  @Value("${cloud.credentials.secretKey}")
  private String secretKey;

  /** bucket名 */
  @Value("${cloud.s3.bucket}")
  private String bucketName;

  /** Url期限 */
  @Value("${cloud.url.expiration}")
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
}
