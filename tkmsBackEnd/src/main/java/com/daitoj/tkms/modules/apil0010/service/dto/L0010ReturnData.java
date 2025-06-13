package com.daitoj.tkms.modules.apil0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 請求一覧検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "L0010ReturnData", description = "請求一覧検索結果")
public class L0010ReturnData {

  /* 請求情報リスト */
  @Schema(
      name = "listCustomerInvoiceResultInfo",
      description = "請求情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<CustomerInvoiceResultInfoDto> listCustomerInvoiceResultInfo;

  /* 編集権限 */
  @Schema(
      name = "hensyuuKengen",
      description = "編集権限",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private String hensyuuKengen;

  /* 参照権限 */
  @Schema(
      name = "sansyouKengen",
      description = "参照権限",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private String sansyouKengen;

}
