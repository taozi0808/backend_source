package com.daitoj.tkms.modules.apis0100.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 承認一覧（下請契約台帳）情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "SubConLedgerApprInfoDto", description = "検索結果")
public class SubConLedgerApprInfoDto {

  /** 物件コード. */
  @Schema(name = "projectSiteCd", description = "物件コード")
  private String projectSiteCd;

  /** 物件名. */
  @Schema(name = "projectSiteNm", description = "物件名")
  private String projectSiteNm;

  /** 物件カナ名. */
  @Schema(name = "projectSiteNm", description = "物件カナ名")
  private String projectSiteKnNm;

  /** 物件住所. */
  @Schema(name = "projectSiteAddr", description = "物件住所")
  private String projectSiteAddr;

  /** 申請者. */
  @Schema(name = "requestEmpNm", description = "申請者")
  private String requestEmpNm;

  /** 申請日. */
  @Schema(name = "requestTs", description = "申請日")
  private String requestTs;

  /** 最終承認日. */
  @Schema(name = "finalApprDt", description = "最終承認日")
  private String finalApprDt;

  /** 結果. */
  @Schema(name = "itemValue", description = "結果")
  private String itemValue;

  /** コメント. */
  @Schema(name = "comment", description = "コメント")
  private String comment;


  /**
   * 項目コード.
   */
  @Schema(name = "itemCd", description = "項目コード")
  private String itemCd;

  /** コンストラクタ. */
  public SubConLedgerApprInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param projectSiteCd 物件コード
   * @param projectSiteNm 物件名
   * @param projectSiteAddr 物件住所
   * @param requestEmpNm 申請者
   * @param requestTs 申請日
   * @param finalApprDt 最終承認日
   * @param itemValue 結果
   * @param comment コメント
   */
  public SubConLedgerApprInfoDto(
      String projectSiteCd,
      String projectSiteNm,
      String projectSiteKnNm,
      String projectSiteAddr,
      String requestEmpNm,
      String requestTs,
      String finalApprDt,
      String itemValue,
      String comment,
      String itemCd) {
    this.projectSiteCd = projectSiteCd;
    this.projectSiteNm = projectSiteNm;
    this.projectSiteKnNm = projectSiteKnNm;
    this.projectSiteAddr = projectSiteAddr;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.finalApprDt = finalApprDt;
    this.itemValue = itemValue;
    this.comment = comment;
    this.itemCd = itemCd;
  }
}
