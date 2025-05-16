package com.daitoj.tkms.modules.apib0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 案件情報結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0030S02ReturnData", description = "案件情報結果")
public class B0030S02ReturnData {

  /** 案件情報 */
  @Schema(description = "案件情報")
  private ProjectResultDto projectInfo;

  /** 物件、概算情報 */
  @Schema(description = "物件、概算情報")
  private List<ProjectSiteDto> projectSites;

  /** 案件請求条件情報 */
  @Schema(description = "案件請求条件情報")
  private List<ProjectPaymentTermsDto> projectPaymentTerms;

  /** 現場棟明細情報 */
  @Schema(description = "現場棟明細情報")
  List<ProjectBuildingDtlDto> projectBuildingDtls;

  /** 案件要望明細情報 */
  @Schema(description = "案件要望明細情報")
  List<ProjectRequestDtlDto> projectRequestDtls;

  /** 先行作業明細情報 */
  @Schema(description = "先行作業明細情報")
  List<ProjectPreWorkDtlDto> projectPreWorkDtls;
}
