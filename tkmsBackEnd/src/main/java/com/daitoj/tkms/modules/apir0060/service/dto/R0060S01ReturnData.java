package com.daitoj.tkms.modules.apir0060.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0060S01ReturnData", description = "検索結果")
public class R0060S01ReturnData {

  /** 顧客管理承認情報 */
  @Schema(
      name = "customerBankBankBanchInfoDto",
      description = "顧客情報",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private CustomerBankBankBanchInfoDto customerBankBankBanchInfoDto;

  /** 取引履歴情報リスト. */
  @Schema(
      name = "listProjectSiteInfoDto",
      description = "取引履歴情報",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  List<TProjectSiteInfoDto> listProjectSiteInfoDto = new ArrayList<>();
}
