package com.daitoj.tkms.modules.apis0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（物件管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0030ReturnData", description = "承認一覧（物件管理）検索結果")
public class S0030ReturnData {

  /**
   * 物件管理承認情報リスト.
   */
  @Schema(
      name = "listProjectSiteApprInfo",
      description = "物件管理承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<ProjectSiteApprInfoDto> listProjectSiteApprInfo = new ArrayList<>();
}
