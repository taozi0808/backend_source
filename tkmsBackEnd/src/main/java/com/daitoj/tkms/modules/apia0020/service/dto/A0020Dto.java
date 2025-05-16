package com.daitoj.tkms.modules.apia0020.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** パスワード通知パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "A0020Dto", description = "パスワード通知パラメータ")
public class A0020Dto {
  /** ユーザーID */
  @NotNull
  @Size(max = 256)
  @Schema(name = "userId", description = "ユーザーID")
  private String userId;

}
