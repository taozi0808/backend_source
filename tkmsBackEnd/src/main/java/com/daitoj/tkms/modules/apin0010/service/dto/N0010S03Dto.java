package com.daitoj.tkms.modules.apin0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 工事予実一覧印刷パラメータ. */
@lombok.Setter
@lombok.Getter
@Schema(name = "N0010S03Dto", description = "工事予実一覧印刷パラメータ")
public class N0010S03Dto extends N0010S01Dto {

  /** 利用PCのシステム日付. */
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM年dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;

  /** 予実作成日. */
  @Schema(name = "wbsCreateDt", description = "予実作成日(yyyy年MM年dd日 ～ yyyy年MM年dd日)")
  private String wbsCreateDt;

  /** 現場着手日. */
  @Schema(name = "constrSiteStartYmd", description = "現場着手日(yyyy年MM年dd日 ～ yyyy年MM年dd日)")
  private String constrSiteStartYmd;

  /** 現場引渡日. */
  @Schema(name = "constrSiteDeliveryYmd", description = "現場引渡日(yyyy年MM年dd日 ～ yyyy年MM年dd日)")
  private String constrSiteDeliveryYmd;
}
