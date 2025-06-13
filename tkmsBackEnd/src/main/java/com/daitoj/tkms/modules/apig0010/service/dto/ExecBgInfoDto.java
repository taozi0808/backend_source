package com.daitoj.tkms.modules.apig0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 実行予算情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "ExecBgInfoDto", description = "検索結果")
public class ExecBgInfoDto {

  /** id. */
  @Schema(name = "id", description = "id")
  private Long id;

  /** 現場コード. */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /** 現場名. */
  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  /** 現場カナ名. */
  @Schema(name = "constrSiteKnNm", description = "現場カナ名")
  private String constrSiteKnNm;

  /** 実行予算コード. */
  @Schema(name = "execBgtCd", description = "実行予算コード")
  private String execBgtCd;

  /** 予算申請日. */
  @Schema(name = "bgtYmRegDt", description = "予算申請日")
  private String bgtYmRegDt;

  /** 予算承認日. */
  @Schema(name = "finalApprDt", description = "予算承認日")
  private String finalApprDt;

  /** 予算作成部門ID. */
  @Schema(name = "bgtCreateOrgId", description = "予算作成部門ID")
  private Long bgtCreateOrgId;

  /** 予算作成部門. */
  @Schema(name = "orgNm", description = "予算作成部門")
  private String orgNm;

  /** 予算作成者コード. */
  @Schema(name = "bgtCreatePicCd", description = "予算作成者コード")
  private String bgtCreatePicCd;

  /** 予算作成者. */
  @Schema(name = "empNm", description = "予算作成者")
  private String empNm;

  /** 実行予算金額. */
  @Schema(name = "execBgtTotalAmt", description = "実行予算金額")
  private BigDecimal execBgtTotalAmt;

  /** 発注金額. */
  @Schema(name = "poAmt", description = "発注金額")
  private BigDecimal poAmt;

  /** 未発注額. */
  @Schema(name = "unpaid", description = "未発注額")
  private BigDecimal unpaid;

  /** 査定済金額. */
  @Schema(name = "assessTotalAmt", description = "査定済金額")
  private BigDecimal assessTotalAmt;

  /** 未査定残. */
  @Schema(name = "unidentified", description = "未査定残")
  private BigDecimal unidentified;

  public ExecBgInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param id 業者ID
   * @param constrSiteCd 現場コード
   * @param constrSiteNm 現場名
   * @param constrSiteKnNm 現場カナ名
   * @param execBgtCd 実行予算コード
   * @param bgtYmRegDt 予算申請日
   * @param finalApprDt 予算承認日
   * @param bgtCreateOrgId 予算作成部門ID
   * @param orgNm 予算作成部門
   * @param bgtCreatePicCd 予算作成者コード
   * @param empNm 予算作成者
   * @param execBgtTotalAmt 実行予算金額
   * @param poAmt 発注金額
   * @param assessTotalAmt 査定済金額
   */
  public ExecBgInfoDto(
      Long id,
      String constrSiteCd,
      String constrSiteNm,
      String constrSiteKnNm,
      String execBgtCd,
      String bgtYmRegDt,
      String finalApprDt,
      Long bgtCreateOrgId,
      String orgNm,
      String bgtCreatePicCd,
      String empNm,
      BigDecimal execBgtTotalAmt,
      BigDecimal poAmt,
      BigDecimal assessTotalAmt) {
    this.id = id;
    this.constrSiteCd = constrSiteCd;
    this.constrSiteNm = constrSiteNm;
    this.constrSiteKnNm = constrSiteKnNm;
    this.execBgtCd = execBgtCd;
    this.bgtYmRegDt = bgtYmRegDt;
    this.finalApprDt = finalApprDt;
    this.bgtCreateOrgId = bgtCreateOrgId;
    this.orgNm = orgNm;
    this.bgtCreatePicCd = bgtCreatePicCd;
    this.empNm = empNm;
    this.execBgtTotalAmt = execBgtTotalAmt;
    this.poAmt = poAmt;
    this.assessTotalAmt = assessTotalAmt;
  }
}
