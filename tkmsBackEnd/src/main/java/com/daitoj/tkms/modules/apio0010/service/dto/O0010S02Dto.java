package com.daitoj.tkms.modules.apio0010.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/** 業者一覧csv出力パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "O0010S02Dto", description = "業者一覧CSV出力パラメータ")
public class O0010S02Dto extends O0010S01Dto {

  /** 利用PCのシステム日付. */
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime sysDate;

}
