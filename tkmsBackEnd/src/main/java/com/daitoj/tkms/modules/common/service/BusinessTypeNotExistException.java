package com.daitoj.tkms.modules.common.service;

/** 業務種類存在しない例外 */
public class BusinessTypeNotExistException extends RuntimeException {
  /** コンストラクタ */
  public BusinessTypeNotExistException() {
    super();
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   */
  public BusinessTypeNotExistException(String message) {
    super(message);
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   * @param cause Throwable
   */
  public BusinessTypeNotExistException(String message, Throwable cause) {
    super(message, cause);
  }
}
