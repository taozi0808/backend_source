package com.daitoj.tkms.modules.apir0060.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

/** 顧客管理 */
@lombok.Getter
@lombok.Setter
@Schema(name = "CustomerInfoDto", description = "検索結果")
public class CustomerInfoDto {

  /** 歴番 */
  @Schema(name = "hisNo", description = "歴番")
  private Integer hisNo;

  @Schema(name = "businessDataSt", description = " 業務データステータス")
  private String businessDataSt;

  @Schema(name = "id", description = " 顧客ID")
  private Long id;

  /** コンストラクタ. */
  public CustomerInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param id 顧客ID
   * @param hisNo 歴番
   * @param businessDataSt 業務データステータス
   */
  public CustomerInfoDto(Long id, Integer hisNo, String businessDataSt) {
    this.id = id;
    this.hisNo = hisNo;
    this.businessDataSt = businessDataSt;
  }
}
