package com.daitoj.tkms.modules.apif0010.service.dto;

import com.daitoj.tkms.modules.apib0010.service.dto.B0010S01Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 物件一覧印刷パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "D0010S03Dto", description = "精積算一覧印刷パラメータ")
public class F0010S03Dto extends B0010S01Dto {

  /** 利用PCのシステム日付. */
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM年dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;

}
