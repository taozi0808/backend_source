package com.daitoj.tkms.modules.apin0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.EmpSearchDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 工事予実入力選択項目取得結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "N0030S01ReturnData", description = "工事予実入力選択項目取得結果")
public class N0030S01ReturnData {
  /** 入力担当社リスト情報 */
  @Schema(description = "入力担当社リスト情報")
  List<EmpSearchDto> picList;
}
