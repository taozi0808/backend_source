package com.daitoj.tkms.modules.apis0050.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 承認一覧（査定管理）初期パラメータ.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0050S01Dto", description = "承認一覧（査定管理）パラメータ")
public class S0050S01Dto {

  /**
   * 従業員コード.
   */
  @Schema(name = "empCd", description = "従業員コード")
  @NotNull
  private String empCd;

  /**
   * 現場コード.
   */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /**
   * 現場所長.
   * */
  @Schema(name = "constrSiteDirectorNm", description = "現場所長")
  private String constrSiteDirectorNm;

  /**
   * 申請日（開始）.
   */
  @DateTimeFormat(pattern = "yyyyMMdd")
  @JsonFormat(pattern = "yyyyMMdd")
  @Schema(
      name = "requestDateFrom",
      description = "申請日（開始）",
      example = "20250520",
      type = "string",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String requestDateFrom;

  /**
   * 申請日（終了）.
   */
  @DateTimeFormat(pattern = "yyyyMMdd")
  @JsonFormat(pattern = "yyyyMMdd")
  @Schema(
      name = "requestDateTo",
      description = "申請日（終了）",
      example = "20250520",
      type = "string",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String requestDateTo;

  /**
   * 申請事業所.
   */
  @Schema(name = "requestOfficeNm", description = "申請事業所")
  private String requestOfficeNm;

  /**
   * 申請者.
   */
  @Schema(name = "requestEmpNm", description = "申請者")
  private String requestEmpNm;

  /**
   * 承認状態リスト.
   */
  @Schema(
      name = "listApprStatus",
      description = "承認状態リスト",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private List<String> listApprStatus;
}
