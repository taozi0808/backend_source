package com.daitoj.tkms.modules.common.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/** エラー情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ErrorInfo", description = "エラー情報")
public class ErrorInfo {
  /** メッセージコード */
  @Schema(name = "code", description = "メッセージコード")
  private String code;

  /** メッセージ */
  @Schema(name = "message", description = "メッセージ")
  private String message;

  /** エラー発生エンドポイント */
  @Schema(name = "path", description = "エラー発生エンドポイント")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String path;

  /** コンストラクタ */
  public ErrorInfo() {}

  /** コンストラクタ */
  public ErrorInfo(String code, String message) {
    this.code = code;
    this.message = message;
  }

  /** コンストラクタ */
  public ErrorInfo(String code, String message, String path) {
    this.code = code;
    this.message = message;
    this.path = path;
  }
}
