package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 顧客検索情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.CustomerSearchDto", description = "顧客検索情報")
public class CustomerSearchDto {

  /** 顧客コード */
  @Schema(description = "顧客コード")
  private String customerCd;

  /** 顧客名１ */
  @Schema(description = "顧客名１")
  private String customerNm1;

  /** 顧客名２ */
  @Schema(description = "顧客名２")
  private String customerNm2;

  /** 顧客略称 */
  @Schema(description = "顧客略称")
  private String customerRyakusyou;

  /** 顧客カナ名 */
  @Schema(description = "顧客カナ名")
  private String customerKnNm;
}
