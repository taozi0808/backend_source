package com.daitoj.tkms.modules.apir0045.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** 役職情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "PositionDto", description = "役職情報")
public class PositionDto {
  /** 役職コード */
  @NotNull
  @Size(max = 2)
  @Schema(description = "役職コード", maxLength = 2, requiredMode = Schema.RequiredMode.REQUIRED)
  private String positionCd;

  /** 役職名 */
  @Schema(description = "役職名(表示のみ)")
  private String positionNm;
}
