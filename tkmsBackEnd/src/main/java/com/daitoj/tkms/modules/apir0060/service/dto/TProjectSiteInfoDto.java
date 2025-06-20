package com.daitoj.tkms.modules.apir0060.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/** 取引履歴情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "TProjectSiteInfoDto", description = "取引履歴情報検索結果")
public class TProjectSiteInfoDto {

  /** 物件コード */
  @Schema(description = "物件コード")
  private String projectSiteCd;

  /** 物件名 */
  @Schema(name = "projectSiteNm", description = "物件名")
  private String projectSiteNm;

  /** 税込請負合計金額 */
  @Schema(name = "inclTaxCoTotalAmt", description = "受注金額")
  private BigDecimal inclTaxCoTotalAmt;

  /* 請求トータル金額 */
  @Schema(name = "invoiceTotalAmt", description = "入金金額")
  protected BigDecimal invoiceTotalAmt;

  /* 完工日 */
  @Schema(name = "constrCompYmd", description = "完工日")
  protected String constrCompYmd;

  @Schema(name = "underConstrProcessCd", description = "状態")
  private String underConstrProcessCd;

  /** 受注見込日 */
  @Schema(name = "orderExpectedYmd", description = "受注見込日")
  private String orderExpectedYmd;

  /* 着工日 */
  @Schema(name = "constrStartYmd", description = "着工日")
  protected String constrStartYmd;

  /** コンストラクタ. */
  public TProjectSiteInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param projectSiteCd 物件コード
   * @param projectSiteNm 物件名
   * @param inclTaxCoTotalAmt 受注金額
   * @param invoiceTotalAmt 入金金額
   * @param constrCompYmd 完工日
   * @param underConstrProcessCd 状態
   * @param orderExpectedYmd 受注見込日
   * @param constrStartYmd 着工日
   */
  public TProjectSiteInfoDto(
      String projectSiteCd,
      String projectSiteNm,
      BigDecimal inclTaxCoTotalAmt,
      BigDecimal invoiceTotalAmt,
      String constrCompYmd,
      String underConstrProcessCd,
      String orderExpectedYmd,
      String constrStartYmd) {
    this.projectSiteCd = projectSiteCd;
    this.projectSiteNm = projectSiteNm;
    this.inclTaxCoTotalAmt = inclTaxCoTotalAmt;
    this.invoiceTotalAmt = invoiceTotalAmt;
    this.constrCompYmd = constrCompYmd;
    this.underConstrProcessCd = underConstrProcessCd;
    this.orderExpectedYmd = orderExpectedYmd;
    this.constrStartYmd = constrStartYmd;
  }
}
