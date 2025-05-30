package com.daitoj.tkms.modules.apib0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

/** 案件取得情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ProjectResultDto", description = "案件取得情報")
public class ProjectResultDto extends ProjectDto {

  /** 受注状態項目内容 */
  @Schema(name = "orderStNm", description = "受注状態項目内容")
  private String orderStNm;

  /** 顧客コード */
  @NotNull
  @Size(max = 6)
  @Schema(
      name = "customerCd",
      description = "顧客コード",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 6)
  protected String customerCd;

  /** 顧客名 */
  @Schema(name = "customerNm", description = "顧客名")
  private String customerNm;

  /** 代表者名 */
  @Schema(name = "ceoNm", description = "代表者名")
  private String ceoNm;

  /** 顧客郵便番号 */
  @Schema(name = "custPostNo", description = "顧客郵便番号")
  private String custPostNo;

  /** 顧客住所１ */
  @Schema(name = "custAddr1", description = "顧客住所１")
  private String custAddr1;

  /** 顧客住所２ */
  @Schema(name = "custAddr2", description = "顧客住所２")
  private String custAddr2;

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

  /** 案件区分項目内容 */
  @Schema(name = "projectKNm", description = "案件区分項目内容")
  private String projectKNm;

  /** 官民区分項目内容 */
  @Schema(name = "govPeoKNm", description = "官民区分項目内容")
  private String govPeoKNm;

  /** 支払区分項目内容 */
  @Schema(name = "paymentKNm", description = "支払区分項目内容")
  private String paymentKNm;

  /**
   * コンストラクタ
   *
   * @param id 案件ID
   * @param projectCd 案件コード
   * @param hisNo 歴番
   * @param projectNm 案件名
   * @param projectKnNm 案件カナ名
   * @param orderStCd 受注状態コード
   * @param orderStNm 受注状態項目内容
   * @param customerBranchCd 顧客支店コード
   * @param customerCd 顧客コード
   * @param customerNm 顧客名
   * @param ceoNm 代表者名
   * @param expectAmt 想定金額
   * @param custPostNo 顧客郵便番号
   * @param custAddr1 顧客住所１
   * @param custAddr2 顧客住所２
   * @param postNo 郵便番号
   * @param constrSiteAddr1 現場住所１
   * @param constrSiteAddr2 現場住所２
   * @param orderExpectedYmd 受注見込日
   * @param startHopeYmd 着工希望日
   * @param compHopeYmd 完了希望日
   * @param salesOrgId 営業部門ID
   * @param salesOrgNm 営業部門名
   * @param salesMgrCd 営業管理職コード
   * @param salesMgrNm 営業管理職名
   * @param salesPicCd 営業担当者コード
   * @param salesPicNm 営業担当者名
   * @param designVendorNm 設計業者コード
   * @param designPicNm 設計担当者
   * @param progressCd 進捗度コード
   * @param progressNm 進捗度項目内容
   * @param projectK 案件区分
   * @param projectKNm 案件区分項目内容
   * @param govPeoK 官民区分
   * @param govPeoKNm 官民区分項目内容
   * @param greenSiteFlg グリンサイトフラグ
   * @param estSubmitDueTs 見積提出期限
   * @param siteArea 敷地面積
   * @param buildingArea 建築面積
   * @param grossFloorArea 延床面積
   * @param buildupArea 施工床面積
   * @param occupiedArea 専有面積
   * @param constrArea 施工面積
   * @param households 戸数
   * @param floorCnt 階数（地上）
   * @param basementCnt 階数（地下）
   * @param constrExpRate 工事経費率
   * @param constrExpAmt 工事経費金額
   * @param saleMgrRate 販売管理費率
   * @param saleMgrAmt 販売管理費金額
   * @param tyouseiAmt 調整金額
   * @param eisRate 雇用保険率
   * @param ehiRate 健康保険保険率
   * @param ltcRate 介護保険率
   * @param wpiRate 厚生年金率
   * @param closingDay 締日
   * @param paymentK 支払区分
   * @param paymentKNm 支払区分項目内容
   * @param paymentD 支払日
   * @param rejectionReason 不成約理由
   * @param delFlg 削除フラグ
   * @param regTs 登録日時
   * @param regUserId 登録者ID
   * @param regPgId 登録PG
   * @param updTs 更新日時
   * @param updUserId 更新者ID
   * @param updPgId 更新PG
   */
  public ProjectResultDto(
      Long id,
      String projectCd,
      Integer hisNo,
      String projectNm,
      String projectKnNm,
      String orderStCd,
      String orderStNm,
      String customerBranchCd,
      String customerCd,
      String customerNm,
      String ceoNm,
      BigDecimal expectAmt,
      String custPostNo,
      String custAddr1,
      String custAddr2,
      String postNo,
      String constrSiteAddr1,
      String constrSiteAddr2,
      String orderExpectedYmd,
      String startHopeYmd,
      String compHopeYmd,
      Long salesOrgId,
      String salesOrgNm,
      String salesMgrCd,
      String salesMgrNm,
      String salesPicCd,
      String salesPicNm,
      String designVendorNm,
      String designPicNm,
      String progressCd,
      String progressNm,
      String projectK,
      String projectKNm,
      String govPeoK,
      String govPeoKNm,
      String greenSiteFlg,
      Instant estSubmitDueTs,
      BigDecimal siteArea,
      BigDecimal buildingArea,
      BigDecimal grossFloorArea,
      BigDecimal buildupArea,
      BigDecimal occupiedArea,
      BigDecimal constrArea,
      BigDecimal households,
      BigDecimal floorCnt,
      BigDecimal basementCnt,
      BigDecimal constrExpRate,
      BigDecimal constrExpAmt,
      BigDecimal saleMgrRate,
      BigDecimal saleMgrAmt,
      BigDecimal tyouseiAmt,
      BigDecimal eisRate,
      BigDecimal ehiRate,
      BigDecimal ltcRate,
      BigDecimal wpiRate,
      String closingDay,
      String paymentK,
      String paymentKNm,
      String paymentD,
      String rejectionReason,
      String delFlg,
      Instant regTs,
      String regUserId,
      String regPgId,
      Instant updTs,
      String updUserId,
      String updPgId) {
    this.id = id;
    this.projectCd = projectCd;
    this.hisNo = hisNo;
    this.projectNm = projectNm;
    this.projectKnNm = projectKnNm;
    this.orderStCd = orderStCd;
    this.orderStNm = orderStNm;
    this.customerBranchCd = customerBranchCd;
    this.customerCd = customerCd;
    this.customerNm = customerNm;
    this.ceoNm = ceoNm;
    this.expectAmt = expectAmt;
    this.custPostNo = custPostNo;
    this.custAddr1 = custAddr1;
    this.custAddr2 = custAddr2;
    this.postNo = postNo;
    this.constrSiteAddr1 = constrSiteAddr1;
    this.constrSiteAddr2 = constrSiteAddr2;
    this.orderExpectedYmd = orderExpectedYmd;
    this.startHopeYmd = startHopeYmd;
    this.salesOrgId = salesOrgId;
    this.salesOrgNm = salesOrgNm;
    this.salesMgrCd = salesMgrCd;
    this.salesMgrNm = salesMgrNm;
    this.salesPicCd = salesPicCd;
    this.salesPicNm = salesPicNm;
    this.progressCd = progressCd;
    this.progressNm = progressNm;
    this.projectK = projectK;
    this.projectKNm = projectKNm;
    this.govPeoK = govPeoK;
    this.govPeoKNm = govPeoKNm;
    this.estSubmitDueTs = estSubmitDueTs;
    this.siteArea = siteArea;
    this.buildingArea = buildingArea;
    this.grossFloorArea = grossFloorArea;
    this.buildupArea = buildupArea;
    this.occupiedArea = occupiedArea;
    this.constrArea = constrArea;
    this.households = households;
    this.floorCnt = floorCnt;
    this.basementCnt = basementCnt;
    this.constrExpRate = constrExpRate;
    this.constrExpAmt = constrExpAmt;
    this.saleMgrRate = saleMgrRate;
    this.saleMgrAmt = saleMgrAmt;
    this.tyouseiAmt = tyouseiAmt;
    this.eisRate = eisRate;
    this.ehiRate = ehiRate;
    this.ltcRate = ltcRate;
    this.wpiRate = wpiRate;
    this.closingDay = closingDay;
    this.paymentK = paymentK;
    this.paymentKNm = paymentKNm;
    this.paymentD = paymentD;
    this.rejectionReason = rejectionReason;
    this.compHopeYmd = compHopeYmd;
    this.designVendorNm = designVendorNm;
    this.designPicNm = designPicNm;
    this.greenSiteFlg = greenSiteFlg;
    this.delFlg = delFlg;
    this.regTs = regTs;
    this.regUserId = regUserId;
    this.regPgId = regPgId;
    this.updTs = updTs;
    this.updUserId = updUserId;
    this.updPgId = updPgId;
  }
}
