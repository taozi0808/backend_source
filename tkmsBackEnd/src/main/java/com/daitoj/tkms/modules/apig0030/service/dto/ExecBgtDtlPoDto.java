package com.daitoj.tkms.modules.apig0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;

/** 実行予算明細発注情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "ExecBgtDtlPoDto", description = "実行予算明細発注情報")
public class ExecBgtDtlPoDto {

  /** 実行予算明細発注ID. */
  @Schema(name = "id", description = "実行予算明細発注ID")
  protected Long id;

  /** 実行予算明細ID. */
  @Schema(name = "execBgtDid", description = "実行予算明細ID")
  protected Long execBgtDid;

  /** 注文書No. */
  @Schema(name = "poNo", description = "注文書No")
  protected String poNo;

  /** 発注金額. */
  @Schema(name = "poAmt", description = "発注金額")
  protected BigDecimal poAmt;

  /** 協力業者コード. */
  @Schema(name = "partnerVendorCd", description = "協力業者コード")
  protected String partnerVendorCd;

  /** 協力業者名. */
  @Schema(name = "partnerVendorNm", description = "協力業者名")
  protected String partnerVendorNm;

  /** 査定済金額. */
  @Schema(name = "assessAmt", description = "査定済金額")
  protected BigDecimal assessAmt;

  /** 更新日時. */
  @Schema(name = "updTs", description = "更新日時")
  protected Instant updTs;

  /**
   * コンストラクタ.
   *
   * @param id 実行予算明細発注ID
   * @param execBgtDid 実行予算明細ID
   * @param poNo 注文書No
   * @param poAmt 発注金額
   * @param partnerVendorCd 協力業者コード
   * @param partnerVendorNm 協力業者名
   * @param assessAmt 査定済金額
   * @param updTs 更新日時
   */
  public ExecBgtDtlPoDto(
      Long id,
      Long execBgtDid,
      String poNo,
      BigDecimal poAmt,
      String partnerVendorCd,
      String partnerVendorNm,
      BigDecimal assessAmt,
      Instant updTs) {
    this.id = id;
    this.execBgtDid = execBgtDid;
    this.poNo = poNo;
    this.poAmt = poAmt;
    this.partnerVendorCd = partnerVendorCd;
    this.partnerVendorNm = partnerVendorNm;
    this.assessAmt = assessAmt;
    this.updTs = updTs;
  }
}
