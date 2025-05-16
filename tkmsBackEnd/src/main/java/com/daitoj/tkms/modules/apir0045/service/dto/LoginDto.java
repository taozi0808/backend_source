package com.daitoj.tkms.modules.apir0045.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** ログイン情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "LoginDto", description = "ログイン情報")
public class LoginDto {
  /** ログインID */
  @NotNull
  @Size(max = 255)
  @Schema(description = "ログインID", maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
  private String loginId;
}
