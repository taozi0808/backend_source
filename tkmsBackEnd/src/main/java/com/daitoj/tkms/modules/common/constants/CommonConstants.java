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

  /** 1:制御あり 2:制御なし */
  public static final String USE_TIME_CONTROL_FLG = "2";

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

  /** 担当事業所コード：「本社」 */
  public static final String OFFICE_HONSHA_CODE = "000";

  /** 歴番:01から連番 */
  public static final Integer HIS_NO_START = 1;

  /** 処理区分：新規 */
  public static final String SHORIKUBUN_SINNKI = "1";

  /** 役職名称に長が含まれる人 */
  public static final String POSITION_MGR_NAME = "長";

  /** 部署:部 */
  public static final Integer ORG_LVL_BU = 3;

  /** 部署:部課 */
  public static final Integer ORG_LVL_KA = 5;

  /** 業務データステータス：'2'(申請中) */
  public static final String BUSINESS_DATA_ST_REQ = "2";

  /** 承認状態：'1'(承認待) */
  public static final String APPR_ST_PENDING_APPROVAL = "1";

  /** 承認状態：'3'(承認済) */
  public static final String APPR_ST_PASS = "3";

  /** 承認状態：'9'(差戻) */
  public static final String APPR_ST_RETURN = "9";

  /** 支払データ作成フラグ：'1'(作成済) */
  public static final String HAS_CREATE_FLG = "1";

  /** 支払データ作成フラグ：'0'(未作成) */
  public static final String  NO_CREATE_FLG= "0";
}
