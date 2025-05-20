package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 資格免許情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "CertSearchDto", description = "資格免許情報")
public class CertSearchDto {
  /** 資格免許コード */
  @Schema(description = "資格免許コード")
  private String certCd;

  /** 資格免許名 */
  @Schema(description = "資格免許名(表示のみ)")
  private String certNm;
}
