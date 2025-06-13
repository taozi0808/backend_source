package com.daitoj.tkms.modules.apin0010.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 工事予実一覧検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "N0010ReturnData", description = "工事予実一覧検索結果")
public class N0010ReturnData {
  /** 工事予実情報リスト. */
  @Schema(
      name = "listConstrWbs",
      description = "工事予実情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ConstrWbsInfoDto> listConstrWbsInfo = new ArrayList<>();
}
