package com.daitoj.tkms.config;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

  private GitProperties gitProperties;
  private BuildProperties buildProperties;

  @Bean
  public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(
      JHipsterProperties jHipsterProperties) {
    MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

    URI redisUri = URI.create(jHipsterProperties.getCache().getRedis().getServer()[0]);

    Config config = new Config();
    // Fix Hibernate lazy initialization https://github.com/jhipster/generator-jhipster/issues/22889
    config.setCodec(new org.redisson.codec.SerializationCodec());
    if (jHipsterProperties.getCache().getRedis().isCluster()) {
      ClusterServersConfig clusterServersConfig =
          config
              .useClusterServers()
              .setMasterConnectionPoolSize(
                  jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
              .setMasterConnectionMinimumIdleSize(
                  jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
              .setSubscriptionConnectionPoolSize(
                  jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
              .addNodeAddress(jHipsterProperties.getCache().getRedis().getServer());

      if (redisUri.getUserInfo() != null) {
        clusterServersConfig.setPassword(
            redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
      }
    } else {
      SingleServerConfig singleServerConfig =
          config
              .useSingleServer()
              .setConnectionPoolSize(
                  jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
              .setConnectionMinimumIdleSize(
                  jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
              .setSubscriptionConnectionPoolSize(
                  jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
              .setAddress(jHipsterProperties.getCache().getRedis().getServer()[0]);

      if (redisUri.getUserInfo() != null) {
        singleServerConfig.setPassword(
            redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
      }
    }
    jcacheConfig.setStatisticsEnabled(true);

    if (jHipsterProperties.getCache().getRedis().getExpiration() != 0) {
        jcacheConfig.setExpiryPolicyFactory(
            CreatedExpiryPolicy.factoryOf(
                new Duration(
                    TimeUnit.SECONDS, jHipsterProperties.getCache().getRedis().getExpiration())));
    }

    return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
  }

  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer(
      javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
    return cm -> {
      // jhipster-needle-redis-add-entry
      // 項目分類コードキー
      createCache(
          cm,
          com.daitoj.tkms.modules.common.repository.MItemListSettingRepository
              .ITEMLIST_BY_CLASS_CACHE,
          jcacheConfiguration);
      // 項目分類コード、項目コードキー
      createCache(
          cm,
          com.daitoj.tkms.modules.common.repository.MItemListSettingRepository
              .ITEMLIST_BY_CLASS_CD_CACHE,
          jcacheConfiguration);
      // システムコードキー
      createCache(
          cm,
          com.daitoj.tkms.modules.common.repository.MSystemConfigRepository
              .SYSTEMCONFIG_BY_SYSCD_CACHE,
          jcacheConfiguration);
      // システムコード、システム設定キー
      createCache(
          cm,
          com.daitoj.tkms.modules.common.repository.MSystemConfigRepository
              .SYSTEMCONFIG_BY_SYSCD_KEY_CACHE,
          jcacheConfiguration);
    };
  }

  private void createCache(
      javax.cache.CacheManager cm,
      String cacheName,
      javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
    javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
    if (cache != null) {
      cache.clear();
    } else {
      cm.createCache(cacheName, jcacheConfiguration);
    }
  }

  @Autowired(required = false)
  public void setGitProperties(GitProperties gitProperties) {
    this.gitProperties = gitProperties;
  }

  @Autowired(required = false)
  public void setBuildProperties(BuildProperties buildProperties) {
    this.buildProperties = buildProperties;
  }

  @Bean
  public KeyGenerator keyGenerator() {
    return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
  }
}
