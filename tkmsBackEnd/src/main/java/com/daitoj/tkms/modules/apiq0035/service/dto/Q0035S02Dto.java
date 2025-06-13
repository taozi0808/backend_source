package com.daitoj.tkms.modules.apiq0035.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/** 作業員名簿物件情報一覧CSV出力パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "Q0035S02Dto", description = "作業員名簿物件情報一覧CSV出力パラメータ")
public class Q0035S02Dto extends Q0035S01Dto {

  /** 利用PCのシステム日付. */
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy-MM-dd HH:mm:ss)",
      example = "2025-01-02 13:14:15",
      type = "string",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private LocalDateTime sysDate;
}
