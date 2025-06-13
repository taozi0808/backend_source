package com.daitoj.tkms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/** s3設定 */
@Configuration
@lombok.Getter
@lombok.Setter
@ConditionalOnProperty(name = "cloud.type", havingValue = "aws")
public class CloudStorageProperties {

    /** エンドポイント */
    @Value("${cloud.aws.endpoint}")
    private String endpoint;

    /** エンドポイント */
    @Value("${cloud.aws.pathStyleAccessEnabled}")
    private boolean pathStyleAccessEnabled;

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

    /** static */
    @Value("${cloud.region.static}")
    private String regionStatic;

}
