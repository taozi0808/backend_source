package com.daitoj.tkms.modules.apir0040.service.dto;

import com.daitoj.tkms.modules.apid0010.service.dto.D0010S01Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 社員一覧印刷パラメータ. */
@lombok.Setter
@lombok.Getter
@Schema(name = "R0040S03Dto", description = "社員一覧印刷パラメータ")
public class R0040S03Dto extends R0040S01Dto {

  /* 利用PCのシステム日付 */
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM年dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;

}
