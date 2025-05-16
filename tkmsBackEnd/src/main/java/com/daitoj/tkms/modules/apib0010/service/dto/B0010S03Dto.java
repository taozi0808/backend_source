package com.daitoj.tkms.modules.apib0010.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/** 案件一覧CSV出力パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0010S03Dto", description = "案件一覧CSV出力パラメータ")
public class B0010S03Dto extends B0010S01Dto {

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

  /** 受注状態リスト */
  @Valid
  @NotNull
  @Size(min = 1)
  @Schema(
      name = "listJyucyuuJyoutai",
      description = "受注状態リスト",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private List<String> listJyucyuuJyoutai;
}
