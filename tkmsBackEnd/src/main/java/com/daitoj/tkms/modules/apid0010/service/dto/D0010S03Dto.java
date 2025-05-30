package com.daitoj.tkms.modules.apid0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 精積算一覧印刷パラメータ. */
@lombok.Setter
@lombok.Getter
@Schema(name = "D0010S03Dto", description = "精積算一覧印刷パラメータ")
public class D0010S03Dto extends D0010S01Dto {

  /** 利用PCのシステム日付. */
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM年dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;

}
