package com.daitoj.tkms.modules.common.service;

/** 排他例外 */
public class ConflictException extends RuntimeException {

  /** コンストラクタ */
  public ConflictException() {
    super();
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   */
  public ConflictException(String message) {
    super(message);
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   * @param cause Throwable
   */
  public ConflictException(String message, Throwable cause) {
    super(message, cause);
  }
}
