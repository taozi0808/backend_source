package com.daitoj.tkms.modules.apij0010.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 査定支払情報検索結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "J0010ReturnData", description = "査定支払情報検索結果")
public class J0010ReturnData {

  /** 査定経費情報リスト */
  @Schema(
      name = "listSateiShiharaiInfoDto",
      description = "査定経費情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listSateiShiharaiInfoDto")
  private List<SateiShiharaiInfoDto> listSateiShiharaiInfoDto = new ArrayList<>();
}
