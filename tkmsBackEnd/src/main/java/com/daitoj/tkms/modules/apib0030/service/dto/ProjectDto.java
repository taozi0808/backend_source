package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/** 案件情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ProjectDto", description = "案件情報")
public class ProjectDto extends BaseDto {
  /** 案件ID */
  @Schema(name = "id", description = "案件ID")
  protected Long id;

  /** 案件コード */
  @Schema(name = "projectCd", description = "案件コード")
  protected String projectCd;

  /** 歴番 */
  @Schema(name = "hisNo", description = "歴番")
  protected String hisNo;

  /** 案件名 */
  @NotNull
  @Schema(name = "projectNm", description = "案件名", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String projectNm;

  /** 案件カナ名 */
  @NotNull
  @Schema(name = "projectKnNm", description = "案件カナ名", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String projectKnNm;

  /** 受注状態コード */
  @NotNull
  @Schema(name = "orderStCd", description = "受注状態コード", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String orderStCd;

  /** 顧客支店コード */
  @NotNull
  @Size(max = 9)
  @Schema(
      name = "customerBranchCd",
      description = "顧客支店コード",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 9)
  protected String customerBranchCd;

  /** 想定金額 */
  @NotNull
  @DecimalMax(value = "99999999999")
  @Schema(
      name = "expectAmt",
      description = "想定金額",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 11)
  protected BigDecimal expectAmt;

  /** 郵便番号 */
  @NotNull
  @Size(max = 7)
  @Schema(
      name = "postNo",
      description = "郵便番号",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 7)
  protected String postNo;

  /** 現場住所１ */
  @NotNull
  @Schema(
      name = "constrSiteAddr1",
      description = "現場住所１",
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String constrSiteAddr1;

  /** 現場住所２ */
  @NotNull
  @Schema(
      name = "constrSiteAddr2",
      description = "現場住所２",
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String constrSiteAddr2;

  /** 受注見込日 */
  @NotNull
  @Size(max = 8)
  @Schema(
      name = "orderExpectedYmd",
      description = "受注見込日",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 8)
  protected String orderExpectedYmd;

  /** 着工希望日 */
  @NotNull
  @Size(max = 8)
  @Schema(
      name = "startHopeYmd",
      description = "着工希望日",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 8)
  protected String startHopeYmd;

  /** 完了希望日 */
  @NotNull
  @Schema(
      name = "compHopeYmd",
      description = "完了希望日",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 8)
  protected String compHopeYmd;

  /** 営業部門適用開始日付 */
  @NotNull
  @Size(max = 8)
  @Schema(
      name = "salesDeptStartDt",
      description = "営業部門適用開始日付",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 8)
  protected String salesDeptStartDt;

  /** 営業部門ID */
  @NotNull
  @Schema(name = "salesOrgId", description = "営業部門ID", requiredMode = Schema.RequiredMode.REQUIRED)
  protected Long salesOrgId;

  /** 営業管理職コード */
  @NotNull
  @Size(max = 6)
  @Schema(
      name = "salesMgrCd",
      description = "営業管理職コード",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 6)
  protected String salesMgrCd;

  /** 営業担当者コード */
  @NotNull
  @Schema(
      name = "salesPicCd",
      description = "営業担当者コード",
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String salesPicCd;

  /** 設計業者コード */
  @NotNull
  @Schema(
      name = "designVender",
      description = "設計業者コード",
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String designVender;

  /** 設計担当者 */
  @NotNull
  @Schema(name = "designPicNm", description = "設計担当者", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String designPicNm;

  /** 進捗度コード */
  @NotNull
  @Size(max = 2)
  @Schema(
      name = "progressCd",
      description = "進捗度コード",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 2)
  protected String progressCd;

  /** 案件区分 */
  @NotNull
  @Schema(name = "projectK", description = "案件区分", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String projectK;

  /** 官民区分 */
  @NotNull
  @Schema(name = "govPeoK", description = "官民区分", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String govPeoK;

  /** グリンサイトフラグ */
  @NotNull
  @Schema(
      name = "greenSiteFlg",
      description = "グリンサイトフラグ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String greenSiteFlg;

  /** 見積提出期限 */
  @Size(max = 8)
  @NotNull
  @Schema(
      name = "estSubmitDueDt",
      description = "見積提出期限",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 8)
  protected String estSubmitDueDt;

  /** 敷地面積 */
  @NotNull
  @Digits(integer = 7, fraction = 2)
  @Schema(
      name = "siteArea",
      description = "敷地面積",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "9999999.99")
  protected BigDecimal siteArea;

  /** 建築面積 */
  @NotNull
  @Digits(integer = 7, fraction = 2)
  @Schema(
      name = "buildingArea",
      description = "建築面積",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "9999999.99")
  protected BigDecimal buildingArea;

  /** 延床面積 */
  @NotNull
  @Digits(integer = 7, fraction = 2)
  @Schema(
      name = "grossFloorArea",
      description = "延床面積",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "9999999.99")
  protected BigDecimal grossFloorArea;

  /** 施工床面積 */
  @NotNull
  @Digits(integer = 7, fraction = 2)
  @Schema(
      name = "buildupArea",
      description = "施工床面積",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "9999999.99")
  protected BigDecimal buildupArea;

  /** 専有面積 */
  @NotNull
  @Digits(integer = 7, fraction = 2)
  @Schema(
      name = "occupiedArea",
      description = "専有面積",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "9999999.99")
  protected BigDecimal occupiedArea;

  /** 施工面積 */
  @NotNull
  @Digits(integer = 7, fraction = 2)
  @Schema(
      name = "constrArea",
      description = "施工面積",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "9999999.99")
  protected BigDecimal constrArea;

  /** 戸数 */
  @NotNull
  @DecimalMax(value = "9999999")
  @Schema(
      name = "households",
      description = "戸数",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 7)
  protected BigDecimal households;

  /** 階数（地上） */
  @NotNull
  @DecimalMax(value = "999")
  @Schema(
      name = "floorCnt",
      description = "階数（地上）",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 3)
  protected BigDecimal floorCnt;

  /** 階数（地下） */
  @NotNull
  @DecimalMax(value = "999")
  @Schema(
      name = "basementCnt",
      description = "階数（地下）",
      requiredMode = Schema.RequiredMode.REQUIRED,
      maxLength = 3)
  protected BigDecimal basementCnt;

  /** 締日 */
  @NotNull
  @Schema(name = "closingDay", description = "締日", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String closingDay;

  /** 支払区分 */
  @NotNull
  @Schema(name = "paymentK", description = "支払区分", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String paymentK;

  /** 支払日 */
  @NotNull
  @Schema(name = "paymentD", description = "支払日", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String paymentD;

  /** 不成約理由 */
  @NotNull
  @Schema(
      name = "rejectionReason",
      description = "不成約理由",
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String rejectionReason;
}
