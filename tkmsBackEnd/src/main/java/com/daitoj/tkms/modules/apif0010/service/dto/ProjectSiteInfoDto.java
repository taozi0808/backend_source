package com.daitoj.tkms.modules.apif0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 物件情報. */
@lombok.Setter
@lombok.Getter
@Schema(name = "ProjectSiteInfoDto", description = "物件情報検索結果")
public class ProjectSiteInfoDto {

  /* 物件コード */
  @Schema(name = "projectSiteCd", description = "物件コード")
  protected String projectSiteCd;

  /* 物件名 */
  @Schema(name = "projectSiteNm", description = "物件名")
  protected String projectSiteNm;

  /* 物件カナ名 */
  @Schema(name = "projectSiteKanaNm", description = "物件カナ名")
  protected String projectSiteKanaNm;

  /* 担当事業所コード */
  @Schema(name = "icOfficeCd", description = "担当事業所コード")
  protected String icOfficeCd;

  /* 担当事業所名 */
  @Schema(name = "icOfficeNm", description = "担当事業所名")
  protected String icOfficeNm;

  /* 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  protected String customerCd;

  /* 顧客名 */
  @Schema(name = "customerNm", description = "顧客名")
  protected String customerNm;

  /* 顧客カナ名 */
  @Schema(name = "customerKanaNm", description = "顧客カナ名")
  protected String customerKanaNm;

  /* 受注年月日 */
  @Schema(name = "orderYmd", description = "受注年月日")
  protected String orderYmd;

  /* 請負金額 */
  @Schema(name = "inclTaxCoTotalAmt", description = "請負金額")
  protected BigDecimal inclTaxCoTotalAmt;

  /* 工事部門コード */
  @Schema(name = "constrOrgCd", description = "工事部門コード")
  protected String constrOrgCd;

  /* 工事部門名 */
  @Schema(name = "constrOrgNm", description = "工事部門名")
  protected String constrOrgNm;

  /* 工事管理職コード */
  @Schema(name = "constrMgrCd", description = "工事管理職コード")
  protected String constrMgrCd;

  /* 工事管理職名 */
  @Schema(name = "constMgrNm", description = "工事管理職名")
  protected String constMgrNm;

  /* 専任技術者コード */
  @Schema(name = "ftEngineerCd", description = "専任技術者コード")
  protected String ftEngineerCd;

  /* 専任技術者名 */
  @Schema(name = "ftEngineerNm", description = "専任技術者名")
  protected String ftEngineerNm;

  /* 施工担当者コード */
  @Schema(name = "constrPicCd", description = "施工担当者コード")
  protected String constrPicCd;

  /* 施工担当者 */
  @Schema(name = "constrPicNm", description = "施工担当者名")
  protected String constrPicNm;

  /* 顧客略称 */
  @Schema(name = "customerRyakusyou", description = "顧客略称")
  protected String customerRyakusyou;

  /* 顧客支店コード */
  @Schema(name = "customerBranchCd", description = "顧客支店コード")
  protected String customerBranchCd;

  /* 案件区分 */
  @Schema(name = "projectK", description = "案件区分")
  protected String projectK;

  /* 案件区分名 */
  @Schema(name = "projectKnm", description = "案件区分名")
  protected String projectKnm;

  /* 着工日 */
  @Schema(name = "constrStartYmd", description = "着工日")
  protected String constrStartYmd;

  /* 完工日 */
  @Schema(name = "constrCompYmd", description = "完工日")
  protected String constrCompYmd;

  /* 案件コード */
  @Schema(name = "projectCd", description = "案件コード")
  protected String projectCd;

  /* 案件名 */
  @Schema(name = "projectNm", description = "案件名")
  protected String projectNm;

  /* 構造区分 */
  @Schema(name = "structureK", description = "構造区分")
  protected String structureK;

  /* 構造区分名 */
  @Schema(name = "structureNm", description = "構造区分名")
  protected String structureNm;

  /* 階数(地上) */
  @Schema(name = "floorCnt", description = "階数(地上)")
  protected BigDecimal floorCnt;

  /* 戸数 */
  @Schema(name = "households", description = "戸数")
  protected BigDecimal households;

  /* 物件住所 */
  @Schema(name = "projectSiteAddr", description = "物件住所")
  protected String projectSiteAddr;

  /* 担当者 */
  @Schema(name = "constrSitePicNm", description = "担当者")
  protected String constrSitePicNm;

  /* 現場担当者コード1 */
  @Schema(name = "constrSitePicCd1", description = "現場担当者コード1")
  protected String constrSitePicCd1;

  /* 現場担当者1 */
  @Schema(name = "constrSitePicNm1", description = "現場担当者1")
  protected String constrSitePicNm1;

  /* 現場担当者コード2 */
  @Schema(name = "constrSitePicCd2", description = "現場担当者コード2")
  protected String constrSitePicCd2;

