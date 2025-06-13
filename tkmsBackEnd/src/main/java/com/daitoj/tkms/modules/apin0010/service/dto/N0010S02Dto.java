package com.daitoj.tkms.modules.apin0010.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/** 工事予実一覧CSV出力パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "N0010S02Dto", description = "工事予実一覧CSV出力パラメータ")
public class N0010S02Dto extends N0010S01Dto {

  /** 利用PCのシステム日付 */
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
