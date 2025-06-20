package com.daitoj.tkms.modules.apic0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;

/** 添付ファイルリスト */
@lombok.Getter
@lombok.Setter
@Schema(name = "RoughEstFileDtlInfoDto", description = "添付ファイルリスト")
public class RoughEstFileDtlInfoDto {

  /** 添付ファイルID */
  @Schema(name = "fileId", description = "添付ファイルID")
  private UUID fileId;

  /** ファイル */
  @Schema(name = "file", description = "ファイル")
  private String file;

  /** 概算ヘッダID */
  @Schema(name = "roughEstHid", description = "概算ヘッダID")
  private Long roughEstHid;

  /** コンストラクタ */
  public RoughEstFileDtlInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param fileId 添付ファイルID
   * @param file ファイル
   * @param roughEstHid 概算ヘッダID
   */
  public RoughEstFileDtlInfoDto(UUID fileId, String file, Long roughEstHid) {
    this.fileId = fileId;
    this.file = file;
    this.roughEstHid = roughEstHid;
  }
}
