package com.daitoj.tkms.modules.common.service;

/** ログイン例外 */
public class InvalidUserException extends RuntimeException {

  /** メッセージコード */
  private String errorCode;

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   */
  public InvalidUserException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   * @param cause Throwable
   */
  public InvalidUserException(String message, Throwable cause) {
    super(message, cause);
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
}
