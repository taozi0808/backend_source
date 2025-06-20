package com.daitoj.tkms.modules.apir0060.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 顧客管理情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "CustomerBankBankBanchInfoDto", description = "検索結果")
public class CustomerBankBankBanchInfoDto {

  /** 顧客コード. */
  @Schema(name = "customerCd", description = "顧客コード")
  private String customerCd;

  /** 顧客名１ */
  @Schema(name = "customerNm1", description = "顧客名１")
  private String customerNm1;

  /** 顧客名２ */
  @Schema(name = "customerNm2", description = "顧客名２")
  private String customerNm2;

  /** 顧客略称 */
  @Schema(name = "customerRyakusyou", description = "顧客略称")
  private String customerRyakusyou;

  /** 顧客カナ名 */
  @Schema(name = "customerKnNm", description = "顧客カナ名")
  private String customerKnNm;

  /** 顧客担当者名 */
  @Schema(name = "customerPicNm", description = "顧客担当者名")
  private String customerPicNm;

  /** 顧客担当者カナ名 */
  @Schema(name = "customerPicKnNm", description = "顧客担当者カナ名")
  private String customerPicKnNm;

  /** 取引先区分. */
  @Schema(name = "tradingK", description = " 取引先区分")
  private String tradingK;

  /** 顧客郵便番号 */
  @Schema(name = "customerPostNo", description = " 顧客郵便番号")
  private String customerPostNo;

  /** 顧客住所１ */
  @Schema(name = "customerAddr1", description = " 顧客住所１")
  private String customerAddr1;

  /** 顧客住所２ */
  @Schema(name = "customerAddr2", description = " 顧客住所２")
  private String customerAddr2;

  /** 顧客電話番号 */
  @Schema(name = "customerTelNo", description = " 顧客電話番号")
  private String customerTelNo;

  /** 顧客担当者携帯番号 */
  @Schema(name = "customerPicPhoneNo", description = " 顧客担当者携帯番号")
  private String customerPicPhoneNo;

  /** 顧客FAX番号 */
  @Schema(name = "customerFaxNo", description = " 顧客FAX番号")
  private String customerFaxNo;

  /** 銀行コード */
  @Schema(name = "bankCd", description = " 銀行コード")
  private String bankCd;

  /** 銀行支店コード */
  @Schema(name = "bankBanchCd", description = " 銀行支店コード")
  private String bankBanchCd;

  /** 預金種別 */
  @Schema(name = "depositType", description = " 預金種別")
  private String depositType;

  /** 口座番号 */
  @Schema(name = "bankAccountNo", description = " 口座番号")
  private String bankAccountNo;

  /** 口座名義 */
  @Schema(name = "bankAccountHolderNm", description = " 口座名義")
  private String bankAccountHolderNm;

  /** 資本金 */
  @Schema(name = "capital", description = " 資本金")
  private String capital;

  /** 従業員数 */
  @Schema(name = "employeeNumber", description = " 従業員数")
  private String employeeNumber;

  /** 業種・業態 */
  @Schema(name = "gyousyuGyoutai", description = " 業種・業態")
  private String gyousyuGyoutai;

  /** 代表者名 */
  @Schema(name = "ceoNm", description = " 代表者名")
  private String ceoNm;

  /** 会社URL */
  @Schema(name = "compUrl", description = " 会社URL")
  private String compUrl;

  /** 備考 */
  @Schema(name = "remarks", description = " 備考")
  private String remarks;

  /** コンストラクタ. */
  public CustomerBankBankBanchInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param customerCd 顧客コード
   * @param customerNm1 顧客名１
   * @param customerNm2 顧客名２
   * @param customerRyakusyou 顧客略称
   * @param customerKnNm 顧客カナ名
   * @param customerPicNm 顧客担当者名
   * @param customerPicKnNm 顧客担当者カナ名
   * @param tradingK 取引先区分
   * @param customerPostNo 顧客郵便番号
   * @param customerAddr1 顧客住所１
   * @param customerAddr2 顧客住所２
   * @param customerTelNo 顧客電話番号
   * @param customerPicPhoneNo 顧客担当者携帯番号
   * @param customerFaxNo 顧客FAX番号
   * @param bankCd 銀行コード
   * @param bankBanchCd 銀行支店コード
   * @param depositType 預金種別
   * @param bankAccountNo 口座番号
   * @param bankAccountHolderNm 口座名義
   * @param capital 資本金
   * @param employeeNumber 従業員数
   * @param gyousyuGyoutai 業種・業態
   * @param ceoNm 代表者名
   * @param compUrl 会社URL
   * @param remarks 備考
   */
  public CustomerBankBankBanchInfoDto(
      String customerCd,
      String customerNm1,
      String customerNm2,
      String customerRyakusyou,
      String customerKnNm,
      String customerPicNm,
      String customerPicKnNm,
      String tradingK,
      String customerPostNo,
      String customerAddr1,
      String customerAddr2,
      String customerTelNo,
      String customerPicPhoneNo,
      String customerFaxNo,
      String bankCd,
      String bankBanchCd,
      String depositType,
      String bankAccountNo,
      String bankAccountHolderNm,
      String capital,
      String employeeNumber,
      String gyousyuGyoutai,
      String ceoNm,
      String compUrl,
      String remarks) {
    this.customerCd = customerCd;
    this.customerNm1 = customerNm1;
    this.customerNm2 = customerNm2;
    this.customerRyakusyou = customerRyakusyou;
    this.customerKnNm = customerKnNm;
    this.customerPicNm = customerPicNm;
    this.customerPicKnNm = customerPicKnNm;
    this.tradingK = tradingK;
    this.customerPostNo = customerPostNo;
    this.customerAddr1 = customerAddr1;
    this.customerAddr2 = customerAddr2;
    this.customerTelNo = customerTelNo;
    this.customerPicPhoneNo = customerPicPhoneNo;
    this.customerFaxNo = customerFaxNo;
    this.bankCd = bankCd;
    this.bankBanchCd = bankBanchCd;
    this.depositType = depositType;
    this.bankAccountNo = bankAccountNo;
    this.bankAccountHolderNm = bankAccountHolderNm;
    this.capital = capital;
    this.employeeNumber = employeeNumber;
    this.gyousyuGyoutai = gyousyuGyoutai;
    this.ceoNm = ceoNm;
    this.compUrl = compUrl;
    this.remarks = remarks;
  }
}
