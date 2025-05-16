package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** ファイルアップロード情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "MItemListSettingResult", description = "ファイルアップロード情報")
public class StorageUploadResult {

  /** ファイル名 */
  @Schema(description = "ファイル名")
  private String fileName;

  /** オブジェクト名 */
  @Schema(description = "オブジェクト名")
  private String objectName;

    /**
     * コンストラクタ
     *
     * @param fileName ファイル名
     * @param objectName オブジェクト名
     */
  public StorageUploadResult(String fileName, String objectName) {
    this.fileName = fileName;
    this.objectName = objectName;
  }
}
