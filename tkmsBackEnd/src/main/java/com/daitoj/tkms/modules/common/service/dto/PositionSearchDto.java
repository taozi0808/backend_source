package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 役職検索情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.PositionSearchDto", description = "役職検索情報")
public class PositionSearchDto {

  /** 役職コード */
  private String positionCd;

  /** 役職名 */
  @Schema(description = "役職名(表示のみ)")
  private String positionNm;
}
