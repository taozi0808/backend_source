package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;

/** ファイルアップロード情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "StorageUploadResult", description = "ファイルアップロード情報")
public class StorageUploadResult {

  /** ファイルID */
  @Schema(description = "ファイルID")
  private List<UUID> uuid;
}
