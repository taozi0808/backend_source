package com.daitoj.tkms.modules.apis0140.service.dto;

import com.daitoj.tkms.modules.apis0120.service.dto.CustomerApprInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * 承認一覧（自社情報）検索結果.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0140ReturnData", description = "承認一覧（自社情報）検索結果")
public class S0140ReturnData {

  /**
   * 業者情報承認情報リスト.
   */
  @Schema(
      name = "listVendorApprInfo",
      description = "業者情報承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<VendorApprInfoDto> listVendorApprInfo = new ArrayList<>();
}
