package com.daitoj.tkms.modules.apib0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 案件登録保存結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0030S03ReturnData", description = "案件登録保存結果")
public class B0030S03ReturnData {
  /** 案件ID */
  @Schema(description = "案件ID")
  private Long projectId;

  /** 歴番 */
  @Schema(description = "歴番")
  private Integer hisNo;

  /** 物件ID */
  @Schema(description = "物件ID")
  private Long projectSiteId;

  /** 物件コード */
  @Schema(description = "物件コード")
  private String projectSiteCd;
}
