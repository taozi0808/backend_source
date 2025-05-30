package com.daitoj.tkms.modules.common.service;

/** 承認情報存在しない例外 */
public class WfApprNotExistException extends RuntimeException {
  /** コンストラクタ */
  public WfApprNotExistException() {
    super();
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   */
  public WfApprNotExistException(String message) {
    super(message);
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   * @param cause Throwable
   */
  public WfApprNotExistException(String message, Throwable cause) {
    super(message, cause);
  }
}
