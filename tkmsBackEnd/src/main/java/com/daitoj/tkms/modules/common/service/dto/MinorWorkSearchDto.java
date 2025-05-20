package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 小工事検索情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.MinorWorkSearchDto", description = "小工事検索情報")
public class MinorWorkSearchDto {
  /** 小工事コード */
  @Schema(description = "小工事コード")
  private String minorWorkCd;

  /** 小工事名 */
  @Schema(description = "小工事名")
  private String minorWorkNm;
}
