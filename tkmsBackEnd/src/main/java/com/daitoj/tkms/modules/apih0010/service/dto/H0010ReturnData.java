package com.daitoj.tkms.modules.apih0010.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 査定一覧検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "H0010ReturnData", description = "査定一覧検索結果")
public class H0010ReturnData {

  /** 査定情報リスト. */
  @Schema(
      name = "listAssessInfo",
      description = "査定情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listAssessInfo")
  private List<AssessInfoDto> listAssessInfo = new ArrayList<>();
}
