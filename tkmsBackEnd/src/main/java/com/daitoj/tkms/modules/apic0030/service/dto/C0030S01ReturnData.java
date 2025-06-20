package com.daitoj.tkms.modules.apic0030.service.dto;

import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.modules.common.service.dto.MitemSettingDto;
import com.daitoj.tkms.modules.common.service.dto.OrgRevSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 概算情報結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0030S01ReturnData", description = "概算情報")
public class C0030S01ReturnData {

  /** 概算情報 */
  @Schema(
      name = "roughInfoDto",
      description = "概算情報",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("roughInfoDto")
  private RoughInfoDto roughInfoDto;

  /** 請求条件リスト */
  @Schema(
      name = "listProjectPaymentTermsInfo",
      description = "請求条件リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listProjectPaymentTermsInfo")
  private List<ProjectPaymentTermsInfoDto> listProjectPaymentTermsInfo = new ArrayList<>();

  /** 概算明細リスト */
  @Schema(
      name = "listRoughEstDtlInfoDto",
      description = "概算明細リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listRoughEstDtlInfoDto")
  private List<RoughEstDtlInfoDto> listRoughEstDtlInfoDto = new ArrayList<>();

  /** 添付ファイルリスト */
  @Schema(
      name = "listRoughEstFileDtlInfoDto",
      description = "添付ファイルリスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listRoughEstFileDtlInfoDto")
  private List<RoughEstFileDtlInfoDto> listRoughEstFileDtlInfoDto = new ArrayList<>();

  /** 現場区分リスト */
  @Schema(
      name = "listConstrSiteInfoDto",
      description = "現場区分リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listConstrSiteInfoDto")
  private List<MitemSettingDto> listConstrSiteInfoDto = new ArrayList<>();

  /** 部門リスト */
  @Schema(
      name = "orgRevSearchInfoDto",
      description = "部門リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("orgRevSearchInfoDto")
  List<OrgRevSearchInfoDto> orgRevSearchInfoDto;

  /** 担当者リスト */
  @Schema(name = "empInfo", description = "担当者リスト", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("empInfo")
  List<MEmpInfoDto> empInfo = new ArrayList<>();

  /** 単価地域リスト */
  @Schema(
      name = "regionInfoDto",
      description = "単価地域リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("regionInfoDto")
  List<RegionInfoDto> regionInfoDto = new ArrayList<>();
}
