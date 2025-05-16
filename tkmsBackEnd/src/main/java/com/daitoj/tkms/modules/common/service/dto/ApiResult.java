package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** API結果情報 */
@Schema(name = "ApiResult", description = "API結果情報")
public class ApiResult<T> {

  /**
   * Get データ
   *
   * @return data
   */
  @Schema(name = "data", description = "データ")
  private T data;

  /**
   * ページネーション等の付加情報
   *
   * @return message
   */
  @Schema(name = "meta", description = "ページネーション等の付加情報")
  private PaginationMeta meta;

  /**
   * エラー情報
   *
   * @return message
   */
  @Schema(name = "error", description = "エラー情報")
  private ErrorInfo error;

  /** コンストラクタ */
  public ApiResult() {}

  /** コンストラクタ */
  public ApiResult(T data, PaginationMeta meta, ErrorInfo error) {
    this.data = data;
    this.meta = meta;
    this.error = error;
  }

  /** 成功 */
  public static <T> ApiResult<T> success() {
    return new ApiResult<>(null, null, null);
  }

  /** 成功 */
  public static <T> ApiResult<T> success(T data) {
    return new ApiResult<>(data, null, null);
  }

  /** 成功 */
  public static <T> ApiResult<T> success(T data, PaginationMeta meta) {
    return new ApiResult<>(data, meta, null);
  }

  /** 失敗 */
  public static <T> ApiResult<T> error(String code, String message) {
    return new ApiResult<>(null, null, new ErrorInfo(code, message));
  }

  /** 失敗 */
  public static <T> ApiResult<T> error(String code, String message, String path) {
    return new ApiResult<>(null, null, new ErrorInfo(code, message, path));
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public PaginationMeta getMeta() {
    return meta;
  }

  public void setMeta(PaginationMeta meta) {
    this.meta = meta;
  }

  public ErrorInfo getError() {
    return error;
  }

  public void setError(ErrorInfo error) {
    this.error = error;
  }
}
