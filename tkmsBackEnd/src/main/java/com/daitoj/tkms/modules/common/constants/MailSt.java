package com.daitoj.tkms.modules.common.constants;

/** メール送信ステータス */
public enum MailSt {
  /** 送信成功 */
  SUCCESS("1"),

  /** 送信失敗 */
  ERROR("2");

  private final String sts;

  /** コンストラクタ */
  MailSt(String sts) {
    this.sts = sts;
  }

  /**
   * メール送信ステータスを取得
   *
   * @return メール送信ステータス
   */
  public String getSts() {
    return sts;
  }
}
