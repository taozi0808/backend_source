package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** ファイルURL情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "StorageUrlResult", description = "ファイルURL情報")
public class StorageUrlResult {
  /** オブジェクト名 */
  @Schema(description = "オブジェクト名")
  private String objectName;

  /** URL */
  @Schema(description = "URL")
  private String url;

  /**
   * コンストラクタ
   *
   * @param objectName オブジェクト名
   * @param url url
   */
  public StorageUrlResult(String objectName, String url) {
    this.objectName = objectName;
    this.url = url;
  }
}
