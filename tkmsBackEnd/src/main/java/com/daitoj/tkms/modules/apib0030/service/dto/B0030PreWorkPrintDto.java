package com.daitoj.tkms.modules.apib0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

/**
 * 案件登録先行作業明細印刷パラメータ.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0030PewWorkPrintDto", description = "案件登録先行作業明細印刷パラメータ")
public class B0030PreWorkPrintDto {

  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM月dd日)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;

  /** 顧客名 */
  @Schema(description = "顧客名")
  private String customerName;

  /** 案件名 */
  @Schema(description = "案件名")
  private String projectNm;

  /** 物件住所1 */
  @Schema(description = "物件住所1")
  private String constrSiteAddr1;

  /** 物件住所2 */
  @Schema(description = "物件住所2")
  private String constrSiteAddr2;

  /** 受注見込日 */
  @Schema(description = "受注見込日")
  private String orderExpectedYmd;

  /** 見積日 */
  @Schema(description = "見積日")
  private String estYmd;

  /** 計画概要 */
  @Schema(description = "計画概要")
  private String outline;

  /** 先行工事内容 */
  @Schema(description = "先行工事内容")
  private String preConstrContent;

  /** 工事原価 */
  @Schema(description = "工事原価")
  private String constrCost;

  /** 支払条件 */
  @Schema(description = "支払条件")
  private String paymentTerms;

  /** その他　*/
  @Schema(description = "その他")
  private String sonota;

  /** 顧客代表者名　*/
  @Schema(description = "顧客代表者名")
  private String ceoNm;

}
