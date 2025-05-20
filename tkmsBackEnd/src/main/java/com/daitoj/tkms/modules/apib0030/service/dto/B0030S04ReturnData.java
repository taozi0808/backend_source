package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.MinorWorkSearchDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 小工事選択項目取得結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0030S04ReturnData", description = "小工事選択項目取得結果")
public class B0030S04ReturnData {
  /** 小工事リスト */
  @Schema(description = "小工事リスト")
  List<MinorWorkSearchDto> minorWorkList;
}
