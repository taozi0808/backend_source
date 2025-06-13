package com.daitoj.tkms.modules.apih0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 査定情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "AssessInfoDto", description = "査定一覧検索結果")
public class AssessInfoDto {

  /* 査定ヘッダID */
  @Schema(name = "id", description = "査定ヘッダID")
  private Long id;

  /* 現場コード */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /* 現場名 */
  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  /* 現場カナ名 */
  @Schema(name = "constrSiteKnNm", description = "現場カナ名")
  private String constrSiteKnNm;

  /* 工事部門コード */
  @Schema(name = "orgCd", description = "工事部門コード")
  private String orgCd;

  /* 工事部門 */
  @Schema(name = "orgNm", description = "工事部門")
  private String orgNm;

  /* 現場所長コード */
  @Schema(name = "constrSiteDirectorCd", description = "現場所長コード")
  private String constrSiteDirectorCd;

  /* 現場所長 */
  @Schema(name = "constrSiteEmpNm", description = "現場所長")
  private String constrSiteEmpNm;

  /* 専任技術者コード */
  @Schema(name = "picCd", description = "専任技術者コード")
  private String picCd;

  /* 専任技術者 */
  @Schema(name = "empNm", description = "専任技術者")
  private String empNm;

  /* 工事工程 */
  @Schema(name = "constrProcessNm", description = "工事工程")
  private String constrProcessNm;

  /* 査定承認日 */
  @Schema(name = "finalApprDt", description = "査定承認日")
  private String finalApprDt;

  /* 実行予算金額 */
  @Schema(name = "execBgtTotalAmt", description = "実行予算金額")
  private BigDecimal execBgtTotalAmt;

  /* 前月迄査定済金額 */
  @Schema(name = "befAssessTotalAmt", description = "前月迄査定済金額")
  private BigDecimal befAssessTotalAmt;

  /* 当月査定金額 */
  @Schema(name = "mtdAssessTotalAmt", description = "前月迄査定済金額")
  private BigDecimal mtdAssessTotalAmt;

  /* 査定済合計金額 */
  @Schema(name = "assessTotalAmt", description = " 査定済合計金額 ")
  private BigDecimal assessTotalAmt;

  /** 発注金額. */
  @Schema(name = "poTotalAmt", description = "発注金額")
  private BigDecimal poTotalAmt;

  /** 未査定残. */
  @Schema(name = "unidentified", description = "未査定残")
  private BigDecimal unidentified;

  /** 所属事業所コード. */
  @Schema(name = "belongOfficeCd", description = "所属事業所コード")
  private BigDecimal belongOfficeCd;

  /** 表示対象. */
  @Schema(name = "displayobject", description = "表示対象")
  private BigDecimal displayobject;

  /**
   * コンストラクタ.
   */
  public AssessInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param id 業者ID
   * @param constrSiteCd 現場コード
   * @param constrSiteNm 現場名
   * @param orgCd 工事部門コード
   * @param orgNm 工事部門
   * @param constrSiteDirectorCd 現場所長コード
   * @param constrSiteEmpNm 現場所長
   * @param picCd 専任技術者コード
   * @param empNm 専任技術者
   * @param constrProcessNm 工事工程
   * @param finalApprDt 査定承認日
   * @param execBgtTotalAmt 実行予算金額
   * @param befAssessTotalAmt 当月査定金額
   * @param mtdAssessTotalAmt 前月迄査定済金額
   * @param assessTotalAmt 査定済合計金額
   * @param poTotalAmt 発注金額
   */
  public AssessInfoDto(
      Long id,
      String constrSiteCd,
      String constrSiteNm,
      String orgCd,
      String orgNm,
      String constrSiteDirectorCd,
      String constrSiteEmpNm,
      String picCd,
      String empNm,
      String constrProcessNm,
      String finalApprDt,
      BigDecimal execBgtTotalAmt,
      BigDecimal befAssessTotalAmt,
      BigDecimal mtdAssessTotalAmt,
      BigDecimal assessTotalAmt,
      BigDecimal poTotalAmt) {
    this.id = id;
    this.constrSiteCd = constrSiteCd;
    this.constrSiteNm = constrSiteNm;
    this.orgCd = orgCd;
    this.orgNm = orgNm;
    this.constrSiteDirectorCd = constrSiteDirectorCd;
    this.constrSiteEmpNm = constrSiteEmpNm;
    this.picCd = picCd;
    this.empNm = empNm;
    this.constrProcessNm = constrProcessNm;
    this.finalApprDt = finalApprDt;
    this.execBgtTotalAmt = execBgtTotalAmt;
    this.befAssessTotalAmt = befAssessTotalAmt;
    this.mtdAssessTotalAmt = mtdAssessTotalAmt;
    this.assessTotalAmt = assessTotalAmt;
    this.poTotalAmt = poTotalAmt;
  }
}
