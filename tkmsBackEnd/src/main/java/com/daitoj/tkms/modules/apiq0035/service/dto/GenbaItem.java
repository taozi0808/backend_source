package com.daitoj.tkms.modules.apiq0035.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/** 表示対象. */
@lombok.Getter
@lombok.Setter
@Schema(name = "GenbaItem", description = "表示対象")
public class GenbaItem {
  /** 表示対象. */
  @Schema(name = "hyoujiTaisyo", description = "表示対象", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hyoujiTaisyo")
  private String hyoujiTaisyo;
}
