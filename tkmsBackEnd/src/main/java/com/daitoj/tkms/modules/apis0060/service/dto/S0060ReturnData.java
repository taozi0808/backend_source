package com.daitoj.tkms.modules.apis0060.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（現場経費管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0060ReturnData", description = "承認一覧（現場経費管理）検索結果")
public class S0060ReturnData {

  /**
   * 現場経費管理承認情報リスト.
   */
  @Schema(
      name = "listConstrSiteExpApprInfo",
      description = "現場経費管理承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<ConstrSiteExpApprInfoDto> listConstrSiteExpApprInfo = new ArrayList<>();
}
