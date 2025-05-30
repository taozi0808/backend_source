package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

/** 案件請求条件情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ProjectPaymentTermsDto", description = "案件請求条件情報")
public class ProjectPaymentTermsDto extends BaseDto {
  /** 案件ID */
  @Schema(description = "案件ID")
  private Long projectId;

  /** 連番 */
  @NotNull
  @Schema(description = "連番", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer seqNo;

  /** 棟コード */
  @NotNull
  @Size(max = 2)
  @Schema(description = "棟コード", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 2)
  private String buildingCd;

  /** 請求条件コード */
  @NotNull
  @Size(max = 2)
  @Schema(description = "請求条件コード", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 2)
  private String paymentTermsCd;

  /** 請求条件区分名 */
  @Schema(description = "請求条件区分名", requiredMode = Schema.RequiredMode.REQUIRED)
  private String paymentTermsNm;

  /** 割合 */
  @NotNull
  @Schema(description = "割合", requiredMode = Schema.RequiredMode.REQUIRED)
  private String paymentRatio;

  /** 税抜請求金額 */
  @NotNull
  @DecimalMax(value = "99999999999")
  @Schema(description = "税抜請求金額", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 11)
  private BigDecimal exclTaxPaymentAmt;

  /** 合計金額 */
  @NotNull
  @DecimalMax(value = "99999999999")
  @Schema(description = "合計金額", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 11)
  private BigDecimal inclTaxPaymentAmt;

  /** 消費税率ID */
  @NotNull
  @Schema(description = "消費税率ID", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer taxRateId;

  /** 消費税率 */
  @Schema(description = "消費税率")
  private BigDecimal taxRate;

  /** 消費税金額 */
  @NotNull
  @DecimalMax(value = "99999999999")
  @Schema(description = "消費税金額", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 11)
  private BigDecimal salesTaxAmt;

  /** コンストラクタ */
  public ProjectPaymentTermsDto() {}

  /**
   * コンストラクタ
   *
   * @param projectId 案件ID
   * @param seqNo 連番
   * @param buildingCd 棟コード
   * @param paymentTermsCd 請求条件区分
   * @param paymentTermsNm 請求条件区分名
   * @param paymentRatio 割合
   * @param exclTaxPaymentAmt 税抜請求金額
   * @param inclTaxPaymentAmt 合計金額
   * @param taxRateId 消費税率ID
   * @param taxRate 消費税率
   * @param salesTaxAmt 消費税金額
   * @param delFlg 削除フラグ
   * @param regTs 登録日時
   * @param regUserId 登録者ID
   * @param regPgId 登録PG
   * @param updTs 更新日時
   * @param updUserId 更新者ID
   * @param updPgId 更新PG
   */
  public ProjectPaymentTermsDto(
      Long projectId,
      Integer seqNo,
      String buildingCd,
      String paymentTermsCd,
      String paymentTermsNm,
      String paymentRatio,
      BigDecimal exclTaxPaymentAmt,
      BigDecimal inclTaxPaymentAmt,
      Integer taxRateId,
      BigDecimal taxRate,
      BigDecimal salesTaxAmt,
      String delFlg,
      Instant regTs,
      String regUserId,
      String regPgId,
      Instant updTs,
      String updUserId,
      String updPgId) {
    this.projectId = projectId;
    this.seqNo = seqNo;
    this.buildingCd = buildingCd;
    this.paymentTermsCd = paymentTermsCd;
    this.paymentTermsNm = paymentTermsNm;
    this.paymentRatio = paymentRatio;
    this.exclTaxPaymentAmt = exclTaxPaymentAmt;
    this.inclTaxPaymentAmt = inclTaxPaymentAmt;
    this.taxRateId = taxRateId;
    this.taxRate = taxRate;
    this.salesTaxAmt = salesTaxAmt;
    this.delFlg = delFlg;
    this.regTs = regTs;
    this.regUserId = regUserId;
    this.regPgId = regPgId;
    this.updTs = updTs;
    this.updUserId = updUserId;
    this.updPgId = updPgId;
  }
}
