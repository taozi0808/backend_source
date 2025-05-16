package com.daitoj.tkms.modules.common.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** マスタデータ結果情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.MItemListSettingDto", description = "マスタデータ結果情報")
public class MItemListSettingDto {

  /** 項目分類コード */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(name = "itemClassCd", description = "項目分類コード ")
  private String itemClassCd;

  /** マスタデータリスト */
  @Schema(name = "itemSettingList", description = "マスタデータリスト")
  private List<MitemSettingDto> itemSettingList;
}
