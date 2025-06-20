package com.daitoj.tkms.modules.apis0140.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/** 承認一覧（自社情報）初期パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "S0140S01Dto", description = "承認一覧（自社情報）パラメータ")
public class S0140S01Dto {

  /** 業者コード. */
  @Schema(name = "vendorCd", description = "業者コード")
  @NotNull
  private String vendorCd;

  /** 作業員コード. */
  @Schema(name = "workerCd", description = "作業員コード")
  private String workerCd;

  /** 協力業者コード. */
  @Schema(name = "partnerVendorCd", description = "協力業者コード")
  private String partnerVendorCd;

  /** 会社名（ｶﾅ含む）. */
  @Schema(name = "compNm", description = "会社名（ｶﾅ含む）")
  private String compNm;

  /** 申請日（開始）. */
  @DateTimeFormat(pattern = "yyyyMMdd")
  @JsonFormat(pattern = "yyyyMMdd")
  @Schema(
      name = "requestDateFrom",
      description = "申請日（開始）",
      example = "20250520",
      type = "string",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String requestDateFrom;

  /** 申請日（終了）. */
  @DateTimeFormat(pattern = "yyyyMMdd")
  @JsonFormat(pattern = "yyyyMMdd")
  @Schema(
      name = "requestDateTo",
      description = "申請日（終了）",
      example = "20250520",
      type = "string",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String requestDateTo;

  /** 承認状態リスト. */
  @Schema(
      name = "listApprStatus",
      description = "承認状態リスト",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private List<String> listApprStatus;
}
