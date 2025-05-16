package com.daitoj.tkms.modules.common.service;

/** 採番ルール情報例外 */
public class InvalidFieldException extends RuntimeException {
  /** コンストラクタ */
  public InvalidFieldException() {
    super();
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   */
  public InvalidFieldException(String message) {
    super(message);
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   * @param cause Throwable
   */
  public InvalidFieldException(String message, Throwable cause) {
    super(message, cause);
  }
}
