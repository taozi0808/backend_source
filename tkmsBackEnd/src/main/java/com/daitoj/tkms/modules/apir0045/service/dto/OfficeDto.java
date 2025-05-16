package com.daitoj.tkms.modules.apir0045.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** 事業所情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "OfficeDto", description = "事業所情報")
public class OfficeDto {
  /** 事業所コード */
  @NotNull
  @Size(max = 2)
  @Schema(description = "事業所コード", maxLength = 2, requiredMode = Schema.RequiredMode.REQUIRED)
  private String officeCd;

  /** 事業所名 */
  @Schema(description = "事業所名(表示のみ)")
  private String officeNm;
}
