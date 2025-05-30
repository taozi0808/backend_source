package com.daitoj.tkms.modules.apib0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 消費税率情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "TaxRateDto", description = "消費税率情報")
public class TaxRateDto {
  /** 消費税率ID */
  @Schema(description = "消費税率ID")
  private Integer id;

  /** 消費税率 */
  @Schema(description = "消費税率")
  private BigDecimal taxRate;

  /** 表示内容 */
  @Schema(description = "表示内容")
  private String displayContent;
}
