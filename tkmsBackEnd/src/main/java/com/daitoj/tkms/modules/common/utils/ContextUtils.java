package com.daitoj.tkms.modules.common.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/** Context Utils */
public class ContextUtils {
  /**
   * Request情報を取得
   *
   * @param key キー
   * @return Request情報
   */
  public static String getParameter(String key) {
    if (RequestContextHolder.getRequestAttributes() == null) {
      return null;
    }

    return (String)
        RequestContextHolder.getRequestAttributes()
            .getAttribute(key, RequestAttributes.SCOPE_REQUEST);
  }

  /**
   * Request情報を設定
   *
   * @param key キー
   * @param value 設定値
   */
  public static void setParameter(String key, String value) {
    if (RequestContextHolder.getRequestAttributes() == null) {
      return;
    }

    RequestContextHolder.getRequestAttributes()
        .setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
  }
}
