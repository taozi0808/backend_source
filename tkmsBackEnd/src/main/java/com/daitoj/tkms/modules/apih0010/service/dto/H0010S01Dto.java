package com.daitoj.tkms.modules.apih0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 査定一覧パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "H0010S01Dto", description = "査定一覧パラメータ")
public class H0010S01Dto extends BasePageRequest {

  /* 現場コード */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /* 現場名 */
  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  /* 現場カナ名 */
  @Schema(name = "constrSiteKnNm", description = "現場カナ名")
  private String constrSiteKnNm;

  /* 専任技術者 */
  @Schema(name = "empNm", description = "専任技術者")
  private String empNm;

  /*　担当事業所コード　*/
  @Schema(name = "belongOfficeCd", description = "担当事業所コード")
  private String belongOfficeCd;

  /*　組織名　*/
  @Schema(name = "orgNm", description = "組織名")
  private String orgNm;

  /*　工事工程　*/
  @Schema(name = "constrProcessNm", description = "工事工程")
  private String constrProcessNm;

  /*　予算作成部門ID　*/
  @Schema(name = "bgtCreateOrgId", description = "予算作成部門ID")
  private String bgtCreateOrgId;

  /*　実行予算コード　*/
  @Schema(name = "execBgtCd", description = "実行予算コード")
  private String execBgtCd;

  /*　現場所長　*/
  @Schema(name = "constrSiteDirectorNm", description = "現場所長")
  private String constrSiteDirectorNm;

  /* 工事部門 */
  @Schema(name = "constrOrgId", description = "工事部門")
  private Long constrOrgId;

  /* 査定承認日 */
  @Schema(name = "finalApprDt", description = "査定承認日")
  private String finalApprDt;

  /* 実行予算金額 */
  @Schema(name = "execBgtTotalAmt", description = "実行予算金額")
  private BigDecimal execBgtTotalAmt;

  /* 発注金額 */
  @Schema(name = "poTotalAmt", description = "発注金額")
  private BigDecimal poTotalAmt;

  /* 前月迄査定済金額 */
  @Schema(name = "assessTotalAmt", description = "前月迄査定済金額")
  private BigDecimal assessTotalAmt;

  /* 当月査定金額 */
  @Schema(name = "mtdAssessTotalAmt", description = " 当月査定金額 ")
  private BigDecimal mtdAssessTotalAmt;

  /** 査定済合計金額. */
  @Schema(name = "assessmentTotalAmount", description = "査定済合計金額")
  private BigDecimal assessmentTotalAmount;

  /** 未査定残. */
  @Schema(name = "unidentified", description = "未査定残")
  private BigDecimal unidentified;

  /** 表示対象. */
  @Schema(name = "displayobject", description = "表示対象")
  private String displayobject;
}
