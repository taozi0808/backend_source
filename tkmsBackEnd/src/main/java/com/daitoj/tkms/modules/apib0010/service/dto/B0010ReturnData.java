package com.daitoj.tkms.modules.apib0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.MitemSettingDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 案件一覧検索結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0010ReturnData", description = "案件一覧検索結果")
public class B0010ReturnData {

  /** 案件情報リスト */
  @Schema(
      name = "listAnkenInfo",
      description = "案件情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<AnkenInfoDto> listAnkenInfo = new ArrayList<>();

  /** マスタデータ(受注状態) */
  @Schema(name = "mitemSettingD0001", description = "スタデータ(受注状態)")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<MitemSettingDto> mitemSettingD0001;
}
