package com.daitoj.tkms.modules.apih0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 査定一覧印刷パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "H0010S03Dto", description = "査定一覧印刷パラメータ")
public class H0010S03Dto extends H0010S01Dto {

  /** 利用PCのシステム日付. */
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM年dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;
}
