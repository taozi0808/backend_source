package com.daitoj.tkms.modules.apid0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 精積算情報. */
@lombok.Setter
@lombok.Getter
@Schema(name = "DetailedEstInfoDto", description = "検索結果")
public class DetailedEstInfoDto {

  /* 精積算ヘッダID */
  @Schema(name = "detailedEstHid", description = "精積算ヘッダID")
  protected Long id;

  /* 精積算コード */
  @Schema(name = "detailedEstCd", description = "精積算コード")
  protected String detailedEstCd;

  /* 案件コード */
  @Schema(name = "projectCd", description = "案件コード")
  protected String projectCd;

  /* 案件名称 */
  @Schema(name = "projectNm", description = "案件名称")
  protected String projectNm;

  /* 概算コード */
  @Schema(name = "roughEstCd", description = "概算コード")
  protected String roughEstCd;

  /* 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  protected String customerCd;

  /* 顧客名称 */
  @Schema(name = "customerNm", description = "顧客名称")
  protected String customerNm;

  /* 概算金額 */
  @Schema(name = "roughEstTotalAmt", description = "概算合計金額(概算金額)")
  protected BigDecimal roughEstTotalAmt;

  /* 精積算金額 */
  @Schema(name = "detailedEstTotalAmt", description = "精積算合計金額(精積算金額)")
  protected BigDecimal detailedEstTotalAmt;

  /* 作成日/修正日 */
  @Schema(name = "detailedEstYmd", description = "精積算日付(作成日/修正日)")
  protected String detailedEstYmd;

  /* 承認日 */
  @Schema(name = "finalApprDt", description = "承認日(最終承認日)")
  protected String finalApprDt;

  /* 精積算部門コード */
  @Schema(name = "detailedEstOrgCd", description = "精積算部門コード")
  protected String detailedEstOrgCd;

  /* 精積算部門 */
  @Schema(name = "detailedEstOrgNm", description = "精積算部門")
  protected String detailedEstOrgNm;

  /* 精積算担当者コード */
  @Schema(name = "detailedEstPicCd", description = "精積算担当者コード")
  protected String detailedEstPicCd;

  /* 精積算担当者 */
  @Schema(name = "detailedEstPicNm", description = "精積算担当者")
  protected String detailedEstPicNm;

  /** コンストラクタ. */
  public DetailedEstInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param id 精積算ヘッダID
   * @param detailedEstCd 精積算コード
   * @param projectCd 案件コード
   * @param projectNm 案件名
   * @param roughEstCd 概算コード
   * @param customerCd 顧客コード
   * @param customerNm 顧客名
   * @param roughEstTotalAmt 概算合計金額
   * @param detailedEstTotalAmt 精積算合計金額
   * @param detailedEstYmd 精積算日付
   * @param finalApprDt 最終承認日
   * @param detailedEstOrgCd 精積算部門コード
   * @param detailedEstOrgNm 精積算部門名
   * @param detailedEstPicCd 精積算担当者コード
   * @param detailedEstPicNm 精積算担当者名
   */
  public DetailedEstInfoDto(
      Long id,
      String detailedEstCd,
      String projectCd,
      String projectNm,
      String roughEstCd,
      String customerCd,
      String customerNm,
      BigDecimal roughEstTotalAmt,
      BigDecimal detailedEstTotalAmt,
      String detailedEstYmd,
      String finalApprDt,
      String detailedEstOrgCd,
      String detailedEstOrgNm,
      String detailedEstPicCd,
      String detailedEstPicNm) {
    this.id = id;
    this.detailedEstCd = detailedEstCd;
    this.projectCd = projectCd;
    this.projectNm = projectNm;
    this.roughEstCd = roughEstCd;
    this.customerCd = customerCd;
    this.customerNm = customerNm;
    this.roughEstTotalAmt = roughEstTotalAmt;
    this.detailedEstTotalAmt = detailedEstTotalAmt;
    this.detailedEstYmd = detailedEstYmd;
    this.finalApprDt = finalApprDt;
    this.detailedEstOrgCd = detailedEstOrgCd;
    this.detailedEstOrgNm = detailedEstOrgNm;
    this.detailedEstPicCd = detailedEstPicCd;
    this.detailedEstPicNm = detailedEstPicNm;
  }

}
