package com.daitoj.tkms.modules.apir0045.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** 社員管理登録情報保存パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0045Dto", description = "社員管理登録情報保存パラメータ")
public class R0045Dto {

  /** 処理区分 */
  @NotNull
  @Size(max = 1)
  @Schema(description = "処理区分", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 1)
  private String shorikubun;

  /** 社員情報 */
  @Valid
  @NotNull
  @Schema(description = "社員情報", requiredMode = Schema.RequiredMode.REQUIRED)
  private EmpDto emp;
}
