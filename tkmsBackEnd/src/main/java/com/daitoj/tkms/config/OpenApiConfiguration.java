package com.daitoj.tkms.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.apidoc.customizer.JHipsterOpenApiCustomizer;

@Configuration
@Profile(JHipsterConstants.SPRING_PROFILE_API_DOCS)
public class OpenApiConfiguration {

  private static final String MODULES_BASE_PACKAGE = "com.daitoj.tkms.modules";
  private static final String[] API_PACKAGE_PATTERNS = {MODULES_BASE_PACKAGE + ".*.web.rest"};

  @Bean
  @ConditionalOnMissingBean(name = "apiFirstGroupedOpenAPI")
  public GroupedOpenApi apiFirstGroupedOpenAPI(
      JHipsterOpenApiCustomizer jhipsterOpenApiCustomizer, JHipsterProperties jHipsterProperties) {
    JHipsterProperties.ApiDocs properties = jHipsterProperties.getApiDocs();
    return GroupedOpenApi.builder()
        .group("openapi")
        .packagesToScan(API_PACKAGE_PATTERNS)
        .addOpenApiCustomizer(jhipsterOpenApiCustomizer)
        .pathsToMatch(properties.getDefaultIncludePattern())
        .build();
  }
}
