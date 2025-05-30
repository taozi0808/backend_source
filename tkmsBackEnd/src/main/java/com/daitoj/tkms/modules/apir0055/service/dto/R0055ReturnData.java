package com.daitoj.tkms.modules.apir0055.service.dto;

import com.daitoj.tkms.domain.MItemListSetting;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 顧客一覧の検索結果. */
@lombok.Setter
@lombok.Getter
@Schema(name = "R0055ReturnData", description = "顧客一覧検索結果")
public class R0055ReturnData {

  /* 顧客情報リスト */
  @Schema(
      name = "listCustomerResultInfo",
      description = "顧客情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<CustomerResultDto> listCustomerResultInfo = new ArrayList<>();

  /* 取引先区分 */
  @Schema(
      name = "tradingKList",
      description = "取引先区分リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<MItemListSetting> tradingKList = new ArrayList<>();

  /* 業種・業態 */
  @Schema(
      name = "gyousyuGyoutaiList",
      description = "業種・業態リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<MItemListSetting> gyousyuGyoutaiList = new ArrayList<>();

}
