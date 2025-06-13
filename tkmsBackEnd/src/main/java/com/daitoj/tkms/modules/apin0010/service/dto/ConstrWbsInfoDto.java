package com.daitoj.tkms.modules.apin0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 工事予実情報. */
@lombok.Setter
@lombok.Getter
@Schema(name = "ConstrWbsInfoDto", description = "検索結果")
public class ConstrWbsInfoDto {
  /** id. */
  @Schema(name = "id", description = "id")
  private Long id;

  /** 現場コード. */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /** 現場名. */
  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  /** 現場着手日. */
  @Schema(name = "constrSiteStartYmd", description = "現場着手日")
  private String constrSiteStartYmd;

  /** 現場引渡日. */
  @Schema(name = "constrSiteDeliveryYmd", description = "現場引渡日")
  private String constrSiteDeliveryYmd;

  /** 予実作成日. */
  @Schema(name = "wbsCreateDt", description = "予実作成日")
  private String wbsCreateDt;

  /** 入力担当者コード. */
  @Schema(name = "createPicCd", description = "入力担当者コード")
  private String createPicCd;

  /** 入力担当者. */
  @Schema(name = "createPicName", description = "入力担当者")
  private String empNm;

  /** 現場名ｶﾅ. */
  @Schema(name = "constrSiteKnNm", description = "現場名ｶﾅ")
  private String constrSiteKnNm;

  /** TODO業者名ｶﾅ. */
  @Schema(name = "compKnNm", description = "業者名ｶﾅ")
  private String compKnNm;

  /** コンストラクタ. */
  public ConstrWbsInfoDto(
      Long id,
      String constrSiteCd,
      String constrSiteNm,
      String constrSiteStartYmd,
      String constrSiteDeliveryYmd,
      String wbsCreateDt,
      String createPicCd,
      String empNm,
      String constrSiteKnNm) {
    this.id = id;
    this.constrSiteCd = constrSiteCd;
    this.constrSiteNm = constrSiteNm;
    this.constrSiteStartYmd = constrSiteStartYmd;
    this.constrSiteDeliveryYmd = constrSiteDeliveryYmd;
    this.wbsCreateDt = wbsCreateDt;
    this.createPicCd = createPicCd;
    this.empNm = empNm;
    this.constrSiteKnNm = constrSiteKnNm;
  }
}
