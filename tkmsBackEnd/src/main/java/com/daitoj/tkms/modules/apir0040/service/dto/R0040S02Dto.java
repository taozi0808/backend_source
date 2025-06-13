package com.daitoj.tkms.modules.apir0040.service.dto;

import com.daitoj.tkms.modules.apid0010.service.dto.D0010S01Dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/** 社員一覧CSV出力パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0040S02Dto", description = "社員一覧CSV出力パラメータ")
public class R0040S02Dto extends R0040S01Dto {

  /* 利用PCのシステム日付 */
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
