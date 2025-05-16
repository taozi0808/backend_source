package com.daitoj.tkms.modules.apia0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.daitoj.tkms.modules.common.utils.Alphanumeric;

/** ログインパラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "A0010Dto", description = "ログインパラメータ")
public class A0010Dto {
  /** ユーザーID */
  @NotNull
  @Size(max = 256)
  @Alphanumeric(param  = "ユーザーID")
  @Schema(name = "userId", description = "ユーザーID")
  private String userId;

  /** パスワード */
  @NotNull
  @Size(min = 5)
  @Alphanumeric(param  = "パスワード")
  @Schema(name = "password", description = "パスワード")
  private String password;
}
