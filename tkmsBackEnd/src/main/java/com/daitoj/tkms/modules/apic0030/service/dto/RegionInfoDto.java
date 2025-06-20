package com.daitoj.tkms.modules.apic0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/** 地域情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "RegionInfoDto", description = "地域情報")
public class RegionInfoDto {

  /** 地域コード */
  @Schema(name = "regionCd", description = "地域コード")
  private String regionCd;

  /** 地域名 */
  @Schema(name = "regionNm", description = "地域名")
  private String regionNm;

  /** コンストラクタ */
  public RegionInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param regionCd 地域コード
   * @param regionNm 地域名
   */
  public RegionInfoDto(
      String regionCd,
      String regionNm) {
    this.regionCd = regionCd;
    this.regionNm = regionNm;
  }
}
