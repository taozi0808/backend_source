package com.daitoj.tkms.modules.apih0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 査定情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "AssessInfoDto", description = "査定一覧検索結果")
public class AssessmentInfoDto {
  /* 現場コード */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /** コンストラクタ. */
  public AssessmentInfoDto(String constrSiteCd) {
    this.constrSiteCd = constrSiteCd;
  }
}
