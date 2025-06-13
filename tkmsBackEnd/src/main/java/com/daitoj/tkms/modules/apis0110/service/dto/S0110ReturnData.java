package com.daitoj.tkms.modules.apis0110.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 承認一覧（再下請負通知書）検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0110ReturnData", description = "承認一覧（再下請負通知書）検索結果")
public class S0110ReturnData {

  /** 再下請負通知書承認情報リスト. */
  @Schema(
      name = "listSubconNotifApprInfo",
      description = "再下請負通知書承認情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<SubconNotifApprInfoDto> listSubconNotifApprInfo = new ArrayList<>();
}
