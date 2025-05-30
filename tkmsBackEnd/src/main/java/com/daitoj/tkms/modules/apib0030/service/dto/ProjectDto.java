package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

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
  protected Integer hisNo;

  /** 案件名 */
  @NotNull
  @Schema(name = "projectNm", description = "案件名", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String projectNm;

  /** 案件カナ名 */
  @Schema(name = "projectKnNm", description = "案件カナ名")
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
  @Schema(name = "constrSiteAddr2", description = "現場住所２")
  protected String constrSiteAddr2;

  /** 受注見込日 */
  @Size(max = 8)
  @Schema(name = "orderExpectedYmd", description = "受注見込日", maxLength = 8)
  protected String orderExpectedYmd;

  /** 着工希望日 */
  @Size(max = 8)
  @Schema(name = "startHopeYmd", description = "着工希望日", maxLength = 8)
  protected String startHopeYmd;

  /** 完了希望日 */
  @Schema(name = "compHopeYmd", description = "完了希望日", maxLength = 8)
  protected String compHopeYmd;

  /** 営業部門ID */
  @Schema(name = "salesOrgId", description = "営業部門ID")
  protected Long salesOrgId;

  /** 営業管理職コード */
  @Size(max = 6)
  @Schema(name = "salesMgrCd", description = "営業管理職コード", maxLength = 6)
  protected String salesMgrCd;

  /** 営業担当者コード */
  @Schema(name = "salesPicCd", description = "営業担当者コード")
  protected String salesPicCd;

  /** 設計業者名 */
  @Schema(name = "designVendor", description = "設計業者名")
  protected String designVendorNm;

  /** 設計担当者名 */
  @Schema(name = "designPicNm", description = "設計担当者名")
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
  @Schema(name = "greenSiteFlg", description = "グリンサイトフラグ")
  protected String greenSiteFlg = "0";

  /** 見積提出期限 */
  @Schema(name = "estSubmitDueTs", description = "見積提出期限")
  protected Instant estSubmitDueTs;

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
  @Digits(integer = 7, fraction = 2)
  @Schema(name = "buildupArea", description = "施工床面積", minimum = "0.01", maximum = "9999999.99")
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
  @Digits(integer = 7, fraction = 2)
  @Schema(name = "constrArea", description = "施工面積", minimum = "0.01", maximum = "9999999.99")
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

  /** 工事経費率 */
  @NotNull
  @Digits(integer = 3, fraction = 2)
  @Schema(
      name = "constrExpRate",
      description = "工事経費率",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "999.99")
  protected BigDecimal constrExpRate;

  /** 工事経費金額 */
  @NotNull
  @DecimalMax(value = "99999999999")
  protected BigDecimal constrExpAmt;

  /** 販売管理費率 */
  @NotNull
  @Digits(integer = 3, fraction = 2)
  @Schema(
      name = "saleMgrRate",
      description = "販売管理費率",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "999.99")
  protected BigDecimal saleMgrRate;

  /** 販売管理費金額 */
  @NotNull
  @DecimalMax(value = "99999999999")
  protected BigDecimal saleMgrAmt;

  /** 調整金額 */
  @NotNull
  @DecimalMax(value = "99999999999")
  protected BigDecimal tyouseiAmt;

  /** 雇用保険率 */
  @NotNull
  @Digits(integer = 3, fraction = 2)
  @Schema(
      name = "eisRate",
      description = "雇用保険率",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "999.99")
  protected BigDecimal eisRate;

  /** 健康保険保険率 */
  @NotNull
  @Digits(integer = 3, fraction = 2)
  @Schema(
      name = "ehiRate",
      description = "健康保険保険率",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "999.99")
  protected BigDecimal ehiRate;

  /** 介護保険率 */
  @NotNull
  @Digits(integer = 3, fraction = 2)
  @Schema(
      name = "ltcRate",
      description = "介護保険率",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "999.99")
  protected BigDecimal ltcRate;

  /** 厚生年金率 */
  @NotNull
  @Digits(integer = 3, fraction = 2)
  @Schema(
      name = "wpiRate",
      description = "厚生年金率",
      requiredMode = Schema.RequiredMode.REQUIRED,
      minimum = "0.01",
      maximum = "999.99")
  protected BigDecimal wpiRate;

  /** 締日区分 */
  @Schema(name = "closingDay", description = "締日区分")
  protected String closingDay;

  /** 支払区分 */
  @Schema(name = "paymentK", description = "支払区分")
  protected String paymentK;

  /** 支払日区分 */
  @Schema(name = "paymentD", description = "支払日区分")
  protected String paymentD;

  /** 不成約理由 */
  @Schema(name = "rejectionReason", description = "不成約理由")
  protected String rejectionReason;

  /** 物件コード */
  @Schema(name = "projectSiteCd", description = "物件コード ")
  private String projectSiteCd;
}
