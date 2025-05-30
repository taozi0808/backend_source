package com.daitoj.tkms.modules.common.service;

/** WF申請情報存在しない例外 */
public class RequestNotExistException extends RuntimeException {
  /** コンストラクタ */
  public RequestNotExistException() {
    super();
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   */
  public RequestNotExistException(String message) {
    super(message);
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   * @param cause Throwable
   */
  public RequestNotExistException(String message, Throwable cause) {
    super(message, cause);
  }
}
