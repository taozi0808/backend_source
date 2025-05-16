package com.daitoj.tkms.modules.apir0045.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 社員管理登録情報取得結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0045S02ReturnData", description = "社員管理登録情報取得結果")
public class R0045S02ReturnData {
  /** 社員結果情報 */
  @Schema(description = "社員結果情報")
  private EmpResultDto emp;
}
