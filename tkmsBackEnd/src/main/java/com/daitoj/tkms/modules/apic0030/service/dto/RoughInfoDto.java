package com.daitoj.tkms.modules.apic0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/** 概算情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "RoughInfoDto", description = "検索結果")
public class RoughInfoDto {
  /** 概算ヘッダID */
  @Schema(name = "roughEstId", description = "概算ヘッダID")
  private Long roughEstId;

  /** 概算コード */
  @Schema(name = "roughEstCd", description = "概算コード")
  private String roughEstCd;

  /** 概算枝コード */
  @Schema(name = "hisNo", description = "概算枝コード")
  private Integer hisNo;

  /** 案件ヘッダID */
  @Schema(name = "projectId", description = "案件ヘッダID")
  private Long projectId;

  /** 案件コード */
  @Schema(name = "projectCd", description = "案件コード")
  private String projectCd;

  /** 案件名 */
  @Schema(name = "projectNm", description = "案件名")
  private String projectNm;

  /** 案件カナ名 */
  @Schema(name = "projectKnNm", description = "案件カナ名")
  private String projectKnNm;

  /** 概算日付 */
  @Schema(name = "roughEstYmd", description = "概算日付")
  private String roughEstYmd;

  /** 概算部門ID */
  @Schema(name = "roughEstOrgId", description = "概算部門ID")
  private Long roughEstOrgId;

  /** 概算部門 */
  @Schema(name = "orgNm", description = "概算部門")
  private String orgNm;

  /** 概算担当者コード */
  @Schema(name = "roughEstPicCd", description = "概算担当者コード")
  private String roughEstPicCd;

  /** 概算担当者 */
  @Schema(name = "empNm", description = "概算担当者")
  private String empNm;

  /** 地域コード */
  @Schema(name = "regionCd", description = "地域コード")
  private String regionCd;

  /** 地域名 */
  @Schema(name = "regionNm", description = "地域名")
  private String regionNm;

  /** 現場区分コード */
  @Schema(name = "constrSiteK", description = "現場区分コード")
  private String constrSiteK;

  /** 現場区分 */
  @Schema(name = "constrSiteKName", description = "現場区分")
  private String constrSiteKName;

  /** 現場郵便番号1 */
  @Schema(name = "postNo1", description = "現場郵便番号1")
  private String postNo1;

  /** 現場郵便番号2 */
  @Schema(name = "postNo2", description = "現場郵便番号2")
  private String postNo2;

  /** 現場住所１ */
  @Schema(name = "constrSiteAddr1", description = "現場住所１")
  private String constrSiteAddr1;

  /** 現場住所２ */
  @Schema(name = "constrSiteAddr2", description = "現場住所２")
  private String constrSiteAddr2;

  /** 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  private String customerCd;

  /** 顧客名 */
  @Schema(name = "customerName", description = "顧客名")
  private String customerName;

  /** 顧客郵便番号1 */
  @Schema(name = "customerPostNo1", description = "顧客郵便番号1")
  private String customerPostNo1;

  /** 顧客郵便番号2 */
  @Schema(name = "customerPostNo2", description = "顧客郵便番号2")
  private String customerPostNo2;

  /** 顧客住所１ */
  @Schema(name = "customerAddr1", description = "顧客住所１")
  private String customerAddr1;

  /** 顧客住所２ */
  @Schema(name = "customerAddr2", description = "顧客住所２")
  private String customerAddr2;

  /** 官民区分 */
  @Schema(name = "govPeoK", description = "官民区分")
  private String govPeoK;

  /** 官民区分名 */
  @Schema(name = "govPeoKName", description = "官民区分名")
  private String govPeoKName;

  /** 見積提出期限 */
  @Schema(name = "estSubmitDueDt", description = "見積提出期限")
  private String estSubmitDueDt;

  /** 敷地面積 */
  @Schema(name = "siteArea", description = "敷地面積")
  private BigDecimal siteArea;

  /** 建築面積 */
  @Schema(name = "buildingArea", description = "建築面積")
  private BigDecimal buildingArea;

  /** 延床面積 */
  @Schema(name = "grossFloorArea", description = "延床面積")
  private BigDecimal grossFloorArea;

  /** 施工床面積 */
  @Schema(name = "buildupArea", description = "施工床面積")
  private BigDecimal buildupArea;

  /** 専有面積 */
  @Schema(name = "occupiedArea", description = "専有面積")
  private BigDecimal occupiedArea;

  /** 戸数 */
  @Schema(name = "households", description = "戸数")
  private BigDecimal households;

  /** 階数（地上） */
  @Schema(name = "floorCnt", description = "階数（地上）")
  private BigDecimal floorCnt;

  /** 階数（地下） */
  @Schema(name = "basementCnt", description = "階数（地下）")
  private BigDecimal basementCnt;

  /** 受注見込日 */
  @Schema(name = "orderExpectedYmd", description = "受注見込日")
  private String orderExpectedYmd;

  /** 着工希望日 */
  @Schema(name = "startHopeYmd", description = "着工希望日")
  private String startHopeYmd;

  /** 完工希望日 */
  @Schema(name = "compHopeYmd", description = "完工希望日")
  private String compHopeYmd;

  /** 営業部門ID */
  @Schema(name = "salesOrgId", description = "営業部門ID")
  private Long salesOrgId;

  /** 営業部門組織名 */
  @Schema(name = "salesOrgName", description = "営業部門組織名")
  private String salesOrgName;

  /** 営業管理職コード */
  @Schema(name = "salesMgrCd", description = "営業管理職コード")
  private String salesMgrCd;

  /** 営業管理職名 */
  @Schema(name = "salesMgrName", description = "営業管理職名")
  private String salesMgrName;

  /** 営業担当者コード */
  @Schema(name = "salesPicCd", description = "営業担当者コード")
  private String salesPicCd;

  /** 営業担当者名 */
  @Schema(name = "salesPicName", description = "営業担当者名")
  private String salesPicName;

  /** 設計業者名 */
  @Schema(name = "designVenderName", description = "設計業者名")
  private String designVenderName;

  /** 設計担当者 */
  @Schema(name = "designPicNm", description = "設計担当者")
  private String designPicNm;

  /** 概算合計金額 */
  @Schema(name = "roughEstTotalAmt", description = "概算合計金額")
  private BigDecimal roughEstTotalAmt;

  /** 業務データステータス */
  @Schema(name = "businessDataSt", description = "業務データステータス")
  private String businessDataSt;

  /** コンストラクタ */
  public RoughInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param roughEstId 概算ヘッダID
   * @param roughEstCd 概算コード
   * @param hisNo 概算枝コード
   * @param projectId 案件ヘッダID
   * @param projectCd 案件コード
   * @param projectNm 案件名
   * @param projectKnNm 案件カナ名
   * @param roughEstYmd 概算日付
   * @param roughEstOrgId 概算部門ID
   * @param orgNm 概算部門
   * @param roughEstPicCd 概算担当者コード
   * @param empNm 概算担当者
   * @param regionCd 地域コード
   * @param regionNm 地域名
   * @param constrSiteK 現場区分コード
   * @param constrSiteKName 現場区分
   * @param postNo1 現場郵便番号1
   * @param postNo2 現場郵便番号2
   * @param constrSiteAddr1 現場住所１
   * @param constrSiteAddr2 現場住所２
   * @param customerCd 顧客コード
   * @param customerName 顧客名
   * @param customerPostNo1 顧客郵便番号1
   * @param customerPostNo2 顧客郵便番号2
   * @param customerAddr1 顧客住所１
   * @param customerAddr2 顧客住所２
   * @param govPeoK 官民区分
   * @param govPeoKName 官民区分名
   * @param estSubmitDueDt 見積提出期限
   * @param siteArea 敷地面積
   * @param buildingArea 建築面積
   * @param grossFloorArea 延床面積
   * @param buildupArea 施工床面積
   * @param occupiedArea 専有面積
   * @param households 戸数
   * @param floorCnt 階数（地上）
   * @param basementCnt 階数（地下）
   * @param orderExpectedYmd 受注見込日
   * @param startHopeYmd 着工希望日
   * @param compHopeYmd 完工希望日
   * @param salesOrgId 営業部門ID
   * @param salesOrgName 営業部門組織名
   * @param salesMgrCd 営業管理職コード
   * @param salesMgrName 営業管理職名
   * @param salesPicCd 営業担当者コード
   * @param salesPicName 営業担当者名
   * @param designVenderName 設計業者名
   * @param designPicNm 設計担当者
   * @param businessDataSt 業務データステータス
   */
  public RoughInfoDto(
      Long roughEstId,
      String roughEstCd,
      Integer hisNo,
      Long projectId,
      String projectCd,
      String projectNm,
      String projectKnNm,
      String roughEstYmd,
      Long roughEstOrgId,
      String orgNm,
      String roughEstPicCd,
      String empNm,
      String regionCd,
      String regionNm,
      String constrSiteK,
      String constrSiteKName,
      String postNo1,
      String postNo2,
      String constrSiteAddr1,
      String constrSiteAddr2,
      String customerCd,
      String customerName,
      String customerPostNo1,
      String customerPostNo2,
      String customerAddr1,
      String customerAddr2,
      String govPeoK,
      String govPeoKName,
      String estSubmitDueDt,
      BigDecimal siteArea,
      BigDecimal buildingArea,
      BigDecimal grossFloorArea,
      BigDecimal buildupArea,
      BigDecimal occupiedArea,
      BigDecimal households,
      BigDecimal floorCnt,
      BigDecimal basementCnt,
      String orderExpectedYmd,
      String startHopeYmd,
      String compHopeYmd,
      Long salesOrgId,
      String salesOrgName,
      String salesMgrCd,
      String salesMgrName,
      String salesPicCd,
      String salesPicName,
      String designVenderName,
      String designPicNm,
      BigDecimal roughEstTotalAmt,
      String businessDataSt) {
    this.roughEstId = roughEstId;
    this.roughEstCd = roughEstCd;
    this.hisNo = hisNo;
    this.projectNm = projectNm;
    this.projectId = projectId;
    this.projectCd = projectCd;
    this.projectNm = projectNm;
    this.projectKnNm = projectKnNm;
    this.roughEstYmd = roughEstYmd;
    this.roughEstOrgId = roughEstOrgId;
    this.orgNm = orgNm;
    this.roughEstPicCd = roughEstPicCd;
    this.empNm = empNm;
    this.regionCd = regionCd;
    this.regionNm = regionNm;
    this.constrSiteK = constrSiteK;
    this.constrSiteKName = constrSiteKName;
    this.postNo1 = postNo1;
    this.postNo2 = postNo2;
    this.constrSiteAddr1 = constrSiteAddr1;
    this.constrSiteAddr2 = constrSiteAddr2;
    this.customerCd = customerCd;
    this.customerName = customerName;
    this.customerPostNo1 = customerPostNo1;
    this.customerPostNo2 = customerPostNo2;
    this.customerAddr1 = customerAddr1;
    this.customerAddr2 = customerAddr2;
    this.govPeoK = govPeoK;
    this.govPeoKName = govPeoKName;
    this.estSubmitDueDt = estSubmitDueDt;
    this.siteArea = siteArea;
    this.buildingArea = buildingArea;
    this.grossFloorArea = grossFloorArea;
    this.buildupArea = buildupArea;
    this.occupiedArea = occupiedArea;
    this.households = households;
    this.floorCnt = floorCnt;
    this.basementCnt = basementCnt;
    this.orderExpectedYmd = orderExpectedYmd;
    this.startHopeYmd = startHopeYmd;
    this.compHopeYmd = compHopeYmd;
    this.salesOrgId = salesOrgId;
    this.salesOrgName = salesOrgName;
    this.salesMgrCd = salesMgrCd;
    this.salesMgrName = salesMgrName;
    this.salesPicCd = salesPicCd;
    this.salesPicName = salesPicName;
    this.designVenderName = designVenderName;
    this.designPicNm = designPicNm;
    this.roughEstTotalAmt = roughEstTotalAmt;
    this.businessDataSt = businessDataSt;
  }
}
