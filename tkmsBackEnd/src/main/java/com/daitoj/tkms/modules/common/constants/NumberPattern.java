package com.daitoj.tkms.modules.common.constants;

/** 採番パターン */
public enum NumberPattern {
  /** 連番（接頭辞なし） */
  SEQUENCE_ONLY("1"),

  /** 接頭辞＋連番 */
  PREFIX_AND_SEQUENCE("2"),

  /** 接頭辞＋年月(YYYYMM)＋連番 */
  PREFIX_AND_YEAR_MONTH_YYYYMM_AND_SEQUENCE("3"),

  /** 接頭辞＋年月(YYMM)＋連番 */
  PREFIX_AND_YEAR_MONTH_YYMM_AND_SEQUENCE("4");

  private final String pattern;

  /** コンストラクタ */
  NumberPattern(String pattern) {
    this.pattern = pattern;
  }

  /**
   * 採番パターンを取得
   *
   * @return 採番パターン
   */
  public String getPattern() {
    return pattern;
  }
}
