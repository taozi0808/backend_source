package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.time.Instant;

/** ベースDto */
@lombok.Getter
@lombok.Setter
@Schema(name = "BaseDto", description = "ベースDto")
public class BaseDto {

  /** 削除フラグ */
  @Size(max = 1)
  @Schema(description = "削除フラグ", maxLength = 1)
  protected String delFlg = "0";

  /** 登録PG */
  @Schema(description = "登録日時")
  protected Instant regTs;

  @Size(max = 255)
  /** 登録者ID */
  @Schema(description = "登録者ID", maxLength = 255)
  protected String regUserId;

  /** 登録PG */
  @Size(max = 50)
  @Schema(description = "登録PG", maxLength = 50)
  protected String regPgId;

  /** 更新日時 */
  @Schema(description = "更新日時")
  protected Instant updTs;

  @Size(max = 255)
  /** 更新者ID */
  @Schema(description = "更新者ID", maxLength = 255)
  protected String updUserId;

  /** 更新PG */
  @Size(max = 50)
  @Schema(description = "更新PG", maxLength = 50)
  protected String updPgId;
}
