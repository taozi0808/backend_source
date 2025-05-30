package com.daitoj.tkms.modules.apis0070.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（工事予実管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0070ReturnData", description = "承認一覧（工事予実管理）検索結果")
public class S0070ReturnData {

  /**
   * 工事予実管理承認情報リスト.
   */
  @Schema(
      name = "listConstrWbsApprInfo",
      description = "工事予実管理承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<ConstrWbsApprInfoDto> listConstrWbsApprInfo = new ArrayList<>();
}
