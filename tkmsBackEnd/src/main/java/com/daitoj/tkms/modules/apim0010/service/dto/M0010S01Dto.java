package com.daitoj.tkms.modules.apim0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/** 現場経費情報パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "M0010S01Dto", description = "案件一覧現場経費情報パラメータ")
public class M0010S01Dto extends BasePageRequest {

  /** 所属事業所コード */
  @NotNull
  @Schema(name = "belongOfficeCd", description = "所属事業所コード")
  private String belongOfficeCd;

  /** 現場コード */
  @Schema(name = "genbaCode", description = "現場コード")
  private String genbaCode;

  /** 現場名（ｶﾅ含む） */
  @Schema(name = "genbaName", description = "現場名")
  private String genbaName;

  /** 申請者 */
  @Schema(name = "shinseisyaCode", description = "申請者")
  private String shinseisyaCode;

  /** 現場着手日（開始） */
  @Schema(name = "gcYmdStart", description = "現場着手日（開始）(yyyy-MM-dd)", example = "2025-01-02")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate gcYmdStart;

  /** 現場着手日（終了） */
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Schema(name = "gcYmdEnd", description = "現場着手日（終了）(yyyy-MM-dd)", example = "2025-01-02")
  private LocalDate gcYmdEnd;

  /** 現場引渡日（開始） */
  @Schema(name = "ghYmdStart", description = "現場引渡日（開始）(yyyy-MM-dd)", example = "2025-01-02")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate ghYmdStart;

  /** 現場引渡日（終了） */
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Schema(name = "ghYmdEnd", description = "現場引渡日（終了）(yyyy-MM-dd)", example = "2025-01-02")
  private LocalDate ghYmdEnd;
}
