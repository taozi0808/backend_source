package com.daitoj.tkms.modules.apib0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 請求条件 */
@lombok.Getter
@lombok.Setter
@Schema(name = "PaymentTermDto", description = "請求条件")
public class PaymentTermDto {
  /** 請求条件コード */
  @Schema(description = "請求条件コード")
  private String paymentTermsCd;

  /** 請求条件名称 */
  @Schema(description = "請求条件名称")
  private String paymentTermsNm;

  /** 請求発生区分 */
  @Schema(description = "請求発生区分")
  private String paymentTriggerK;

  /** 請求金額計算区分 */
  @Schema(description = "請求金額計算区分")
  private String paymentAmtCalK;
}
