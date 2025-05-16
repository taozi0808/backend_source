package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** 役職情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.PositionDto", description = "役職情報")
public class PositionDto {
  /** 役職コード */
  @NotNull
  @Size(max = 2)
  @Schema(description = "役職コード", maxLength = 2, requiredMode = Schema.RequiredMode.REQUIRED)
  private String positionCd;

  /** 役職名 */
  @Schema(description = "役職名(表示のみ)")
  private String positionNm;

  /** 初期役職区分;0 */
  @Schema(description = "初期役職区分(表示のみ)")
  private String initPositionK;

  /** 承認権限;0 */
  @Schema(description = "承認権限(表示のみ)")
  private String confirmPerm;

  /** 削除権限 */
  @Schema(description = "削除権限(表示のみ)")
  private String deletePerm;

  /** 編集権限 */
  @Schema(description = "編集権限(表示のみ)")
  private String editPerm;

  /** 参照権限 */
  @Schema(description = "参照権限(表示のみ)")
  private String referPerm;

  /** 注文書権限 */
  @Schema(description = "注文書権限(表示のみ)")
  private String poPerm;
}
