package com.daitoj.tkms.modules.apil0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Optional;

/** 顧客結果情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "CustomerInvoiceResultInfoDto", description = "顧客結果情報")
public class CustomerInvoiceResultInfoDto extends CustomerInvoiceHdrInfoDto {

  /* 請求済金額 */
  @Schema(name = "paidAmt", description = "請求済金額")
  public BigDecimal getPaidAmt() {
    return Optional.ofNullable(invoiceTotalAmt)
        .orElse(BigDecimal.ZERO)
        .subtract(Optional.ofNullable(paymentAmt).orElse(BigDecimal.ZERO));
  }

  /* 未請求残 */
  @Schema(name = "outstandingAmt", description = "未請求残")
  public BigDecimal getOutstandingAmt() {
    return Optional.ofNullable(inclTaxCoTotalAmt)
        .orElse(BigDecimal.ZERO)
        .subtract(Optional.ofNullable(getPaidAmt()).orElse(BigDecimal.ZERO))
        .subtract(Optional.ofNullable(paymentAmt).orElse(BigDecimal.ZERO));
  }

  /* 請求書(歴番付け) */
  @Schema(name = "invoiceNoWithHisno", description = "請求書(歴番付け)")
  public String getInvoiceNoWithHisno() {
    return invoiceNo + "-" + hisNo;
  }

}
