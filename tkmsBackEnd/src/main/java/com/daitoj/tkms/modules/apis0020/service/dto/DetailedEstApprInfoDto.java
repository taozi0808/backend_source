package com.daitoj.tkms.modules.apis0020.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 * 承認一覧（精積算管理）情報.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "DetailedEstApprInfoDto", description = "検索結果")
public class DetailedEstApprInfoDto {

  /**
   * 精積算コード.
   */
  @Schema(name = "detailedEstCd", description = "精積算コード")
  private String detailedEstCd;

  /**
   * 案件名.
   */
  @Schema(name = "projectNm", description = "案件名")
  private String projectNm;

  /**
   * 顧客コード.
   */
  @Schema(name = "customerBranchCd", description = "顧客コード")
  private String customerBranchCd;

  /**
   * 顧客名.
   */
  @Schema(name = "customerNm", description = "顧客名")
  private String customerNm;

  /**
   * 概算金額.
   */
  @Schema(name = "roughEstTotalAmt", description = "概算金額")
  private BigDecimal roughEstTotalAmt;

  /**
   * 精積算金額.
   */
  @Schema(name = "detailedEstTotalAmt", description = "精積算金額")
  private BigDecimal detailedEstTotalAmt;

  /**
   * 作成日/修正日.
   */
  @Schema(name = "detailedEstYmd", description = "作成日/修正日")
  private String detailedEstYmd;

  /**
   * 最終承認日.
   */
  @Schema(name = "finalApprDt", description = "最終承認日")
  private String finalApprDt;

  /**
   * 担当部門.
   */
  @Schema(name = "orgNm", description = "担当部門")
  private String orgNm;

  /**
   * 担当者.
   */
  @Schema(name = "detailedEstEmpNm", description = "担当者")
  private String detailedEstEmpNm;

  /**
   * 申請者.
   */
  @Schema(name = "requestEmpNm", description = "申請者")
  private String requestEmpNm;

  /**
   * 申請日.
   */
  @Schema(name = "requestTs", description = "申請日")
  private String requestTs;

  /**
   * 結果.
   */
  @Schema(name = "itemValue", description = "結果")
  private String itemValue;

  /**
   * コメント.
   */
  @Schema(name = "comment", description = "コメント")
  private String comment;

  /**
   * 案件カナ名.
   */
  @Schema(name = "projectKnNm", description = "案件カナ名")
  private String projectKnNm;

  /**
   * 項目コード.
   */
  @Schema(name = "itemCd", description = "項目コード")
  private String itemCd;

  /**
   * コンストラクタ.
   */
  public DetailedEstApprInfoDto() {
  }

  /**
   * コンストラクタ.
   *
   * @param detailedEstCd       精積算コード
   * @param projectNm           案件名
   * @param customerBranchCd    顧客コード
   * @param customerNm          顧客名
   * @param roughEstTotalAmt    概算金額
   * @param detailedEstTotalAmt 精積算金額
   * @param detailedEstYmd      作成日/修正日
   * @param finalApprDt         最終承認日
   * @param orgNm               担当部門
   * @param detailedEstEmpNm    担当者
   * @param requestEmpNm        申請者
   * @param requestTs           申請日
   * @param itemValue           結果
   * @param comment             コメント
   */
  public DetailedEstApprInfoDto(
      String detailedEstCd,
      String projectNm,
      String customerBranchCd,
      String customerNm,
      BigDecimal roughEstTotalAmt,
      BigDecimal detailedEstTotalAmt,
      String detailedEstYmd,
      String finalApprDt,
      String orgNm,
      String detailedEstEmpNm,
      String requestEmpNm,
      String requestTs,
      String itemValue,
      String comment,
      String projectKnNm,
      String itemCd) {
    this.detailedEstCd = detailedEstCd;
    this.projectNm = projectNm;
    this.customerBranchCd = customerBranchCd;
    this.customerNm = customerNm;
    this.roughEstTotalAmt = roughEstTotalAmt;
    this.detailedEstTotalAmt = detailedEstTotalAmt;
    this.detailedEstYmd = detailedEstYmd;
    this.finalApprDt = finalApprDt;
    this.orgNm = orgNm;
    this.detailedEstEmpNm = detailedEstEmpNm;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.itemValue = itemValue;
    this.comment = comment;
    this.projectKnNm = projectKnNm;
    this.itemCd = itemCd;
  }

}
