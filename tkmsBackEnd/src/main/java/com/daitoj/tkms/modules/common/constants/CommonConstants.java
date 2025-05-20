package com.daitoj.tkms.modules.common.constants;

/** 定数定義 */
public final class CommonConstants {

  /** CSV出力のCharset */
  public static final String CSV_CHARSET_SHIFT_JIS = "Shift_JIS";

  /** 削除フラグ（未取消） */
  public static final String DELETE_FLAG_VALID = "0";

  /** 削除フラグ（削除済） */
  public static final String DELETE_FLAG_DELETE = "1";

  /** 権限なし */
  public static final String NO_PERMISSION = "0";

  /** 権限あり */
  public static final String HAS_PERMISSION = "1";

  /** アカウント区分:社員 */
  public static final String ACCOUNT_K_EMP = "1";

  /** アカウント区分:協力業者 */
  public static final String ACCOUNT_K_VENDOR = "2";

  /** アカウント区分:協力業者社員 */
  public static final String ACCOUNT_K_VENDOR_WORKER = "3";

  /** 最新フラグ;1：最新 */
  public static final String NEWEST_FLAG_NEW = "1";

  /** 最新フラグ;2：過去 */
  public static final String NEWEST_FLAG_HIS = "2";

  /** 権限チェックフラグ-チェック要 */
  public static final String PERM_CHECK_FLG = "1";

  /** CSV・PDF最大件数 */
  public static final int SEARCH_MAX_COUNT = 10000;

  /** 担当事業所：「本社」 */
  public static final String OFFICE_HONSHA = "01";

  /** 歴番:01から連番 */
  public static final String HIS_NO_START = "01";

  /** 処理区分：新規 */
  public static final String SHORIKUBUN_SINNKI = "1";

  /** 概算作成フラグ 0：未作成 */
  public static final String ROUGH_EST_CREATE_FLG_UNCREATED = "0";

  /** 役職名称に長が含まれる人 */
  public static final String POSITION_MGR_NAME = "長";

  /** 部署:部 */
  public static final Integer ORG_LVL_BU = 2;

  /** 部署:部課 */
  public static final Integer ORG_LVL_KA = 4;
}