  /* 現場担当者2 */
  @Schema(name = "constrSitePicNm2", description = "現場担当者2")
  protected String constrSitePicNm2;

  /* 現場担当者コード3 */
  @Schema(name = "constrSitePicCd3", description = "現場担当者コード3")
  protected String constrSitePicCd3;

  /* 現場担当者3 */
  @Schema(name = "constrSitePicNm3", description = "現場担当者3")
  protected String constrSitePicNm3;

  /* 現場担当者コード4 */
  @Schema(name = "constrSitePicCd4", description = "現場担当者コード4")
  protected String constrSitePicCd4;

  /* 現場担当者4 */
  @Schema(name = "constrSitePicNm4", description = "現場担当者4")
  protected String constrSitePicNm4;

  /* 現場担当者コード5 */
  @Schema(name = "constrSitePicCd5", description = "現場担当者コード5")
  protected String constrSitePicCd5;

  /* 現場担当者5 */
  @Schema(name = "constrSitePicNm5", description = "現場担当者5")
  protected String constrSitePicNm5;

  /* 現場担当TEL */
  @Schema(name = "constrSitePicTel", description = "現場担当TEL")
  protected String constrSitePicTel;

  /* 現場担当1TEL */
  @Schema(name = "constrSitePicTel1", description = "現場担当1TEL")
  protected String constrSitePicTel1;

  /* 現場担当2TEL */
  @Schema(name = "constrSitePicTel2", description = "現場担当2TEL")
  protected String constrSitePicTel2;

  /* 現場所長コード */
  @Schema(name = "constrSiteDirectorCd", description = "現場所長コード")
  protected String constrSiteDirectorCd;

  /* 現場所長 */
  @Schema(name = "constrSiteDirectorNm", description = "現場所長")
  protected String constrSiteDirectorNm;

  /* 所長代理コード */
  @Schema(name = "deputyDirectorCd", description = "所長代理コード")
  protected String deputyDirectorCd;

  /* 所長代理 */
  @Schema(name = "deputyDirectorNm", description = "所長代理")
  protected String deputyDirectorNm;

  /* 副所長コード */
  @Schema(name = "viceDirectorCd", description = "副所長コード")
  protected String viceDirectorCd;

  /* 副所長 */
  @Schema(name = "viceDirectorNm", description = "副所長")
  protected String viceDirectorNm;

  /* 現場主任コード */
  @Schema(name = "constrSiteSupvCd", description = "現場主任コード")
  protected String constrSiteSupvCd;

  /* 現場主任 */
  @Schema(name = "constrSiteSupvNm", description = "現場主任")
  protected String constrSiteSupvNm;

  /* 外構・解体・着工 */
  @Schema(name = "exteriorConstructionDt", description = "外構・解体・着工")
  protected String exteriorConstructionDt;

  /* 上棟 */
  @Schema(name = "ridgepoleDt", description = "上棟")
  protected String ridgepoleDt;

