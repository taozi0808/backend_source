package com.daitoj.tkms.modules.apis0110.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 承認一覧（下請契約台帳）情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "SubconNotifApprInfoDto", description = "検索結果")
public class SubconNotifApprInfoDto {

  /** 元請業者コード. */
  @Schema(name = "parentPartnerVendorCd", description = "元請業者コード")
  private String parentPartnerVendorCd;

  /** 元請業者. */
  @Schema(name = "parentPartnerVendorNm", description = "元請業者")
  private String parentPartnerVendorNm;

  /** 元請業者カナ名. */
  @Schema(name = "parentPartnerVendorKnNm", description = "元請業者カナ名")
  private String parentPartnerVendorKnNm;

  /** 下請業者コード. */
  @Schema(name = "childPartnerVendorCd", description = "下請業者コード")
  private String childPartnerVendorCd;

  /** 下請業者. */
  @Schema(name = "childPartnerVendorNm", description = "下請業者")
  private String childPartnerVendorNm;

  /** 下請業者カナ名. */
  @Schema(name = "childPartnerVendorKnNm", description = "下請業者カナ名")
  private String childPartnerVendorKnNm;

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

  /** 項目コード. */
  @Schema(name = "itemCd", description = "項目コード")
  private String itemCd;

  /** コンストラクタ. */
  public SubconNotifApprInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param parentPartnerVendorCd 元請業者コード
   * @param parentPartnerVendorNm 元請業者
   * @param parentPartnerVendorKnNm 元請業者カナ名
   * @param childPartnerVendorCd 下請業者コード
   * @param childPartnerVendorNm 下請業者
   * @param childPartnerVendorKnNm 下請業者
   * @param requestEmpNm 申請者
   * @param requestTs 申請日
   * @param finalApprDt 最終承認日
   * @param itemValue 結果
   * @param comment コメント
   * @param itemCd 項目コード
   */
  public SubconNotifApprInfoDto(
      String parentPartnerVendorCd,
      String parentPartnerVendorNm,
      String parentPartnerVendorKnNm,
      String childPartnerVendorCd,
      String childPartnerVendorNm,
      String childPartnerVendorKnNm,
      String requestEmpNm,
      String requestTs,
      String finalApprDt,
      String itemValue,
      String comment,
      String itemCd) {
    this.parentPartnerVendorCd = parentPartnerVendorCd;
    this.parentPartnerVendorNm = parentPartnerVendorNm;
    this.parentPartnerVendorKnNm = parentPartnerVendorKnNm;
    this.childPartnerVendorCd = childPartnerVendorCd;
    this.childPartnerVendorNm = childPartnerVendorNm;
    this.childPartnerVendorKnNm = childPartnerVendorKnNm;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.finalApprDt = finalApprDt;
    this.itemValue = itemValue;
    this.comment = comment;
    this.itemCd = itemCd;
  }
}
