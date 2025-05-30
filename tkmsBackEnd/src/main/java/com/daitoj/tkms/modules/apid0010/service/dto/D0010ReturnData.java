package com.daitoj.tkms.modules.apid0010.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 精積算一覧検索結果. */
@Schema(name = "D0010ReturnData", description = "精積算一覧検索結果")
@lombok.Setter
@lombok.Getter
public class D0010ReturnData {

  /* 精積算情報リスト */
  @Schema(
      name = "listDetailedEstInfo",
      description = "精積算情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<DetailedEstResultDto> listDetailedEstInfo = new ArrayList<>();


}
