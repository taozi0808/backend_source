package com.daitoj.tkms.modules.apig0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** 実行予算一覧検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "G0010ReturnData", description = "実行予算一覧検索結果")
public class G0010ReturnData {

  /** 実行予算情報リスト. */
  @Schema(
      name = "listJikkouyosanInfo",
      description = "実行予算情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<ExecBgInfoDto> jikkouyosanInfo = new ArrayList<>();

}
