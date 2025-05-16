package com.daitoj.tkms.modules.apic0010.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 概算一覧検索結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0010ReturnData", description = "概算一覧検索結果")
public class C0010ReturnData {

  /** 概算情報リスト */
  @Schema(
      name = "listGaisanInfo",
      description = "概算情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listGaisanInfo")
  private List<GaisanInfoDto> listGaisanInfo = new ArrayList<>();
}
