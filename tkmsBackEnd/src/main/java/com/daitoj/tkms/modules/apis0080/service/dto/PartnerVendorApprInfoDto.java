package com.daitoj.tkms.modules.apis0080.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 承認一覧（協力業者管理）情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "PartnerVendorApprInfoDto", description = "検索結果")
public class PartnerVendorApprInfoDto {

  /** 協力業者コード. */
  @Schema(name = "partnerVendorCd", description = "協力業者コード")
  private String partnerVendorCd;

  /** 協力業者名. */
  @Schema(name = "compNm", description = "協力業者名")
  private String compNm;

  /** 協力業者カナ名. */
  @Schema(name = "compKnNm", description = "協力業者カナ名")
  private String compKnNm;

  /** 支店名. */
  @Schema(name = "branchNm", description = "支店名")
  private String branchNm;

  /** 支店カナ名. */
  @Schema(name = "branchKnNm", description = "支店カナ名")
  private String branchKnNm;

  /** 解体登録. */
  @Schema(name = "demolPerm", description = "解体登録")
  private String demolPerm;

  /** 警備認定. */
  @Schema(name = "securityCert", description = "警備認定")
  private String securityCert;

  /** 産廃許可. */
  @Schema(name = "vendorIw", description = "産廃許可")
  private String vendorIw;

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
  public PartnerVendorApprInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param partnerVendorCd 協力業者コード
   * @param compNm 協力業者名
   * @param compKnNm 協力業者カナ名
   * @param branchNm 支店名
   * @param branchKnNm 支店カナ名
   * @param demolPerm 解体登録
   * @param securityCert 警備認定
   * @param vendorIw 産廃許可
   * @param requestEmpNm 申請者
   * @param requestTs 申請日
   * @param itemValue 結果
   * @param comment コメント
   * @param itemCd 項目コード
   * @param finalApprDt 最終承認日
   */
  public PartnerVendorApprInfoDto(
      String partnerVendorCd,
      String compNm,
      String compKnNm,
      String branchNm,
      String branchKnNm,
      String demolPerm,
      String securityCert,
      String vendorIw,
      String requestEmpNm,
      String requestTs,
      String itemValue,
      String comment,
      String itemCd,
      String finalApprDt) {
    this.partnerVendorCd = partnerVendorCd;
    this.compNm = compNm;
    this.compKnNm = compKnNm;
    this.branchNm = branchNm;
    this.branchKnNm = branchKnNm;
    this.demolPerm = demolPerm;
    this.securityCert = securityCert;
    this.vendorIw = vendorIw;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.itemValue = itemValue;
    this.comment = comment;
    this.itemCd = itemCd;
    this.finalApprDt = finalApprDt;
  }
}
