package com.daitoj.tkms.modules.apil0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 請求情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "CustomerInvoiceHdrInfoDto", description = "請求情報検索結果")
public class CustomerInvoiceHdrInfoDto {

  @Schema(name = "customer_invoice_hid", description = "顧客請求HID")
  protected Long id;

  /* 物件コード */
  @Schema(name = "projectSiteCd", description = "物件コード")
  protected String projectSiteCd;

  /* 物件名 */
  @Schema(name = "projectSiteNm", description = "物件名")
  protected String projectSiteNm;

  /* 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  protected String customerCd;

  /* 顧客名 */
  @Schema(name = "customerNm", description = "顧客名")
  protected String customerNm;

  /* 物件着手日 */
  @Schema(name = "constrStartYmd", description = "物件着手日")
  protected String constrStartYmd;

  /* 物件引渡日 */
  @Schema(name = "constrCompYmd", description = "物件引渡日")
  protected String constrCompYmd;

  /* 請求書No */
  @Schema(name = "invoiceNo", description = "請求書No")
  protected String invoiceNo;

  /* 支払条件 */
  @Schema(name = "paymentTermsNm", description = "請求条件名称")
  protected String paymentTermsNm;

  /* 請求年月 */
  @Schema(name = "invoiceDt", description = "請求年月")
  protected String invoiceDt;

  /* 請負金額 */
  @Schema(name = "inclTaxCoTotalAmt", description = "請負金額")
  protected BigDecimal inclTaxCoTotalAmt;

  /* 請求トータル金額 */
  @Schema(name = "invoiceTotalAmt", description = "請求トータル金額")
  protected BigDecimal invoiceTotalAmt;

  /* 今回請求金額 */
  @Schema(name = "paymentAmt", description = "今回請求金額")
  protected BigDecimal paymentAmt;

  /* 歴番 */
  @Schema(name = "hisNo", description = "歴番")
  protected Integer hisNo;

  /** コンストラクタ. */
  public CustomerInvoiceHdrInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param projectSiteCd 物件コード
   * @param projectSiteNm 物件名
   * @param customerCd 顧客コード
   * @param customerNm 顧客名
   * @param constrStartYmd 物件着手日
   * @param constrCompYmd 物件完工日
   * @param invoiceNo 請求No
   * @param paymentTermsNm 支払条件
   * @param invoiceDt 請求年月
   * @param inclTaxCoTotalAmt 請負金額
   * @param invoiceTotalAmt 請求トータル金額
   * @param paymentAmt 今回請求金額
   * @param hisNo 歴番
   */
  public CustomerInvoiceHdrInfoDto(
      Long id,
      String projectSiteCd,
      String projectSiteNm,
      String customerCd,
      String customerNm,
      String constrStartYmd,
      String constrCompYmd,
      String invoiceNo,
      String paymentTermsNm,
      String invoiceDt,
      BigDecimal inclTaxCoTotalAmt,
      BigDecimal invoiceTotalAmt,
      BigDecimal paymentAmt,
      Integer hisNo) {
    this.id = id;
    this.projectSiteCd = projectSiteCd;
    this.projectSiteNm = projectSiteNm;
    this.customerCd = customerCd;
    this.customerNm = customerNm;
    this.constrStartYmd = constrStartYmd;
    this.constrCompYmd = constrCompYmd;
    this.invoiceNo = invoiceNo;
    this.paymentTermsNm = paymentTermsNm;
    this.invoiceDt = invoiceDt;
    this.inclTaxCoTotalAmt = inclTaxCoTotalAmt;
    this.invoiceTotalAmt = invoiceTotalAmt;
    this.paymentAmt = paymentAmt;
    this.hisNo = hisNo;
  }

}
