package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** マスタデータ情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.MitemSettingDto", description = "マスタデータ情報")
public class MitemSettingDto {

  /** 項目コード */
  @Schema(name = "itemCd", description = "項目コード ")
  private String itemCd;

  /** 項目内容 */
  @Schema(name = "itemValue", description = "項目内容")
  private String itemValue;
}
