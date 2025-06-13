package com.daitoj.tkms.modules.apir0040.service.dto;

import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.domain.MPosition;
import com.daitoj.tkms.modules.apid0010.service.dto.DetailedEstInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 社員一覧検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0040ReturnData", description = "社員一覧検索結果")
public class R0040ReturnData {

  /* 社員情報リスト */
  @Schema(
      name = "listEmpInfo",
      description = "社員情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<EmpInfoDto> listEmpInfo = new ArrayList<>();

  /* 性別 */
  @Schema(
      name = "listItemListSetting",
      description = "性別リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<MItemListSetting> listSexInfo = new ArrayList<>();

  /* 役職 */
  @Schema(name = "listPositionInfo", description = "役職リスト")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<MPosition> listPositionInfo = new ArrayList<>();

}
