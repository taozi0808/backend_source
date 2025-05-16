package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 組織情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.OrgDto", description = "組織情報")
public class OrgDto {
  /** 組織ID */
  @Schema(description = "組織ID")
  private Long id;

  /** 組織改定ID */
  @Schema(description = "組織改定ID")
  private Long orgRevId;

  /** 組織コード */
  @Schema(description = "組織コード")
  private String orgCd;

  /** 組織名 */
  @Schema(description = "組織名")
  private String orgNm;

  /** 上位組織ID */
  @Schema(description = "上位組織ID")
  private Long parentOrgId;

  /** 階層 */
  @Schema(description = "階層")
  private Integer lvl;

  /** 表示順 */
  @Schema(description = "表示順")
  private Integer displayOrder;
}
