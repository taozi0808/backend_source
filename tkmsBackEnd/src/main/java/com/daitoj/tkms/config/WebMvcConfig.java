package com.daitoj.tkms.config;

import com.daitoj.tkms.modules.common.interceptor.PermissionInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** WebMvcConfigurer */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  /** リクエストパラメータチエックインターセプター */
  private final PermissionInterceptor permissionInterceptor;

  /** コンストラクタ */
  public WebMvcConfig(PermissionInterceptor permissionInterceptor) {
    this.permissionInterceptor = permissionInterceptor;
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    for (HttpMessageConverter<?> converter : converters) {
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        break;
      }
    }
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry
        .addInterceptor(permissionInterceptor)
        .addPathPatterns("/api/**") // チエックするAPI
        .excludePathPatterns(
            "/api/v1/login/auth",
            "/api/v1/login/notices",
            "/api/v1/reset/auth",
            "/api/v1/passwordUpdate/update",
            "/api/v1/storage/**",
            "/api/v1/cache/**",
            "/api/v1/itemListSetting/**"); // チエックしないAPI
  }
}
