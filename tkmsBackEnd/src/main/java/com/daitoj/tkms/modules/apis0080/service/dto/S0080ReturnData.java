package com.daitoj.tkms.modules.apis0080.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（協力業者管理）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0080ReturnData", description = "承認一覧（協力業者管理）検索結果")
public class S0080ReturnData {

  /**
   * 協力業者管理承認情報リスト.
   */
  @Schema(
      name = "listPartnerVendorApprInfo",
      description = "協力業者管理承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<PartnerVendorApprInfoDto> listPartnerVendorApprInfo = new ArrayList<>();
}
