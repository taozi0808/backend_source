package com.daitoj.tkms.modules.apic0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/** 概算情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "OrgRevSearchInfoDto", description = "検索結果")
public class OrgRevSearchInfoDto {

  /** 組織ID */
  @Schema(name = "org_id", description = "組織ID")
  private Long orgId;

  /** 組織コード */
  @Schema(name = "orgCd", description = "組織コード")
  private String orgCd;

  /** 組織名 */
  @Schema(name = "orgNm", description = "組織名")
  private String orgNm;

  /** コンストラクタ */
  public OrgRevSearchInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param orgId 組織ID
   * @param orgCd 組織コード
   * @param orgNm 組織名
   */
  public OrgRevSearchInfoDto(Long orgId, String orgCd, String orgNm) {
    this.orgId = orgId;
    this.orgCd = orgCd;
    this.orgNm = orgNm;
  }
}
