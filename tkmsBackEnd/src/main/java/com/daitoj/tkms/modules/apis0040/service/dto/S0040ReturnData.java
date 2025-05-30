package com.daitoj.tkms.modules.apis0040.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（実行予算管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0040ReturnData", description = "承認一覧（実行予算管理）検索結果")
public class S0040ReturnData {

  /**
   * 実行予算管理承認情報リスト.
   */
  @Schema(
      name = "listExecBgtApprInfo",
      description = "実行予算管理承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<ExecBgtApprInfoDto> listExecBgtApprInfo = new ArrayList<>();
}
