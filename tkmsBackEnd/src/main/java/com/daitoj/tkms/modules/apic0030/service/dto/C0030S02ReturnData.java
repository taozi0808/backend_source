package com.daitoj.tkms.modules.apic0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.MitemSettingDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 単価情報リスト結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0030S02ReturnData", description = "単価情報リスト")
public class C0030S02ReturnData {

  /** 単価情報リスト */
  @Schema(
      name = "mPriceRegionInfoDtoList",
      description = "単価情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mPriceRegionInfoDtoList")
  private List<MPriceRegionInfoDto> mPriceRegionInfoDtoList;

}
