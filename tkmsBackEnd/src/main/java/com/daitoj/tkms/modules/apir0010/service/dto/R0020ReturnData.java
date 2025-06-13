package com.daitoj.tkms.modules.apir0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.OrgRevSearchDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 権限管理情報結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0020ReturnData", description = "権限管理情報結果")
public class R0020ReturnData {

  /** 部署権限リスト */
  @Schema(description = "部署権限リスト")
  List<R0010S01Dto> r0010S01DtoInfo;

  /** 役職権限リスト */
  @Schema(description = "役職権限リスト")
  List<R0010S02Dto> r0010S02DtoInfo;
}
