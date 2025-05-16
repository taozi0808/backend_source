package com.daitoj.tkms.modules.apia0030.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 個人設定API結果情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "A0030ApiResult", description = "個人設定API結果情報")
public class A0030ApiResult {

  /** ステータス */
  @Schema(description = "ステータス")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String status;

  /** メッセージ */
  @Schema(description = "メッセージ")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String message;

  /** データリスト */
  @Schema(description = "データリスト")
  private List<Object> data = new ArrayList<>();
}
