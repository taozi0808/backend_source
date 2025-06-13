package com.daitoj.tkms.modules.apil0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 請求一覧印刷パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "L0010S03Dto", description = "請求一覧印刷パラメータ")
public class L0010S03Dto extends L0010S01Dto {

  /* システム日付 */
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM月dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String sysDate;

  /* 物件着手日期間 */
  @Schema(name = "constrStartYmd", description = "物件着手日期間")
  private String constrStartYmd;

  /* 物件引渡日期間 */
  @Schema(name = "constrCompYmd", description = "物件引渡日期間")
  private String constrCompYmd;

}
