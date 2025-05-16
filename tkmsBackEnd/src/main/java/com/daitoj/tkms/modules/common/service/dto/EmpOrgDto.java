package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 従業員・組織・対照情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.EmpOrgDto", description = "従業員・組織・対照情報")
public class EmpOrgDto {
  /** 連番 */
  @Schema(description = "連番")
  private Integer seqNo;

  /** 組織情報 */
  @Schema(description = "組織情報")
  private OrgDto org;
}
