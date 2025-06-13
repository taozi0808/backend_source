package com.daitoj.tkms.modules.apil0010.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/** 請求書一覧CSV出力パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "l0010S02Dto", description = "請求書一覧CSV出力パラメータ")
public class L0010S02Dto extends L0010S01Dto {

  /** 利用PCのシステム日付. */
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime sysDate;

}
