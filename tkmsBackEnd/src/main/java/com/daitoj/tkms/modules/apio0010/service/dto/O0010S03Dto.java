package com.daitoj.tkms.modules.apio0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 業者一覧印刷パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "O0010S03Dto", description = "業者一覧印刷パラメータ")
public class O0010S03Dto extends O0010S01Dto {
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM月dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;
}
