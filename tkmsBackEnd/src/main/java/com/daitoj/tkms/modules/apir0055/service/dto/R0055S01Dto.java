package com.daitoj.tkms.modules.apir0055.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/** 顧客一覧初期パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0055S01Dto", description = "顧客一覧初期パラメータ")
public class R0055S01Dto extends BasePageRequest {

  /* 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  private String customerCd;

  /* 顧客名 */
  @Schema(name = "customerNm", description = "顧客名")
  private String customerNm;

  /* 業種・業態コード */
  @Schema(name = "gyousyuGyoutai", description = "業種・業態コード")
  private String gyousyuGyoutai;

  /* 業種・業態名 */
  @Schema(name = "gyousyuGyoutaiNm", description = "業種・業態名")
  private String gyousyuGyoutaiNm;

  /* 取引先区分 */
  @Schema(name = "tradingK", description = "取引先区分")
  private String tradingK;

  /* 取引先区分名 */
  @Schema(name = "tradingKNm", description = "取引先区分名")
  private String tradingKNm;

}
