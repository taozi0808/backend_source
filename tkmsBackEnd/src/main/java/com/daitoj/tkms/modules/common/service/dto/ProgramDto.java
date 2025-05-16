package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 機能情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.ProgramDto", description = "機能情報")
public class ProgramDto {
  /** 機能ID */
  @Schema(description = "機能ID")
  private String pgId;

  /** 機能名 */
  @Schema(description = "機能名")
  private String pgNm;

  /** 機能説明 */
  @Schema(description = "機能説明")
  private String pgRemark;

  /** コンポーネント */
  @Schema(description = "コンポーネント")
  private String pgComponent;

  /** 承認権限チェック要否フラグ;0 */
  @Schema(description = "承認権限チェック要否フラグ;0")
  private String confirmPermChkFlg;

  /** 削除権限チェック要否フラグ;0 */
  @Schema(description = "削除権限チェック要否フラグ;0")
  private String deletePermChkFlg;

  /** 編集権限チェック要否フラグ;0 */
  @Schema(description = "編集権限チェック要否フラグ;0")
  private String editPermChkFlg;

  /** 参照権限チェック要否フラグ;0 */
  @Schema(description = "参照権限チェック要否フラグ;0")
  private String referPermChkFlg;

  /** 注文書権限チェック要否フラグ;0 */
  @Schema(description = "注文書権限チェック要否フラグ;0")
  private String poPermChkFlg;
}
