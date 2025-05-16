package com.daitoj.tkms.modules.apir0045.service.dto;

import com.daitoj.tkms.domain.MCert;
import com.daitoj.tkms.modules.common.service.dto.MItemListSettingDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 社員管理選択項目取得結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0045S01ReturnData", description = "社員管理選択項目取得結果")
public class R0045S01ReturnData {

  /** マスタデータリスト情報 */
  @Schema(description = "マスタデータリスト情報")
  List<MItemListSettingDto> settingList;

  /** 資格リスト情報 */
  @Schema(description = "資格リスト情報")
  List<MCert> certList;
}
