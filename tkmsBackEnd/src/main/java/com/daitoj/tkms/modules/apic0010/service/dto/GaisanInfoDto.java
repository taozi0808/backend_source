package com.daitoj.tkms.modules.apic0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 概算情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "GaisanInfoDto", description = "検索結果")
public class GaisanInfoDto {
  /** 概算ヘッダID */
  @Schema(name = "id", description = "概算ヘッダID")
  private Long id;

  /** 案件コード */
  @Schema(name = "projectCd", description = "案件コード")
  private String projectCd;

  /** 案件枝コード */
  @Schema(name = "hisNo", description = "案件枝コード")
  private Integer hisNo;

  /** 概算コード */
  @Schema(name = "roughEstCd", description = "概算コード")
  private String roughEstCd;

  /** 案件名 */
  @Schema(name = "projectNm", description = "案件名")
  private String projectNm;

  /** 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  private String customerCd;

  /** 顧客名 */
  @Schema(name = "customerName", description = "顧客名")
  private String customerName;

  /** 見積提出期限 */
  @Schema(name = "estSubmitDueDt", description = "見積提出期限")
  private String estSubmitDueDt;

  /** 概算合計金額 */
  @Schema(name = "roughEstTotalAmt", description = "概算合計金額")
  private BigDecimal roughEstTotalAmt;

  /** 着工希望日 */
  @Schema(name = "startHopeYmd", description = "着工希望日")
  private String startHopeYmd;

  /** 完工希望日 */
  @Schema(name = "compHopeYmd", description = "完工希望日")
  private String compHopeYmd;

  /** 概算部門コード */
  @Schema(name = "orgCd", description = "概算部門コード")
  private String orgCd;

  /** 概算部門 */
  @Schema(name = "orgNm", description = "概算部門")
  private String orgNm;

  /** 概算担当者コード */
  @Schema(name = "roughEstPicCd", description = "概算担当者コード")
  private String roughEstPicCd;

  /** 概算担当者 */
  @Schema(name = "empNm", description = "概算担当者")
  private String empNm;

  /** コンストラクタ */
  public GaisanInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param id 概算ヘッダID
   * @param projectCd 案件コード
   * @param hisNo 案件枝コード
   * @param roughEstCd 概算コード
   * @param projectNm 案件名
   * @param customerCd 顧客コード
   * @param customerName 顧客名
   * @param estSubmitDueDt 見積提出期限
   * @param roughEstTotalAmt 概算合計金額
   * @param startHopeYmd 着工希望日
   * @param compHopeYmd 完工希望日
   * @param orgCd 概算部門コード
   * @param orgNm 概算部門
   * @param roughEstPicCd 概算担当者コード
   * @param empNm 概算担当者
   */
  public GaisanInfoDto(
      Long id,
      String projectCd,
      Integer hisNo,
      String roughEstCd,
      String projectNm,
      String customerCd,
      String customerName,
      String estSubmitDueDt,
      BigDecimal roughEstTotalAmt,
      String startHopeYmd,
      String compHopeYmd,
      String orgCd,
      String orgNm,
      String roughEstPicCd,
      String empNm) {
    this.id = id;
    this.projectCd = projectCd;
    this.hisNo = hisNo;
    this.roughEstCd = roughEstCd;
    this.projectNm = projectNm;
    this.customerCd = customerCd;
    this.customerName = customerName;
    this.estSubmitDueDt = estSubmitDueDt;
    this.roughEstTotalAmt = roughEstTotalAmt;
    this.startHopeYmd = startHopeYmd;
    this.compHopeYmd = compHopeYmd;
    this.orgCd = orgCd;
    this.orgNm = orgNm;
    this.roughEstPicCd = roughEstPicCd;
    this.empNm = empNm;
  }
}
