package com.daitoj.tkms.modules.apis0050.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（査定管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0050ReturnData", description = "承認一覧（査定管理）検索結果")
public class S0050ReturnData {

  /**
   * 査定管理承認情報リスト.
   */
  @Schema(
      name = "listAssessApprInfo",
      description = "査定管理承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<AssessApprInfoDto> listAssessApprInfo = new ArrayList<>();
}
