package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 従業員の異動明細情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.EmpTransferDtlDto", description = "従業員の異動明細情報")
public class EmpTransferDtlDto {

  /** 組織ID */
  @Schema(description = "組織ID")
  private String orgId;

  /** 組織コード */
  @Schema(description = "組織コード")
  private String orgCd;

  /** 組織区分 */
  @Schema(description = "組織区分")
  private String orgK;
}