  /**
   * コンストラクタ.
   *
   * @param projectSiteCd 物件コード
   * @param projectSiteNm 物件名
   * @param projectSiteKanaNm 物件カナ名
   * @param icOfficeCd 担当事業所コード
   * @param icOfficeNm 担当事業所名
   * @param customerCd 顧客コード
   * @param customerNm 顧客名
   * @param orderYmd 受注年月日
   * @param inclTaxCoTotalAmt 税込請負合計金額
   * @param constrOrgCd 工事部門コード
   * @param constrOrgNm 工事部門名
   * @param constrMgrCd 工事管理職コード
   * @param constMgrNm 工事管理職名
   * @param ftEngineerCd 専任技術者コード
   * @param ftEngineerNm 専任技術者名
   * @param constrPicCd 施工担当者コード
   * @param constrPicNm 施工担当者名
   * @param customerRyakusyou 顧客略称
   * @param customerBranchCd 顧客支店コード
   * @param projectK 案件区分
   * @param constrStartYmd 着工日
   * @param constrCompYmd 完工日
   * @param projectCd 案件コード
   * @param projectNm 案件名
   * @param structureK 構造区分
   * @param structureNm 構造区分名
   * @param floorCnt 階数(地上)
   * @param households 戸数
   * @param projectSiteAddr 物件住所
   * @param constrSitePicCd1 現場担当者コード1
   * @param constrSitePicNm1 現場担当者1
   * @param constrSitePicCd2 現場担当者コード2
   * @param constrSitePicNm2 現場担当者2
   * @param constrSitePicCd3 現場担当者コード3
   * @param constrSitePicNm3 現場担当者3
   * @param constrSitePicCd4 現場担当者コード4
   * @param constrSitePicNm4 現場担当者4
   * @param constrSitePicCd5 現場担当者コード5
   * @param constrSitePicNm5 現場担当者5
   * @param constrSitePicTel1 現場担当1TEL
   * @param constrSitePicTel2 現場担当2TEL
   * @param constrSiteDirectorCd 現場所長コード
   * @param constrSiteDirectorNm 現場所長
   * @param deputyDirectorCd 副所長コード
   * @param deputyDirectorNm 副所長
   * @param viceDirectorCd 所長代理コード
   * @param viceDirectorNm 所長代理
   * @param constrSiteSupvCd 現場主任コード
   * @param constrSiteSupvNm 現場主任
   * @param exteriorConstructionDt 外構・解体・着工
   * @param ridgepoleDt 上棟
   */
  public ProjectSiteInfoDto(
      String projectSiteCd,
      String projectSiteNm,
      String projectSiteKanaNm,
      String icOfficeCd,
      String icOfficeNm,
      String customerCd,
      String customerNm,
      String customerKanaNm,
      String orderYmd,
      BigDecimal inclTaxCoTotalAmt,
      String constrOrgCd,
      String constrOrgNm,
      String constrMgrCd,
      String constMgrNm,
      String ftEngineerCd,
      String ftEngineerNm,
      String constrPicCd,
      String constrPicNm,
      String customerRyakusyou,
      String customerBranchCd,
      String projectK,
      String projectKnm,
      String constrStartYmd,
      String constrCompYmd,
      String projectCd,
      String projectNm,
      String structureK,
      String structureNm,
      BigDecimal floorCnt,
      BigDecimal households,
      String projectSiteAddr,
      String constrSitePicCd1,
      String constrSitePicNm1,
      String constrSitePicCd2,
      String constrSitePicNm2,
      String constrSitePicCd3,
      String constrSitePicNm3,
      String constrSitePicCd4,
      String constrSitePicNm4,
      String constrSitePicCd5,
      String constrSitePicNm5,
      String constrSitePicTel1,
      String constrSitePicTel2,
      String constrSiteDirectorCd,
      String constrSiteDirectorNm,
      String deputyDirectorCd,
      String deputyDirectorNm,
      String viceDirectorCd,
      String viceDirectorNm,
      String constrSiteSupvCd,
      String constrSiteSupvNm,
      String exteriorConstructionDt,
      String ridgepoleDt) {
    this.projectSiteCd = projectSiteCd;
    this.projectSiteNm = projectSiteNm;
    this.projectSiteKanaNm = projectSiteKanaNm;
    this.icOfficeCd = icOfficeCd;
    this.icOfficeNm = icOfficeNm;
    this.customerCd = customerCd;
    this.customerNm = customerNm;
    this.customerKanaNm = customerKanaNm;
    this.orderYmd = orderYmd;
    this.inclTaxCoTotalAmt = inclTaxCoTotalAmt;
    this.constrOrgCd = constrOrgCd;
    this.constrOrgNm = constrOrgNm;
    this.constrMgrCd = constrMgrCd;
    this.constMgrNm = constMgrNm;
    this.ftEngineerCd = ftEngineerCd;
    this.ftEngineerNm = ftEngineerNm;
    this.constrPicCd = constrPicCd;
    this.constrPicNm = constrPicNm;
    this.customerRyakusyou = customerRyakusyou;
    this.customerBranchCd = customerBranchCd;
    this.projectK = projectK;
    this.projectKnm = projectKnm;
    this.constrStartYmd = constrStartYmd;
    this.constrCompYmd = constrCompYmd;
    this.projectCd = projectCd;
    this.projectNm = projectNm;
    this.structureK = structureK;
    this.structureNm = structureNm;
    this.floorCnt = floorCnt;
    this.households = households;
    this.projectSiteAddr = projectSiteAddr;
    this.constrSitePicCd1 = constrSitePicCd1;
    this.constrSitePicNm1 = constrSitePicNm1;
    this.constrSitePicCd2 = constrSitePicCd2;
    this.constrSitePicNm2 = constrSitePicNm2;
    this.constrSitePicCd3 = constrSitePicCd3;
    this.constrSitePicNm3 = constrSitePicNm3;
    this.constrSitePicCd4 = constrSitePicCd4;
    this.constrSitePicNm4 = constrSitePicNm4;
    this.constrSitePicCd5 = constrSitePicCd5;
    this.constrSitePicNm5 = constrSitePicNm5;
    this.constrSitePicTel1 = constrSitePicTel1;
    this.constrSitePicTel2 = constrSitePicTel2;
    this.constrSiteDirectorCd = constrSiteDirectorCd;
    this.constrSiteDirectorNm = constrSiteDirectorNm;
    this.deputyDirectorCd = deputyDirectorCd;
    this.deputyDirectorNm = deputyDirectorNm;
    this.viceDirectorCd = viceDirectorCd;
    this.viceDirectorNm = viceDirectorNm;
    this.constrSiteSupvCd = constrSiteSupvCd;
    this.constrSiteSupvNm = constrSiteSupvNm;
    this.exteriorConstructionDt = exteriorConstructionDt;
    this.ridgepoleDt = ridgepoleDt;
  }
}
