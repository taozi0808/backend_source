package com.daitoj.tkms.modules.apia0040.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 申請・承認待ち情報. */
@lombok.Setter
@lombok.Getter
@Schema(name = "ApplyInfoDto", description = "申請・承認待ち情報検索結果")
public class ApplyInfoDto {

  /* 一般申請名 */
  @Schema(name = "genApplTitle", description = "一般申請名")
  private String genApplTitle;

  /* 業務データステータス */
  @Schema(name = "businessDataSt", description = "業務データステータス")
  private String businessDataSt;

  /* 申請日時 */
  @Schema(name = "requestDate", description = "申請日時")
  private String requestDate;

  /**
   * コンストラクタ.
   *
   * @param genApplTitle 一般申請名
   * @param businessDataSt 業務データステータス
   * @param requestDate 申請日時
   */
  public ApplyInfoDto(String genApplTitle, String businessDataSt, String requestDate) {
    this.genApplTitle = genApplTitle;
    this.businessDataSt = businessDataSt;
    this.requestDate = requestDate;
  }

}
