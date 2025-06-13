package com.daitoj.tkms.modules.apir0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.OrgRevSearchDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/** 権限情報保存パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0010S03Dto", description = "権限情報保存パラメータ")
public class R0010S03Dto {

  /** 部署権限リスト */
  @Schema(description = "部署権限リスト")
  List<R0010S01Dto> r0010S01DtoInfo;

  /** 役職権限リスト */
  @Schema(description = "役職権限リスト")
  List<R0010S02Dto> r0010S02DtoInfo;
}
