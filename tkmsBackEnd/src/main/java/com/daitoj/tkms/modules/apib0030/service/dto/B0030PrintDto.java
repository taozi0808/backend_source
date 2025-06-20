package com.daitoj.tkms.modules.apib0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案件登録印刷パラメータ.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0030PrintDto", description = "案件登録印刷パラメータ")
public class B0030PrintDto extends ProjectDto {

  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM月dd日HH:mm:ss)")
  private String sysDate;

  /** 案件区分項目内容 */
  @Schema(name = "projectKNm", description = "案件区分項目内容")
  private String projectKNm;

  /** 現場コード */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /** 現場名 */
  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  /** 物件コード */
  @Schema(name = "projectSiteCd", description = "物件コード")
  private String projectSiteCd;

  /** 物件名 */
  @Schema(name = "projectSiteNm", description = "物件名")
  private String projectSiteNm;

  /** 物件カナ名 */
  @Schema(name = "projectSiteKnNm", description = "物件カナ名")
  private String projectSiteKnNm;

  /** 概算コード */
  @Schema(name = "roughEstCd", description = "概算コード")
  private String roughEstCd;

  /** 顧客名 */
  @Schema(name = "customerNm", description = "顧客名")
  private String customerNm;

  /** 顧客郵便番号 */
  @Schema(name = "custPostNo", description = "顧客郵便番号")
  private String custPostNo;

  /** 顧客住所１ */
  @Schema(name = "custAddr1", description = "顧客住所１")
  private String custAddr1;

  /** 顧客住所２ */
  @Schema(name = "custAddr2", description = "顧客住所２")
  private String custAddr2;

  /** 官民区分項目内容 */
  @Schema(name = "govPeoKNm", description = "官民区分項目内容")
  private String govPeoKNm;

  /** 営業部門名 */
  @Schema(name = "salesOrgNm", description = "営業部門名")
  private String salesOrgNm;

  /** 営業管理職名 */
  @Schema(name = "salesMgrNm", description = "営業管理職名")
  private String salesMgrNm;

  /** 営業担当者名 */
  @Schema(name = "salesPicNm", description = "営業担当者名")
  private String salesPicNm;

  /** 進捗度項目内容 */
  @Schema(name = "progressNm", description = "進捗度項目内容")
  private String progressNm;

  /** 案件請求条件情報 */
  @Schema(description = "案件請求条件情報")
  private List<ProjectPaymentTermsDto> projectPaymentTerms;

  /** 現場棟明細情報 */
  @Schema(description = "現場棟明細情報")
  List<ProjectBuildingDtlDto> projectBuildingDtls;

  /** 案件要望明細情報 */
  @Schema(description = "案件要望明細情報")
  List<ProjectRequestPrintDto> projectRequestDtls;

  /** 敷地面積坪 */
  @Schema(
    name = "siteAreaTsu",
    description = "敷地面積坪")
  protected BigDecimal siteAreaTsu;

  /** 建築面積坪 */
  @Schema(
    name = "buildingAreaTsu",
    description = "建築面積坪")
  protected BigDecimal buildingAreaTsu;

  /** 延床面積坪 */
  @Schema(
    name = "grossFloorAreaTsu",
    description = "延床面積坪")
  protected BigDecimal grossFloorAreaTsu;

  /** 施工床面積坪 */
  @Schema(name = "buildupAreaTsu", description = "施工床面積坪")
  protected BigDecimal buildupAreaTsu;

  /** 専有面積坪 */
  @Schema(
    name = "occupiedAreaTsu",
    description = "専有面積坪")
  protected BigDecimal occupiedAreaTsu;

  /** 施工面積坪 */
  @Schema(name = "constrAreaTsu", description = "施工面積坪")
  protected BigDecimal constrAreaTsu;

  /** 支払区分項目内容 */
  @Schema(name = "paymentKNm", description = "支払区分項目内容")
  private String paymentKNm;

  /** 締日区分項目内容 */
  @Schema(name = "closingDayNm", description = "締日区分項目内容")
  protected String closingDayNm;

  /** 支払日区分項目内容 */
  @Schema(name = "paymentDNm", description = "支払日区分項目内容")
  protected String paymentDNm;

  /** 案件請求条件情報合計パラメータ */
  @Schema(name = "param", description = "案件請求条件情報合計パラメータ")
  private Map<String, BigDecimal> param = new HashMap<>();

}
