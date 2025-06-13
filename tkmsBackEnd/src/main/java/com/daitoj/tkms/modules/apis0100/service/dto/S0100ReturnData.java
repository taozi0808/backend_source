package com.daitoj.tkms.modules.apis0100.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（下請契約台帳）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0100ReturnData", description = "承認一覧（下請契約台帳）検索結果")
public class S0100ReturnData {

  /**
   * 下請契約台帳承認情報リスト.
   */
  @Schema(
      name = "listSubConLedgerApprInfo",
      description = "下請契約台帳承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<SubConLedgerApprInfoDto> listSubConLedgerApprInfo = new ArrayList<>();
}
