package com.daitoj.tkms.modules.apii0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.MitemSettingDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 出来高シュミレーション一覧検索結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "I0010ReturnData", description = "来高シュミレーション一覧検索結果")
public class I0010ReturnData {

  /** 出来高シュミレーションリスト */
  @Schema(
      name = "listDekidakaShimyurēsyonInfo",
      description = "出来高シュミレーションリスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<DekidakaShimyurēsyonInfoDto> listDekidakaShimyurēsyonInfo = new ArrayList<>();

  /** マスタデータ(受注状態) */
  @Schema(name = "mitemSettingD0005", description = "スタデータ(受注状態)")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<MitemSettingDto> mitemSettingD0005;
}
