package com.daitoj.tkms.modules.apis0060.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 承認一覧（現場経費管理）情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "ConstrSiteExpApprInfoDto", description = "検索結果")
public class ConstrSiteExpApprInfoDto {
  /** 現場/案件コード. */
  @Schema(name = "constrSiteOrProjectCd", description = "現場/案件コード")
  private String constrSiteOrProjectCd;

  /** 現場/案件名. */
  @Schema(name = "constrSiteOrProjectNm", description = "現場/案件名")
  private String constrSiteOrProjectNm;

  /** 現場/案件カナ名. */
  @Schema(name = "constrSiteOrProjectNm", description = "現場/案件カナ名")
  private String constrSiteOrProjectKnNm;

  /** 現場着手日. */
  @Schema(name = "constrSiteStartYmd", description = "現場着手日")
  private String constrSiteStartYmd;

  /** 現場引渡日. */
  @Schema(name = "constrSiteDeliveryYmd", description = "現場引渡日")
  private String constrSiteDeliveryYmd;

  /** 申請者. */
  @Schema(name = "requestEmpNm", description = "申請者")
  private String requestEmpNm;

  /** 申請日. */
  @Schema(name = "requestTs", description = "申請日")
  private String requestTs;

  /** 結果. */
  @Schema(name = "itemValue", description = "結果")
  private String itemValue;

  /** コメント. */
  @Schema(name = "comment", description = "コメント")
  private String comment;

  /** 項目コード. */
  @Schema(name = "itemCd", description = "項目コード")
  private String itemCd;

  /** 最終承認日. */
  @Schema(name = "itemCd", description = "最終承認日")
  private String finalApprDt;

  /** コンストラクタ. */
  public ConstrSiteExpApprInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param constrSiteOrProjectCd 現場/案件コード
   * @param constrSiteOrProjectNm 現場/案件名
   * @param constrSiteOrProjectKnNm 現場/案件カナ名
   * @param constrSiteStartYmd 現場着手日
   * @param constrSiteDeliveryYmd 現場引渡日
   * @param requestTs 申請日
   * @param itemValue 結果
   * @param comment コメント
   * @param itemCd 結果コード
   * @param finalApprDt 最終承認日
   */
  public ConstrSiteExpApprInfoDto(
      String constrSiteOrProjectCd,
      String constrSiteOrProjectNm,
      String constrSiteOrProjectKnNm,
      String constrSiteStartYmd,
      String constrSiteDeliveryYmd,
      String requestEmpNm,
      String requestTs,
      String itemValue,
      String comment,
      String itemCd,
      String finalApprDt) {
    this.constrSiteOrProjectCd = constrSiteOrProjectCd;
    this.constrSiteOrProjectNm = constrSiteOrProjectNm;
    this.constrSiteOrProjectKnNm = constrSiteOrProjectKnNm;
    this.constrSiteStartYmd = constrSiteStartYmd;
    this.constrSiteDeliveryYmd = constrSiteDeliveryYmd;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.itemValue = itemValue;
    this.comment = comment;
    this.itemCd = itemCd;
    this.finalApprDt = finalApprDt;
  }
}
