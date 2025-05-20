package com.daitoj.tkms.modules.apir0045.service.dto;

import com.daitoj.tkms.modules.common.service.dto.EmpTransferHdrDto;
import io.swagger.v3.oas.annotations.media.Schema;

/** 従業員の異動情報結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0045S04ReturnData", description = "従業員の異動情報結果")
public class R0045S04ReturnData {

  /** 従業員の異動情報 */
  @Schema(description = "従業員の異動情報")
  private EmpTransferHdrDto empTransferHdr;
}
