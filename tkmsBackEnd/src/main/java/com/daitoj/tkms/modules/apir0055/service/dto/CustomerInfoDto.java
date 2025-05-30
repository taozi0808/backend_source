package com.daitoj.tkms.modules.apir0055.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 顧客情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "CustomerInfoDto", description = "検索結果")
public class CustomerInfoDto {
  /* 顧客ID */
  @Schema(name = "id", description = "顧客ID")
  protected long id;

  /* 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  protected String customerCd;

  /* 顧客名 */
  @Schema(name = "customerNm", description = "顧客名")
  protected String customerNm;

  /* 顧客略名 */
  @Schema(name = "customerRyakusyou", description = "顧客略名")
  protected String customerRyakusyou;

  /* 顧客カナ名 */
  @Schema(name = "customerKnNm", description = "顧客カナ名")
  protected String customerKnNm;

  /* 取引先区分 */
  @Schema(name = "tradingK", description = "取引区分")
  protected String tradingK;

  /* 取引先区分 */
  @Schema(name = "tradingKNm", description = "取引先区分名")
  protected String tradingKNm;

  /* 業種・業態 */
  @Schema(name = "gyousyuGyoutai", description = "業種・業態")
  protected String gyousyuGyoutai;

  /* 業種・業態(表示) */
  @Schema(name = "gyousyuGyoutaiNm", description = "業種・業態(表示)")
  protected String gyousyuGyoutaiNm;

  /* 顧客住所 */
  @Schema(name = "customerAddr", description = "顧客住所")
  protected String customerAddr;

  /* 電話番号 */
  @Schema(name = "customerTelNo", description = "電話番号")
  protected String customerTelNo;

  /* 代表者名 */
  @Schema(name = "ceoNm", description = "代表者名")
  protected String ceoNm;

  /** コンストラクタ. */
  public CustomerInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param id id
   * @param customerCd 顧客コード
   * @param customerNm 顧客名
   * @param tradingK 取引先区分
   * @param tradingKNm 取引先区分名
   * @param gyousyuGyoutai 業種・業態
   * @param gyousyuGyoutaiNm 業種・業態(表示)
   * @param customerAddr 顧客住所
   * @param customerTelNo 電話番号
   * @param ceoNm 代表者名
   */
  public CustomerInfoDto(
      long id,
      String customerCd,
      String customerNm,
      String customerRyakusyou,
      String customerKnNm,
      String tradingK,
      String tradingKNm,
      String gyousyuGyoutai,
      String gyousyuGyoutaiNm,
      String customerAddr,
      String customerTelNo,
      String ceoNm) {
    this.id = id;
    this.customerCd = customerCd;
    this.customerNm = customerNm;
    this.customerRyakusyou = customerRyakusyou;
    this.customerKnNm = customerKnNm;
    this.tradingK = tradingK;
    this.tradingKNm = tradingKNm;
    this.gyousyuGyoutai = gyousyuGyoutai;
    this.gyousyuGyoutaiNm = gyousyuGyoutaiNm;
    this.customerAddr = customerAddr;
    this.customerTelNo = customerTelNo;
    this.ceoNm = ceoNm;
  }

}
