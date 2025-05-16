package com.daitoj.tkms.modules.common.service;

/** 採番上限超過例外 */
public class SequenceLimitException extends RuntimeException {
  /** コンストラクタ */
  public SequenceLimitException() {
    super();
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   */
  public SequenceLimitException(String message) {
    super(message);
  }

  /**
   * コンストラクタ
   *
   * @param message メッセージ
   * @param cause Throwable
   */
  public SequenceLimitException(String message, Throwable cause) {
    super(message, cause);
  }
}
