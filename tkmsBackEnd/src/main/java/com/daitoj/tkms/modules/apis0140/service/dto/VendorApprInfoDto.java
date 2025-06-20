package com.daitoj.tkms.modules.apis0140.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 承認一覧（自社情報）情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "VendorApprInfoDto", description = "検索結果")
public class VendorApprInfoDto {

  /** 協力業者コード. */
  @Schema(name = "partnerVendorCd", description = "協力業者コード")
  private String partnerVendorCd;

  /** 会社名. */
  @Schema(name = "compNm", description = "会社名")
  private String compNm;

  /** 会社カナ名. */
  @Schema(name = "compKnNm", description = "会社カナ名")
  private String compKnNm;

  /** 支店名. */
  @Schema(name = "branchNm", description = "支店名")
  private String branchNm;

  /** 支店カナ名. */
  @Schema(name = "branchKnNm", description = "支店カナ名")
  private String branchKnNm;

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

  /** 項目コード. */
  @Schema(name = "itemCd", description = "項目コード")
  private String itemCd;

  /**
   * コンストラクタ.
   *
   * @param partnerVendorCd 協力業者コード
   * @param compNm 会社名
   * @param compKnNm 会社カナ名
   * @param branchNm 支店名
   * @param branchKnNm 支店カナ名
   * @param requestTs 申請日
   * @param finalApprDt 最終承認日
   * @param itemValue 結果
   * @param comment コメント
   * @param itemCd 項目コード
   */
  public VendorApprInfoDto(
      String partnerVendorCd,
      String compNm,
      String compKnNm,
      String branchNm,
      String branchKnNm,
      String requestTs,
      String finalApprDt,
      String itemValue,
      String comment,
      String itemCd) {
    this.partnerVendorCd = partnerVendorCd;
    this.compNm = compNm;
    this.compKnNm = compKnNm;
    this.branchNm = branchNm;
    this.branchKnNm = branchKnNm;
    this.requestTs = requestTs;
    this.finalApprDt = finalApprDt;
    this.itemValue = itemValue;
    this.comment = comment;
    this.itemCd = itemCd;
  }
}
