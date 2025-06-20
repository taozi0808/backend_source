package com.daitoj.tkms.modules.apic0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 概算明細リスト */
@lombok.Getter
@lombok.Setter
@Schema(name = "RoughEstDtlInfoDto", description = "概算明細リスト")
public class RoughEstDtlInfoDto {

  /** 概算明細ID */
  @Schema(name = "id", description = "概算明細ID")
  private Long id;

  /** 大工事コード */
  @Schema(name = "majorWorkCd", description = "大工事コード")
  private String majorWorkCd;

  /** 大工事名 */
  @Schema(name = "majorWorkNm", description = "大工事名")
  private String majorWorkNm;

  /** 小工事コード */
  @Schema(name = "minorWorkCd", description = "小工事コード")
  private String minorWorkCd;

  /** 小工事名 */
  @Schema(name = "minorWorkNm", description = "小工事名")
  private String minorWorkNm;

  /** 規格 */
  @Schema(name = "spec", description = "規格")
  private String spec;

  /** 数量 */
  @Schema(name = "qty", description = "数量")
  private BigDecimal qty;

  /** 単価 */
  @Schema(name = "price", description = "単価")
  private BigDecimal price;

  /** 単位 */
  @Schema(name = "unit", description = "単位")
  private String unit;

  /** 概算金額 */
  @Schema(name = "roughEstAmt", description = "概算金額")
  private BigDecimal roughEstAmt;

  /** 備考 */
  @Schema(name = "remarks", description = "備考")
  private String remarks;

  /** 業者見積No */
  @Schema(name = "vendorEstNo", description = "業者見積No")
  private String vendorEstNo;

  /** 協力業者コード */
  @Schema(name = "partnerVendorCd", description = "協力業者コード")
  private String partnerVendorCd;

  /** 会社名 */
  @Schema(name = "compNm", description = "会社名")
  private String compNm;

  /** コンストラクタ */
  public RoughEstDtlInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param id 概算明細ID
   * @param majorWorkCd 大工事コード
   * @param majorWorkNm 大工事名
   * @param minorWorkCd 小工事コード
   * @param minorWorkNm 小工事名
   * @param spec 規格
   * @param qty 数量
   * @param price 単価
   * @param unit 単位
   * @param roughEstAmt 概算金額
   * @param remarks 備考
   * @param vendorEstNo 業者見積No
   * @param partnerVendorCd 協力業者コード
   * @param compNm 会社名
   */
  public RoughEstDtlInfoDto(
      Long id,
      String majorWorkCd,
      String majorWorkNm,
      String minorWorkCd,
      String minorWorkNm,
      String spec,
      BigDecimal qty,
      BigDecimal price,
      String unit,
      BigDecimal roughEstAmt,
      String remarks,
      String vendorEstNo,
      String partnerVendorCd,
      String compNm) {
      this.id = id;
    this.majorWorkCd = majorWorkCd;
    this.majorWorkNm = majorWorkNm;
    this.minorWorkCd = minorWorkCd;
    this.minorWorkNm = minorWorkNm;
    this.spec = spec;
    this.qty = qty;
    this.price = price;
    this.unit = unit;
    this.roughEstAmt = roughEstAmt;
    this.remarks = remarks;
    this.vendorEstNo = vendorEstNo;
    this.partnerVendorCd = partnerVendorCd;
    this.compNm = compNm;
  }
}
