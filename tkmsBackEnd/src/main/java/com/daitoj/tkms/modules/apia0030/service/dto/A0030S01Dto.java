package com.daitoj.tkms.modules.apia0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** 個人設定パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "A0030S01Dto", description = "個人設定パラメータ")
public class A0030S01Dto {

  /** ログインID */
  @NotNull
  @Size(max = 256)
  @Schema(name = "loginId", description = "ログインID")
  private String loginId;

  /** パスワード */
  @NotNull
  @Size(min = 5)
  @Schema(name = "password", description = "パスワード")
  private String password;

  /** 新規パスワード */
  @NotNull
  @Size(min = 5)
  @Schema(name = "newPassword", description = "新規パスワード")
  private String newPassword;
}
