package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 大工事検索情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.MajorWorkSearchDto", description = "大工事検索情報")
public class MajorWorkSearchDto {
  /** 大工事コード */
  @Schema(description = "大工事コード")
  private String majorWorkCd;

  /** 大工事名 */
  @Schema(description = "大工事名")
  private String majorWorkNm;
}
