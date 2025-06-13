package com.daitoj.tkms.modules.apir0055.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

/** 顧客情報一覧印刷パラメータ. */
@lombok.Setter
@lombok.Getter
@Schema(name = "R0055S03Dto", description = "顧客情報一覧印刷パラメータ")
public class R0055S03Dto extends R0055S01Dto {

  /* 利用PCのシステム日付(yyyy年MM月dd日HH:mm:ss) */
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM月dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;

  /* 取引区分名 */
  @Schema(
      name = "tradingNm",
      description = "取引区分名",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private String tradingNm;

}
