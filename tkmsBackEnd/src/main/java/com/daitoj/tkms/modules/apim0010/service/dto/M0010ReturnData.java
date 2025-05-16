package com.daitoj.tkms.modules.apim0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 現場経費一覧検索結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "M0010ReturnData", description = "現場経費一覧検索結果")
public class M0010ReturnData {

  /** 現場経費情報リスト */
  @Schema(
      name = "listGenbakeihiInfo",
      description = "現場経費情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<GenbakeihiInfoDto> listGenbakeihiInfo = new ArrayList<>();
}
