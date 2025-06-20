package com.daitoj.tkms.modules.apic0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/** 単価情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "MPriceRegionInfoDto", description = "単価情報")
public class MPriceRegionInfoDto {
  /** 単価地域ID */
  @Schema(name = "priceRegionId", description = "単価地域ID")
  private Long priceRegionId;

  /** 地域コード */
  @Schema(name = "regionCd", description = "地域コード")
  private String regionCd;

  /** 地域名 */
  @Schema(name = "regionNm", description = "地域名")
  private String regionNm;

  /** 大工事コード */
  @Schema(description = "大工事コード")
  private String majorWorkCd;

  /** 大工事名 */
  @Schema(description = "大工事名")
  private String majorWorkNm;

  /** 大工事表示順 */
  @Schema(description = "大工事表示順")
  private Integer majorWorkCddisplayOrder;

  /** 小工事コード */
  @Schema(description = "小工事コード")
  private String minorWorkCd;

  /** 小工事名 */
  @Schema(description = "小工事名")
  private String minorWorkNm;

  /** 小工事表示順 */
  @Schema(description = "小工事表示順")
  private Integer minorWorkCddisplayOrder;

  /** 規格 */
  @Schema(name = "spec", description = "規格")
  private String spec;

  /** 単価 */
  @Schema(name = "price", description = "単価")
  private BigDecimal price;

  /** 単位 */
  @Schema(name = "unit", description = "単位")
  private String unit;

  /** コンストラクタ */
  public MPriceRegionInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param priceRegionId 単価地域ID
   * @param regionCd 地域コード
   * @param regionNm 地域名
   * @param majorWorkCd 大工事コード
   * @param majorWorkNm 大工事名
   * @param majorWorkCddisplayOrder 大工事表示順
   * @param minorWorkCd 小工事コード
   * @param minorWorkNm 小工事名
   * @param minorWorkCddisplayOrder 小工事表示順
   * @param spec 規格
   * @param price 単価
   * @param unit 単位
   */
  public MPriceRegionInfoDto(
      Long priceRegionId,
      String regionCd,
      String regionNm,
      String majorWorkCd,
      String majorWorkNm,
      Integer majorWorkCddisplayOrder,
      String minorWorkCd,
      String minorWorkNm,
      Integer minorWorkCddisplayOrder,
      String spec,
      BigDecimal price,
      String unit) {
    this.priceRegionId = priceRegionId;
    this.regionCd = regionCd;
    this.regionNm = regionNm;
    this.majorWorkCd = majorWorkCd;
    this.majorWorkNm = majorWorkNm;
    this.majorWorkCddisplayOrder = majorWorkCddisplayOrder;
    this.minorWorkCd = minorWorkCd;
    this.minorWorkNm = minorWorkNm;
    this.minorWorkCddisplayOrder = minorWorkCddisplayOrder;
    this.spec = spec;
    this.price = price;
    this.unit = unit;
  }
}
