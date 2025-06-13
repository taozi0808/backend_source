package com.daitoj.tkms.modules.apir0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 部署選択肢 */
@lombok.Getter
@lombok.Setter
@Schema(name = "MorGInfoDto", description = "部署選択肢結果")
public class MorGInfoDto {
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
  public MorGInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param orgId 組織ID
   * @param orgCd 組織コード
   * @param orgNm 組織名
   */
  public MorGInfoDto(Long orgId, String orgCd, String orgNm) {
    this.orgId = orgId;
    this.orgCd = orgCd;
    this.orgNm = orgNm;
  }
}
