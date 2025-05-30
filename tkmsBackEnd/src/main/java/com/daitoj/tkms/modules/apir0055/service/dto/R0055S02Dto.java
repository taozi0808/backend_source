package com.daitoj.tkms.modules.apir0055.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/** 顧客情報一覧csv出力パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0055S02Dto", description = "顧客情報一覧CSV出力パラメータ")
public class R0055S02Dto extends R0055S01Dto {
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime sysDate;
}
