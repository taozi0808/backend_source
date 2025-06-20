package com.daitoj.tkms.modules.apif0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 物件一覧パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "F0010S01Dto", description = "物件一覧パラメータ")
public class F0010S01Dto {

  /* 物件コード */
  @Schema(name = "projectSiteCd", description = "物件コード")
  private String projectSiteCd;

  /* 物件名（ｶﾅ含む） */
  @Schema(name = "projectSiteNm", description = "物件名（ｶﾅ含む）")
  private String projectSiteNm;

  /* 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  private String customerCd;

  /* 顧客名（ｶﾅ含む） */
  @Schema(name = "customerNm", description = "顧客名")
  private String customerNm;

  /* 受注年月日（開始） */
  @Schema(name = "orderYmdFrom", description = "受注年月日(開始)")
  private String orderYmdFrom;

  /* 受注年月日（終了） */
  @Schema(name = "orderYmdTo", description = "受注年月日(終了)")
  private String orderYmdTo;

  /* 担当事業所 */
  @Schema(name = "icOfficeNm", description = "担当事業所")
  private String icOfficeNm;

  /* 着手日（開始）*/
  @Schema(name = "constrStartYmdFrom", description = "着手日(開始)")
  private String constrStartYmdFrom;

  /* 着手日（終了）*/
  @Schema(name = "constrStartYmdTo", description = "着手日（終了）")
  private String constrStartYmdTo;

  /* 完工日（開始）*/
  @Schema(name = "constrCompYmdStart", description = "完工日(開始)")
  private String constrCompYmdFrom;

  /* 完工日（終了）*/
  @Schema(name = "constrCompYmdTo", description = "完工日(終了)")
  private String constrCompYmdTo;

  /* 工事部門名 */
  @Schema(name = "constrOrgNm", description = "工事部門名")
  private String constrOrgNm;

  /* 工事管理職名 */
  @Schema(name = "constrMgrNm", description = "工事管理職名")
  private String constrMgrNm;

  /* 専任技術者名 */
  @Schema(name = "ftEngineerNm", description = "専任技術者名")
  private String ftEngineerNm;

  /* 施工担当者名 */
  @Schema(name = "constrPicNm", description = "施工担当者名")
  private String constrPicNm;

  /* 所属事業所コード */
  @Schema(name = "belongOfficeCd", description = "所属事業所コード")
  private String belongOfficeCd;

}
