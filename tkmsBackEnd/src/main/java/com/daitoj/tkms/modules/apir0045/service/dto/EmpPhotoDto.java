package com.daitoj.tkms.modules.apir0045.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

/** 従業員顔写真情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "EmpPhotoDto", description = "従業員顔写真情報")
public class EmpPhotoDto extends BaseDto {

  /** ID(登録の場合、nullを渡す) */
  @Schema(description = "ID(登録の場合、nullを渡す)")
  private Long id;

  /** 連番 */
  @Schema(description = "連番")
  private Integer seqNo;

  /** 写真URL */
  @Schema(description = "写真URL")
  private String fileIndex;

  /** 写真ID */
  @Schema(description = "写真ID")
  private UUID photoFileId;
}
