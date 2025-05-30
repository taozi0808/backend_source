package com.daitoj.tkms.modules.apio0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 業者情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "VendorInfoDto", description = "業者情報検索結果")
public class VendorInfoDto {

  /* id */
  @Schema(name = "id", description = "id")
  protected Long id;

  /* 業者コード */
  @Schema(name = "vendorCd", description = "業者コード")
  protected String vendorCd;

  /* 支店コード */
  @Schema(name = "branchCd", description = "支店コード")
  protected String branchCd;

  /* 業者名 */
  @Schema(name = "compNm", description = "業者名")
  protected String compNm;

  /*　業者カナ名　*/
  @Schema(name = "compKnNm", description = "業者カナ名")
  protected String compKnNm;

  /* 支店名 */
  @Schema(name = "branchNm", description = "支店名")
  protected String branchNm;

  /* 業者住所1 */
  @Schema(name = "vendorAddr1", description = "業者住所1")
  protected String vendorAddr1;

  /* 業者住所2 */
  @Schema(name = "vendorAddr2", description = "業者住所2")
  protected String vendorAddr2;

  /* 代表者名 */
  @Schema(name = "ceoNm", description = "代表者名")
  protected String ceoNm;

  /* 電話番号 */
  @Schema(name = "vendorTelNo", description = "電話番号")
  protected String vendorTelNo;

  /* 業種1 */
  @Schema(name = "jobType1", description = "業種1")
  protected String jobType1;

  /* 業種2 */
  @Schema(name = "jobType2", description = "業種2")
  protected String jobType2;

  /* 業種3 */
  @Schema(name = "jobType3", description = "業種3")
  protected String jobType3;

  /* 業種4 */
  @Schema(name = "jobType4", description = "業種4")
  protected String jobType4;

  /* 業種5 */
  @Schema(name = "jobType5", description = "業種5")
  protected String jobType5;

  /* 解体登録 */
  @Schema(name = "demolPerm", description = "解体登録")
  protected String demolPerm;

  /* 警備認定 */
  @Schema(name = "securityCert", description = "警備認定")
  protected String securityCert;

  /* 産廃許可 */
  @Schema(name = "iwPerm", description = "産廃許可")
  protected String iwPerm;

  /* 許可期限 */
  @Schema(name = "iwPermDt", description = "許可期限")
  protected String iwPermDt;

  /** コンストラクタ. */
  public VendorInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param id 業者ID
   * @param vendorCd 業者コード
   * @param branchCd 支店コード
   * @param compNm 業者名
   * @param branchNm 支店名
   * @param vendorAddr1 住所1
   * @param vendorAddr2
   * @param ceoNm
   * @param vendorTelNo
   * @param jobType1
   * @param jobType2
   * @param jobType3
   * @param jobType4
   * @param jobType5
   * @param demolPerm
   * @param securityCert
   */
  public VendorInfoDto(
      Long id,
      String vendorCd,
      String branchCd,
      String compNm,
      String compKnNm,
      String branchNm,
      String vendorAddr1,
      String vendorAddr2,
      String ceoNm,
      String vendorTelNo,
      String jobType1,
      String jobType2,
      String jobType3,
      String jobType4,
      String jobType5,
      String demolPerm,
      String securityCert,
      String iwPerm,
      String iwPermDt) {
    this.id = id;
    this.vendorCd = vendorCd;
    this.branchCd = branchCd;
    this.compNm = compNm;
    this.compKnNm = compKnNm;
    this.branchNm = branchNm;
    this.vendorAddr1 = vendorAddr1;
    this.vendorAddr2 = vendorAddr2;
    this.ceoNm = ceoNm;
    this.vendorTelNo = vendorTelNo;
    this.jobType1 = jobType1;
    this.jobType2 = jobType2;
    this.jobType3 = jobType3;
    this.jobType4 = jobType4;
    this.jobType5 = jobType5;
    this.demolPerm = demolPerm;
    this.securityCert = securityCert;
    this.iwPerm = iwPerm;
    this.iwPermDt = iwPermDt;
  }
}
