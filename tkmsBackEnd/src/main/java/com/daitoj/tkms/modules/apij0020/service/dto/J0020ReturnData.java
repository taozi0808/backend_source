package com.daitoj.tkms.modules.apij0020.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 査定支払情報検索結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "J0020ReturnData", description = "査定支払情報検索結果")
public class J0020ReturnData {

  /** 現場経費情報リスト */
  @Schema(
      name = "listKeihiShiharaiInfoDto",
      description = "現場経費情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listKeihiShiharaiInfoDto")
  private List<KeihiShiharaiInfoDto> listKeihiShiharaiInfoDto = new ArrayList<>();
}
