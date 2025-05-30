package com.daitoj.tkms.modules.apis0020.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（精積算管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0020ReturnData", description = "承認一覧（精積算管理）検索結果")
public class S0020ReturnData {

  /**
   * 精積算承認情報リスト.
   */
  @Schema(
      name = "listDetailedEstApprInfo",
      description = "精積算承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<DetailedEstApprInfoDto> listDetailedEstApprInfo = new ArrayList<>();
}
