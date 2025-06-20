package com.daitoj.tkms.modules.apic0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.MajorWorkSearchDto;
import com.daitoj.tkms.modules.common.service.dto.MinorWorkSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 大工事/小工事結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0030S03ReturnData", description = "大工事/小工事結果")
public class C0030S03ReturnData {

  /** 大工事リスト */
  @Schema(
      name = "listMajorWorkSearchDto",
      description = "大工事リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listMajorWorkSearchDto")
  private List<MajorWorkSearchDto> listMajorWorkSearchDto = new ArrayList<>();

  /** 小工事リスト */
  @Schema(
      name = "listMinorWorkSearchDto",
      description = "小工事リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listMinorWorkSearchDto")
  private List<MinorWorkSearchDto> listMinorWorkSearchDto = new ArrayList<>();

}
