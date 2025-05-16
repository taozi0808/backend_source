package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 先行作業明細情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ProjectPreWorkDtlDto", description = "先行作業明細情報")
public class ProjectPreWorkDtlDto extends BaseDto {
  /** 案件ID */
  @Schema(description = "案件ID")
  private Long projectId;

  /** 連番 */
  @NotNull
  @Schema(description = "連番", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer seqNo;

  /** 単価ID */
  @Schema(description = "単価ID")
  private Long priceId;

  /** 大工事コード */
  @Schema(description = "大工事コード")
  private String majorWorkCd;

  /** 大工事名 */
  @Schema(description = "大工事名")
  private String majorWorkNm;

  /** 小工事コード */
  @Schema(description = "小工事コード")
  private String minorWorkCd;

  /** 小工事名 */
  @Schema(description = "小工事名")
  private String minorWorkNm;

  /** 計画概要 */
  @NotNull
  @Schema(description = "計画概要", requiredMode = Schema.RequiredMode.REQUIRED)
  private String outline;

  /** 先行工事内容 */
  @NotNull
  @Schema(description = "先行工事内容", requiredMode = Schema.RequiredMode.REQUIRED)
  private String preConstrContent;

  /** 工事原価 */
  @NotNull
  @Schema(description = "工事原価", requiredMode = Schema.RequiredMode.REQUIRED)
  private String constrCost;

  /** 支払条件 */
  @NotNull
  @Schema(description = "支払条件", requiredMode = Schema.RequiredMode.REQUIRED)
  private String paymentTerms;

  /** その他 */
  @NotNull
  @Schema(description = "その他", requiredMode = Schema.RequiredMode.REQUIRED)
  private String sonota;

  /** 添付ファイルID */
  @NotNull
  @Schema(description = "添付ファイルID", requiredMode = Schema.RequiredMode.REQUIRED)
  private Long fileId;
}
