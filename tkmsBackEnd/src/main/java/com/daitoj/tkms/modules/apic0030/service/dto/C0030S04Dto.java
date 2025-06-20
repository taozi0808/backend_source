package com.daitoj.tkms.modules.apic0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** 概算出来高Sデータ作成保存パラメータパラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0030S04Dto", description = "概算出来高Sデータ作成保存パラメータパラメータ")
public class C0030S04Dto extends BaseDto {

  /** 概算コード */
  @NotNull
  @Size(max = 12)
  @Schema(description = "概算コード", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 12)
  private String roughEstCd;

  /** 概算明細リスト */
  @NotNull
  @Schema(
      name = "listC0030S02DtoInfoDto",
      description = "概算明細リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listC0030S02DtoInfoDto")
  private List<C0030S02Dto> listC0030S02DtoInfoDto = new ArrayList<>();
}
