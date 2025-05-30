package com.daitoj.tkms.modules.apis0090.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（作業員情報管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0090ReturnData", description = "承認一覧（作業員情報管理）検索結果")
public class S0090ReturnData {

  /**
   * 作業員情報管理承認情報リスト.
   */
  @Schema(
      name = "listWorkerApprInfo",
      description = "作業員情報管理承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<WorkerApprInfoDto> listWorkerApprInfo = new ArrayList<>();
}
