package com.daitoj.tkms.modules.apir0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 部署権限リスト */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0010S01Dto", description = "部署権限リスト")
public class R0010S01Dto {

  /** 組織ID */
  @Schema(name = "orgid", description = "組織ID")
  private Long orgId;

  /** 組織コード */
  @Schema(name = "orgCd", description = "組織コード")
  private String orgCd;

  /** 組織名 */
  @Schema(name = "orgNm", description = "組織名")
  private String orgNm;

  /** メニュー項目ID */
  @Schema(name = "menuItemId", description = "メニュー項目ID")
  private Integer menuItemId;

  /** 参照権限 */
  @Schema(description = "参照権限(表示のみ)")
  private String referPerm;

  /** コンストラクタ */
  public R0010S01Dto() {}

  /**
   * コンストラクタ
   *
   * @param orgId 組織ID
   * @param orgCd 組織コード
   * @param orgNm 組織名
   * @param menuItemId メニュー項目ID
   * @param referPerm 参照権限
   */
  public R0010S01Dto(Long orgId, String orgCd, String orgNm, Integer menuItemId, String referPerm) {
    this.orgId = orgId;
    this.orgCd = orgCd;
    this.orgNm = orgNm;
    this.menuItemId = menuItemId;
    this.referPerm = referPerm;
  }
}
