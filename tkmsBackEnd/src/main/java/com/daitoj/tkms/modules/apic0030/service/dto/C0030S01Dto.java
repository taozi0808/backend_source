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

/** 概算作成登録情報保存パラメータパラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0030S01Dto", description = "概算作成登録情報保存パラメータパラメータ")
public class C0030S01Dto extends BaseDto {

  /** 概算コード */
  @Size(max = 12)
  @Schema(description = "概算コード", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 12)
  private String roughEstCd;

  /** 概算日付 */
  @NotNull
  @Schema(name = "roughEstYmd", description = "概算日付")
  private String roughEstYmd;

  /** 案件コード */
  @Schema(name = "projectCd", description = "案件コード")
  private String projectCd;

  @Schema(name = "roughEstTotalAmt", description = "概算合計金額")
  private BigDecimal roughEstTotalAmt;

  /** 現場区分コード */
  @NotNull
  @Schema(name = "constrSiteK", description = "現場区分コード")
  private String constrSiteK;

  @NotNull
  @Schema(name = "regionCd", description = "地域コード")
  private String regionCd;

  @Schema(name = "roughEstOrgId", description = "概算部門ID")
  private Long roughEstOrgId;

  /** 概算担当者コード */
  @NotNull
  @Size(max = 6)
  @Schema(description = "概算担当者コード", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 11)
  private String roughEstPicCd;

  @Schema(description = "単価地域ID(登録の場合、nullを渡す)")
  private Long priceRegionId;

  /** 概算明細リスト */
  @Schema(
      name = "listC0030S02DtoInfoDto",
      description = "概算明細リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listC0030S02DtoInfoDto")
  private List<C0030S02Dto> listC0030S02DtoInfoDto = new ArrayList<>();

  @Schema(
      name = "listC0030S03DtoInfoDto",
      description = "添付ファイル明細",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listC0030S03DtoInfoDto")
  private List<C0030S03Dto> listC0030S03DtoInfoDto = new ArrayList<>();
}
