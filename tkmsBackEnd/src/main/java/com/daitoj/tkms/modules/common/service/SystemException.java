package com.daitoj.tkms.modules.common.service;

/** システム例外 */
public class SystemException extends RuntimeException {
  /**
   * コンストラクタ
   *
   * @param message メッセージ
   */
  public SystemException(String message) {
    super(message);
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   * @param cause Throwable
   */
  public SystemException(String message, Throwable cause) {
    super(message, cause);
  }
}
