package com.daitoj.tkms.modules.apif0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 物件一覧検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "F0010ReturnData", description = "物件一覧検索結果")
public class F0010ReturnData {

  /* 物件結果情報リスト */
  @Schema(
      name = "listProjectSiteInfo",
      description = "物件結果情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<ProjectSiteInfoDto> listProjectSiteInfo = new ArrayList<>();

}
