package com.daitoj.tkms.modules.apir0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 役職権限リスト */
@lombok.Getter
@lombok.Setter
@Schema(name = "R00100S02Dto", description = "役職権限リスト")
public class R0010S02Dto {

  /** 役職コード */
  @Schema(name = "positionCd", description = "役職コード")
  private String positionCd;

  /** 役職名 */
  @Schema(name = "positionNm", description = "役職名(表示のみ)")
  private String positionNm;

  /** 初期役職区分;0 */
  @Schema(name = "initPositionK", description = "初期役職区分(表示のみ)")
  private String initPositionK;

  /** 承認権限;0 */
  @Schema(name = "confirmPerm", description = "承認権限(表示のみ)")
  private String confirmPerm;

  /** 削除権限 */
  @Schema(name = "deletePerm", description = "削除権限(表示のみ)")
  private String deletePerm;

  /** 編集権限 */
  @Schema(name = "editPerm", description = "編集権限(表示のみ)")
  private String editPerm;

  /** 参照権限 */
  @Schema(name = "referPerm", description = "参照権限(表示のみ)")
  private String referPerm;

  /** コンストラクタ */
  public R0010S02Dto() {}

  /**
   * コンストラクタ
   *
   * @param positionCd 役職コード
   * @param positionNm 役職名
   * @param initPositionK 初期役職区分
   * @param confirmPerm 承認権限
   * @param deletePerm 削除権限
   * @param editPerm 編集権限
   * @param referPerm 参照権限
   */
  public R0010S02Dto(
      String positionCd,
      String positionNm,
      String initPositionK,
      String confirmPerm,
      String deletePerm,
      String editPerm,
      String referPerm) {
    this.positionCd = positionCd;
    this.positionNm = positionNm;
    this.initPositionK = initPositionK;
    this.confirmPerm = confirmPerm;
    this.deletePerm = deletePerm;
    this.editPerm = editPerm;
    this.referPerm = referPerm;
  }
}
