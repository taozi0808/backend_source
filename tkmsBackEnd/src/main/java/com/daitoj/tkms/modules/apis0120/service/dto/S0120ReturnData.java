package com.daitoj.tkms.modules.apis0120.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（顧客管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0120ReturnData", description = "承認一覧（顧客管理）検索結果")
public class S0120ReturnData {

  /**
   * 顧客管理承認情報リスト.
   */
  @Schema(
      name = "listCustomerApprInfo",
      description = "顧客管理承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<CustomerApprInfoDto> listCustomerApprInfo = new ArrayList<>();
}
