package com.daitoj.tkms.modules.apir0045.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** 従業員・組織・対照情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "EmpOrgDto", description = "従業員・組織・対照情報")
public class EmpOrgDto extends BaseDto {

  /** ID(登録の場合、nullを渡す) */
  @Schema(description = "ID(登録の場合、nullを渡す)")
  private Long id;

  /** 連番 */
  @NotNull
  @Schema(description = "連番", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer seqNo;

  /** 組織情報 */
  @Valid
  @NotNull
  @Schema(description = "組織情報", requiredMode = Schema.RequiredMode.REQUIRED)
  private OrgDto org;

  /** 組織区分 */
  @NotNull
  @Schema(description = "組織区分", requiredMode = Schema.RequiredMode.REQUIRED)
  private String orgK;
}
