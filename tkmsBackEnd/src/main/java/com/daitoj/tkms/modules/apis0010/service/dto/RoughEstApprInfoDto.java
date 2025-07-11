package com.daitoj.tkms.modules.apis0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 * 承認一覧（概算管理）情報.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "RoughEstApprInfoDto", description = "検索結果")
public class RoughEstApprInfoDto {
  /**
   * 概算コード.
   */
  @Schema(name = "roughEstCd", description = "概算コード")
  private String roughEstCd;

  /**
   * 案件コード.
   */
  @Schema(name = "projectCd", description = "案件コード")
  private String projectCd;

  /**
   * 案件名.
   */
  @Schema(name = "projectNm", description = "案件名")
  private String projectNm;

  /**
   * 顧客コード.
   */
  @Schema(name = "customerCd", description = "顧客コード")
  private String customerCd;

  /**
   * 顧客名1.
   */
  @Schema(name = "customerNm1", description = "顧客名")
  private String customerNm1;

  /**
   * 顧客名2.
   */
  @Schema(name = "customerNm2", description = "顧客名")
  private String customerNm2;

  /**
   * 見積提出期限.
   */
  @Schema(name = "estSubmitDueTs", description = "見積提出期限")
  private String estSubmitDueTs;

  /**
   * 概算金額.
   */
  @Schema(name = "roughEstTotalAmt", description = "概算金額")
  private BigDecimal roughEstTotalAmt;

  /**
   * 着工希望日.
   */
  @Schema(name = "startHopeYmd", description = "着工希望日")
  private String startHopeYmd;

  /**
   * 完工希望日.
   */
  @Schema(name = "compHopeYmd", description = "完工希望日")
  private String compHopeYmd;

  /**
   * 概算部門.
   */
  @Schema(name = "roughEstOrgNm", description = "概算部門")
  private String roughEstOrgNm;

  /**
   * 概算担当者.
   */
  @Schema(name = "roughEstEmpNm", description = "概算担当者")
  private String roughEstEmpNm;

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
   * 最終承認日.
   */
  @Schema(name = "itemCd", description = "最終承認日")
  private String finalApprDt;

  /**
   * コンストラクタ.
   */
  public RoughEstApprInfoDto() {
  }

  /**
   * コンストラクタ.
   *
   * @param roughEstCd       概算コード
   * @param projectCd        案件コード
   * @param projectNm        案件名
   * @param customerCd       顧客コード
   * @param customerNm1      顧客名1
   * @param customerNm2      顧客名2
   * @param estSubmitDueTs   見積提出期限
   * @param roughEstTotalAmt 概算金額
   * @param startHopeYmd     着工希望日
   * @param compHopeYmd      完工希望日
   * @param roughEstOrgNm    概算部門
   * @param roughEstEmpNm    概算担当者
   * @param requestEmpNm     申請者
   * @param requestTs        申請日
   * @param itemValue        結果
   * @param comment          コメント
   * @param projectKnNm      案件カナ名
   * @param finalApprDt      最終承認日
   */
  public RoughEstApprInfoDto(
      String roughEstCd,
      String projectCd,
      String projectNm,
      String customerCd,
      String customerNm1,
      String customerNm2,
      String estSubmitDueTs,
      BigDecimal roughEstTotalAmt,
      String startHopeYmd,
      String compHopeYmd,
      String roughEstOrgNm,
      String roughEstEmpNm,
      String requestEmpNm,
      String requestTs,
      String itemValue,
      String comment,
      String projectKnNm,
      String itemCd,
      String finalApprDt) {
    this.roughEstCd = roughEstCd;
    this.projectCd = projectCd;
    this.projectNm = projectNm;
    this.customerCd = customerCd;
    this.customerNm1 = customerNm1;
    this.customerNm2 = customerNm2;
    this.estSubmitDueTs = estSubmitDueTs;
    this.roughEstTotalAmt = roughEstTotalAmt;
    this.startHopeYmd = startHopeYmd;
    this.compHopeYmd = compHopeYmd;
    this.roughEstOrgNm = roughEstOrgNm;
    this.roughEstEmpNm = roughEstEmpNm;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.itemValue = itemValue;
    this.comment = comment;
    this.projectKnNm = projectKnNm;
    this.itemCd = itemCd;
    this.finalApprDt = finalApprDt;
  }
}
