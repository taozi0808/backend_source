package com.daitoj.tkms.modules.apic0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/** 請求条件リスト */
@lombok.Getter
@lombok.Setter
@Schema(name = "ProjectPaymentTermsInfoDto", description = "請求条件リスト")
public class ProjectPaymentTermsInfoDto {
  //  /** 連番 */
  //  @Schema(name = "seqNo", description = "連番")
  //  private Integer seqNo;

  /** 請求条件区分 */
  @Schema(name = "paymentTermsK", description = "請求条件区分")
  private String paymentTermsK;

  /** 請求条件名 */
  @Schema(name = "itemValue", description = "請求条件名")
  private String itemValue;

  /** 割合 */
  @Schema(name = "paymentRatio", description = "割合")
  private String paymentRatio;

  /** 税抜請求金額 */
  @Schema(name = "exclTaxPaymentAmt", description = "税抜請求金額")
  private BigDecimal exclTaxPaymentAmt;

  /** 税込請求金額 */
  @Schema(name = "inclTaxPaymentAmt", description = "税込請求金額")
  private BigDecimal inclTaxPaymentAmt;

  /** 消費税率ID */
  @Schema(name = "taxRateId", description = "消費税率ID")
  private Integer taxRateId;

  /** 消費税率 */
  @Schema(name = "taxRate", description = "消費税率")
  private BigDecimal taxRate;

  /** 消費税金額 */
  @Schema(name = "salesTaxAmt", description = "消費税金額")
  private BigDecimal salesTaxAmt;

  /** コンストラクタ */
  public ProjectPaymentTermsInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param paymentTermsK 請求条件区分
   * @param itemValue 請求条件名
   * @param paymentRatio 割合
   * @param exclTaxPaymentAmt 税抜請求金額
   * @param inclTaxPaymentAmt 税込請求金額
   * @param taxRateId 消費税率ID
   * @param taxRate 消費税率
   * @param salesTaxAmt 消費税金額
   */
  public ProjectPaymentTermsInfoDto(
      String paymentTermsK,
      String itemValue,
      String paymentRatio,
      BigDecimal exclTaxPaymentAmt,
      BigDecimal inclTaxPaymentAmt,
      Integer taxRateId,
      BigDecimal taxRate,
      BigDecimal salesTaxAmt) {
    this.paymentTermsK = paymentTermsK;
    this.itemValue = itemValue;
    this.paymentRatio = paymentRatio;
    this.exclTaxPaymentAmt = exclTaxPaymentAmt;
    this.inclTaxPaymentAmt = inclTaxPaymentAmt;
    this.taxRateId = taxRateId;
    this.taxRate = taxRate;
    this.salesTaxAmt = salesTaxAmt;
  }
}
