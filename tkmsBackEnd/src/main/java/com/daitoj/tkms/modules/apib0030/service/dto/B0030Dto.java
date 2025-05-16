package com.daitoj.tkms.modules.apib0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/** 案件登録保存パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0030Dto", description = "案件登録保存パラメータ")
public class B0030Dto {

  /** 処理区分 */
  @NotNull
  @Size(max = 1)
  @Schema(description = "処理区分", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 1)
  private String shorikubun;

  /** 案件情報 */
  @Valid
  @NotNull
  @Schema(description = "案件情報", requiredMode = Schema.RequiredMode.REQUIRED)
  private ProjectDto projectInfo;

  /** 案件請求条件情報 */
  @Valid
  @Schema(description = "案件請求条件情報")
  private List<ProjectPaymentTermsDto> projectPaymentTerms;

  /** 現場棟明細情報 */
  @Valid
  @Schema(description = "現場棟明細情報")
  List<ProjectBuildingDtlDto> projectBuildingDtls;

  /** 案件要望明細情報 */
  @Valid
  @Schema(description = "案件要望明細情報")
  List<ProjectRequestDtlDto> projectRequestDtls;

  /** 先行作業明細情報 */
  @Valid
  @Schema(description = "先行作業明細情報")
  List<ProjectPreWorkDtlDto> projectPreWorkDtls;
}
