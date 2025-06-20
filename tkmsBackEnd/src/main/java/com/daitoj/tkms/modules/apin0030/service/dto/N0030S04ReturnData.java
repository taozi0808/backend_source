package com.daitoj.tkms.modules.apin0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 工事予実入力保存結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "N0030S04ReturnData", description = "工事予実入力情報保存結果 ")
public class N0030S04ReturnData {

  /** 現場コード. */
  @Schema(name = "constrSiteCd", description = "現場コード")
  protected String constrSiteCd;
}
