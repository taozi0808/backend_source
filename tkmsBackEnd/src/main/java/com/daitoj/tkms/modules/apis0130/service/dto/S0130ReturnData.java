package com.daitoj.tkms.modules.apis0130.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（会社管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0130ReturnData", description = "承認一覧（会社管理）検索結果")
public class S0130ReturnData {

  /**
   * 会社管理承認情報リスト.
   */
  @Schema(
      name = "listEmpApprInfo",
      description = "会社管理承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<EmpApprInfoDto> listEmpApprInfo = new ArrayList<>();
}
