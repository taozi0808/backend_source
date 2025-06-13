package com.daitoj.tkms.modules.apig0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/** 実行予算一覧パラメータ/. */
@lombok.Getter
@lombok.Setter
@Schema(name = "G0010S01Dto", description = "実行予算一覧パラメータ")
public class G0010S01Dto extends BasePageRequest {

  /** id. */
  @Schema(name = "id", description = "id")
  private Long id;

  /** 現場コード. */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /** 現場名. */
  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  /** 現場カナ名. */
  @Schema(name = "constrSiteKnNm", description = "現場カナ名")
  private String constrSiteKnNm;

  /** 実行予算コード. */
  @Schema(name = "execBgtCd", description = "実行予算コード")
  private String execBgtCd;

  /** 予算申請日（開始）. */
  @Schema(name = "bgtYmRegDtStart", description = "予算申請日（開始）")
  private String bgtYmRegDtStart;

  /** 予算申請日（終了）. */
  @Schema(name = "bgtYmRegDtEnd", description = "予算申請日（終了）")
  private String bgtYmRegDtEnd;

  /** 予算承認日（開始）. */
  @Schema(name = "finalApprDtStart", description = "予算承認日（開始）")
  private String finalApprDtStart;

  /** 予算承認日（終了）. */
  @Schema(name = "finalApprDtEnd", description = "予算承認日（終了）")
  private String finalApprDtEnd;

  /** 予算作成部門. */
  @Schema(name = "orgNm", description = "予算作成部門")
  private String orgNm;

  /** 予算作成者. */
  @Schema(name = "empNm", description = "予算作成者")
  private String empNm;

  /** 実行予算金額. */
  @Schema(name = "execBgtTotalAmt", description = "実行予算金額")
  private BigDecimal execBgtTotalAmt;

  /** 発注金額. */
  @Schema(name = "poAmt", description = "発注金額")
  private BigDecimal poAmt;

  /** 査定済金額. */
  @Schema(name = "assessTotalAmt", description = "査定済金額")
  private BigDecimal assessTotalAmt;

  /** 担当事業所コード. */
  @Schema(name = "belongOfficeCd", description = "")
  private String belongOfficeCd;

  /** 未発注額. */
  @Schema(name = "unpaid", description = "未発注額")
  private BigDecimal unpaid;

  /** 未査定残. */
  @Schema(name = "unidentified", description = "未査定残")
  private BigDecimal unidentified;

  /** 予算作成済を表示フラグ. */
  @Schema(name = "budgetMakingFlg", description = "予算作成済を表示フラグ")
  private String budgetMakingFlg;
}
