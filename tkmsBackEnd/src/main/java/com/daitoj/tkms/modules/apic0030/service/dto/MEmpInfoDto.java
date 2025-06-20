package com.daitoj.tkms.modules.apic0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/** 概算情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "MEmpInfoDto", description = "検索結果")
public class MEmpInfoDto {

  /** 従業員コード */
  @Schema(description = "従業員コード")
  private String empCd;

  /** 従業員氏名 */
  @Schema(description = "従業員氏名")
  private String empNm;

  /** コンストラクタ */
  public MEmpInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param empCd 従業員コード
   * @param empNm 従業員氏名
   */
  public MEmpInfoDto(String empCd, String empNm) {
    this.empCd = empCd;
    this.empNm = empNm;
  }
}
