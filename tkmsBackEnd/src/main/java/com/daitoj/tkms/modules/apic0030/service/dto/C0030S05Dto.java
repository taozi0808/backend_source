package com.daitoj.tkms.modules.apic0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/** 概算情報印刷パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0030S05Dto", description = "概算情報印刷パラメータ")
public class C0030S05Dto extends RoughInfoDto {

  /** 利用PCのシステム日付 */
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String sysDate;
}
