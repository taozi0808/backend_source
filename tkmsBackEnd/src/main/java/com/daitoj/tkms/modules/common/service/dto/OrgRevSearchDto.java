package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 組織改定情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.OrgDto", description = "組織改定情報")
public class OrgRevSearchDto {

  /** 適用開始日 */
  @Schema(description = "適用開始日")
  private String effectiveStartDt;
}
