package com.daitoj.tkms.modules.apiq0036.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 作業員名簿業者一覧検索結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "Q0036ReturnData", description = "作業員名簿業者一覧検索結果")
public class Q0036ReturnData {

  /** 作業員名簿業者情報リスト */
  @Schema(
      name = "listSagyouinInfo",
      description = "作業員名簿業者情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<SagyouinInfoDto> listSagyouinInfo = new ArrayList<>();


}
