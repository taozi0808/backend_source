package com.daitoj.tkms.modules.apir0045.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 社員管理登録情報保存結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0045S03ReturnData", description = "社員管理登録情報保存結果")
public class R0045S03ReturnData {
  /** 従業員ID */
  @Schema(description = "従業員ID")
  private Long empId;

  /** 従業員コード */
  @Schema(description = "従業員コード")
  private String empCd;
}
