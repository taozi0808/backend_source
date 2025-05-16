package com.daitoj.tkms.modules.apir0045.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** 資格情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "EmpCertDto", description = "資格情報")
public class EmpCertDto extends BaseDto {

  /** ID(登録の場合、nullを渡す) */
  @Schema(description = "ID(登録の場合、nullを渡す)")
  private Long id;

  /** 連番 */
  @NotNull
  @Schema(description = "連番", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer seqNo;

  /** 資格免許コード */
  @Valid
  @NotNull
  @Schema(description = "資格免許コード", requiredMode = Schema.RequiredMode.REQUIRED)
  private CertDto certCd;

  /** 資格有効期限 */
  @Schema(description = "資格有効期限")
  private String certExpirationDt;
}
