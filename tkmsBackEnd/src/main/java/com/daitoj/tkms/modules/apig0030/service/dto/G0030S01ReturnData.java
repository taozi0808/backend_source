package com.daitoj.tkms.modules.apig0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 実行予算選択項目取得結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "G0030S01ReturnData", description = "実行予算選択項目取得結果")
public class G0030S01ReturnData {

  /** 部門リスト情報. */
  @Schema(description = "部門リスト情報")
  List<?> orgList;

  /** 工事データリスト情報. */
  @Schema(description = "工事データリスト情報")
  List<?> workList;

  /** 単位リスト情報. */
  @Schema(description = "単位リスト情報")
  List<?> unitList;
}
