package com.daitoj.tkms.modules.apis0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（概算管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0010ReturnData", description = "承認一覧（概算管理）検索結果")
public class S0010ReturnData {

  /**
   * 概算承認情報リスト.
   */
  @Schema(
      name = "listRoughEstApprInfo",
      description = "概算承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<RoughEstApprInfoDto> listRoughEstApprInfo = new ArrayList<>();
}
