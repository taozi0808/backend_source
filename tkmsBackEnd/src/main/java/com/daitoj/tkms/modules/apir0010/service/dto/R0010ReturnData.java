package com.daitoj.tkms.modules.apir0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.OrgRevSearchDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 選択項目の選択肢結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0010ReturnData", description = "選択項目の選択肢結果")
public class R0010ReturnData {

  /** 適用開始日 */
  @Schema(description = "適用開始日")
  List<OrgRevSearchDto> startDtInfo;

  /** 部署選択肢リスト */
  @Schema(description = "部署選択肢リスト")
  List<MorGInfoDto> morGInfoDtoInfo;

  /** 部署権限リスト */
  @Schema(description = "部署権限リスト")
  List<R0010S01Dto> r0010S01DtoInfo;

  /** 役職権限リスト */
  @Schema(description = "役職権限リスト")
  List<R0010S02Dto> r0010S02DtoInfo;
}
