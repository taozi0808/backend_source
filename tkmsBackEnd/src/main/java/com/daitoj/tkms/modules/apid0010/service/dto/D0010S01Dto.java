package com.daitoj.tkms.modules.apid0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 精積算一覧パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "D0010S01Dto", description = "精積算一覧パラメータ")
public class D0010S01Dto {

  /* 案件コード */
  @Schema(name = "projectCd", description = "案件コード")
  private String projectCd;

  /* 案件名 */
  @Schema(name = "projectNm", description = "案件名")
  private String projectNm;

  /* 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  private String customerCd;

  /* 顧客名 */
  @Schema(name = "customerNm", description = "顧客名")
  private String customerNm;

  /* 概算コード */
  @Schema(name = "roughEstCd", description = "概算コード")
  private String roughEstCd;

  /* 精積算コード */
  @Schema(name = "detailedEstCd", description = "精積算コード")
  private String detailedEstCd;

  /* 精積算部門 */
  @Schema(name = "detailedEstOrgNm", description = "精積算部門名")
  private String detailedEstOrgNm;

  /* 精積算担当者 */
  @Schema(name = "detailedEstPicNm", description = "精積算担当者名")
  private String detailedEstPicNm;

  /* 精積算作成済フラグ */
  @NotNull
  @Schema(name = "detailedEstCreateFlg", description = "精積算作成済フラグ")
  private String detailedEstCreateFlg;

}
