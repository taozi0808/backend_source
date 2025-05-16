package com.daitoj.tkms.modules.apir0045.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;

/** 印刷資格情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "EmpCertPrintDto", description = "印刷資格情報")
public class EmpCertPrintDto extends BaseDto {
  /** 資格有効期限 */
  @Schema(description = "資格有効期限")
  private String certExpirationDt;

  /** 資格免許名 */
  @Schema(description = "資格免許名(表示のみ)")
  private String certNm;

  public EmpCertPrintDto(String certNm, String certExpirationDt) {
    this.certExpirationDt = certExpirationDt;
    this.certNm = certNm;
  }
}
