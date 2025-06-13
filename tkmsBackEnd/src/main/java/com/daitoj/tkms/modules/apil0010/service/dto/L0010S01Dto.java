package com.daitoj.tkms.modules.apil0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 請求一覧パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "L0010S01Dto", description = "請求一覧パラメータ")
public class L0010S01Dto {

  /* 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  private String customerCd;

  /* 顧客名 */
  @Schema(name = "customerNm", description = "顧客名")
  private String customerNm;

  /* 物件コード */
  @Schema(name = "projectSiteCd", description = "物件コード")
  private String projectSiteCd;

  /*　物件名　*/
  @Schema(name = "projectSiteNm", description = "物件名")
  private String projectSiteNm;

  /* 請求No */
  @Schema(name = "invoiceNo", description = "請求No")
  private String invoiceNo;

  /* 物件着手日(開始) */
  @Schema(name = "constrStartYmdFrom", description = "物件着手日(開始)(yyyyMMdd)", example = "20250512")
  private String constrStartYmdFrom;

  /* 物件着手日(終了) */
  @Schema(name = "constrStartYmdTo", description = "物件着手日(終了)(yyyyMMdd)", example = "20250512")
  private String constrStartYmdTo;

  /* 物件引渡日(開始) */
  @Schema(name = "constrCompYmdFrom", description = "物件引渡日(開始)(yyyyMMdd)", example = "20250512")
  private String constrCompYmdFrom;

  /* 物件引渡日(終了) */
  @Schema(name = "constrCompYmdTo", description = "物件引渡日(終了)(yyyyMMdd)", example = "20250512")
  private String constrCompYmdTo;

  /* 表示対象(未請求) */
  @Schema(name = "displayItem1", description = "表示対象(未請求)")
  private String displayItem1;

  /* 表示対象(請求残有り) */
  @Schema(name = "displayItem2", description = "表示対象(請求残有り)")
  private String displayItem2;

  /* 表示対象(請求残無し) */
  @Schema(name = "displayItem3", description = "表示対象(請求残無し)")
  private String displayItem3;

  /* 表示履歴 */
  @Schema(name = "displayHistory", description = "表示履歴")
  private String displayHistory;

  /* 所属事務所コード */
  @Schema(name = "belongOfficeCd", description = "所属事務所コード")
  private String belongOfficeCd;

}
