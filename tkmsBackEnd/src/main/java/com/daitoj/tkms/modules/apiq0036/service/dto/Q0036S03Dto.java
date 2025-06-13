package com.daitoj.tkms.modules.apiq0036.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/** 作業員名簿業者一覧印刷パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "Q0036S03Dto", description = "作業員名簿業者一覧印刷パラメータ")
public class Q0036S03Dto extends Q0036S01Dto {
  /** 利用PCのシステム日付 */
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM年dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;




}
