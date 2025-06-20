package com.daitoj.tkms.modules.apij0020.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/** 査定経費情報リスト */
@lombok.Getter
@lombok.Setter
@Schema(name = "SateiShiharaiInfoDto", description = "検索結果")
public class KeihiShiharaiInfoDto {
  /** 現場経費明細ID */
  @Schema(name = "id", description = "現場経費明細ID")
  private Long id;

  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  @Schema(name = "projectCd", description = "案件コード")
  private String projectCd;

  @Schema(name = "projectNm", description = "案件名")
  private String projectNm;

  @Schema(name = "expPaymentD", description = "支払日")
  private String expPaymentD;

  @Schema(name = "payeeK", description = "支払先区分")
  private String payeeK;

  @Schema(name = "payeeKNm", description = "支払先区分名")
  private String payeeKNm;

  @Schema(name = "partnerVendorCd", description = "協力業者コード")
  private String partnerVendorCd;

  @Schema(name = "compNm", description = "協力業者名")
  private String compNm;

  @Schema(name = "payeeEmpCd", description = "支払先従業員コード")
  private String payeeEmpCd;

  @Schema(name = "empNm", description = "支払先従業員名")
  private String empNm;

  @Schema(name = "empNm1", description = "支払先その他")
  private String empNm1;

  @Schema(name = "expItemCd", description = "経費内訳コード")
  private String expItemCd;

  @Schema(name = "expItemCdNm", description = "内訳区分名")
  private String expItemCdNm;

  @Schema(name = "majorWorkCd", description = "大工事コード")
  private String majorWorkCd;

  @Schema(name = "majorWorkNm", description = "大工事名")
  private String majorWorkNm;

  @Schema(name = "minorWorkCd", description = "小工事コード")
  private String minorWorkCd;

  @Schema(name = "minorWorkNm", description = "小工事名")
  private String minorWorkNm;

  @Schema(name = "inclTaxPaymentAmt", description = "支払金額（税込）")
  private BigDecimal inclTaxPaymentAmt;

  @Schema(name = "remarks", description = "備考")
  private String remarks;

  /** コンストラクタ */
  public KeihiShiharaiInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param id 現場経費明細ID
   * @param constrSiteCd 現場コード
   * @param constrSiteNm 現場名
   * @param projectCd 案件コード
   * @param projectNm 案件名
   * @param expPaymentD 支払日
   * @param payeeK 支払先区分
   * @param payeeKNm 支払先区分名
   * @param partnerVendorCd 協力業者コード
   * @param compNm 協力業者名
   * @param payeeEmpCd 支払先従業員コード
   * @param empNm 支払先従業員名
   * @param empNm1 支払先その他
   * @param expItemCd 経費内訳コード
   * @param expItemCdNm 内訳区分名
   * @param majorWorkCd 大工事コード
   * @param majorWorkNm 大工事名
   * @param minorWorkCd 小工事コード
   * @param minorWorkNm 小工事名
   * @param inclTaxPaymentAmt 支払金額（税込）
   * @param remarks 備考
   */
  public KeihiShiharaiInfoDto(
      Long id,
      String constrSiteCd,
      String constrSiteNm,
      String projectCd,
      String projectNm,
      String expPaymentD,
      String payeeK,
      String payeeKNm,
      String partnerVendorCd,
      String compNm,
      String payeeEmpCd,
      String empNm,
      String empNm1,
      String expItemCd,
      String expItemCdNm,
      String majorWorkCd,
      String majorWorkNm,
      String minorWorkCd,
      String minorWorkNm,
      BigDecimal inclTaxPaymentAmt,
      String remarks) {
    this.id = id;
    this.constrSiteCd = constrSiteCd;
    this.constrSiteNm = constrSiteNm;
    this.projectCd = projectCd;
    this.projectNm = projectNm;
    this.expPaymentD = expPaymentD;
    this.payeeK = payeeK;
    this.payeeKNm = payeeKNm;
    this.partnerVendorCd = partnerVendorCd;
    this.compNm = compNm;
    this.payeeEmpCd = payeeEmpCd;
    this.empNm = empNm;
    this.empNm1 = empNm1;
    this.expItemCd = expItemCd;
    this.expItemCdNm = expItemCdNm;
    this.majorWorkCd = majorWorkCd;
    this.majorWorkNm = majorWorkNm;
    this.minorWorkCd = minorWorkCd;
    this.minorWorkNm = minorWorkNm;
    this.inclTaxPaymentAmt = inclTaxPaymentAmt;
    this.remarks = remarks;
  }
}
