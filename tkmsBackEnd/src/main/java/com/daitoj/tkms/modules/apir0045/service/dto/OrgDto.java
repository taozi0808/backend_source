package com.daitoj.tkms.modules.apir0045.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 組織情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "OrgDto", description = "組織情報")
public class OrgDto {
  /** 組織ID */
  @NotNull
  @Schema(description = "組織ID", requiredMode = Schema.RequiredMode.REQUIRED)
  private Long id;

  /** 組織改定ID */
  @Schema(description = "組織改定ID(表示のみ)")
  private Long orgRevId;

  /** 組織コード */
  @Schema(description = "組織コード(表示のみ)")
  private String orgCd;

  /** 組織名 */
  @Schema(description = "組織名(表示のみ)")
  private String orgNm;

  /** 表示順 */
  @Schema(description = "表示順(表示のみ)")
  private Integer displayOrder;
}
