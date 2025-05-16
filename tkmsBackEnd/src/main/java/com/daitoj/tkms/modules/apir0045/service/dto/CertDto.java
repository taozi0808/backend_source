package com.daitoj.tkms.modules.apir0045.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** 資格免許情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "CertDto", description = "資格免許情報")
public class CertDto {
  /** 資格免許コード */
  @NotNull
  @Size(max = 5)
  @Schema(description = "資格免許コード", maxLength = 5, requiredMode = Schema.RequiredMode.REQUIRED)
  private String certCd;

  /** 資格免許名 */
  @Schema(description = "資格免許名(表示のみ)")
  private String certNm;

  /** 表示順 */
  @Schema(description = "表示順(表示のみ)")
  private Integer displayOrder;
}
