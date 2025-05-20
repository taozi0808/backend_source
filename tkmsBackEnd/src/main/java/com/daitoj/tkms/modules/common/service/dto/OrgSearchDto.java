package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 組織検索情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.OrgSearchDto", description = "組織検索情報")
public class OrgSearchDto {

  /** 組織ID */
  @Schema(description = "組織ID")
  private Long id;

  /** 組織コード */
  @Schema(description = "組織コード")
  private String orgCd;

  /** 組織名 */
  @Schema(description = "組織名")
  private String orgNm;
}
