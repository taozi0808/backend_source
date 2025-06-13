package com.daitoj.tkms.modules.apig0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 実行予算一覧印刷パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0010S03Dto", description = "実行予算一覧印刷パラメータ")
public class G0010S03Dto extends G0010S01Dto {

  /** 利用PCのシステム日付. */
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM年dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;

}
