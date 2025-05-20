package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 従業員の異動情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.EmpTransferHdrDto", description = "従業員の異動情報")
public class EmpTransferHdrDto {

  /** 役職コード */
  @Schema(description = "役職コード")
  private String positionCd;

  /** 組織異動リスト */
  @Schema(description = "組織異動リスト")
  private List<EmpTransferDtlDto> dtlList;
}
