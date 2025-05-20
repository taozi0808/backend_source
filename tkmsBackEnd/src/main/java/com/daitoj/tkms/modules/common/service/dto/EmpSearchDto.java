package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 従業員検索情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.EmpSearchDto", description = "従業員検索情報")
public class EmpSearchDto {
  /** 従業員コード */
  @Schema(description = "従業員コード")
  private String empCd;

  /** 従業員氏 */
  @Schema(description = "従業員氏")
  private String empFamNm;

  /** 従業員名 */
  @Schema(description = "従業員名")
  private String empPerNm;

  /** 従業員氏名 */
  @Schema(description = "従業員氏名")
  private String empNm;
}
