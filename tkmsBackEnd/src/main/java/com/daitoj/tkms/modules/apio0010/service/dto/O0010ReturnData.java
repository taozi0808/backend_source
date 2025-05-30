package com.daitoj.tkms.modules.apio0010.service.dto;

import com.daitoj.tkms.domain.MItemListSetting;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 業者一覧検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "O0010ReturnData", description = "業者一覧検索結果")
public class O0010ReturnData {

  /* 業者情報リスト */
  @Schema(
      name = "listVendorInfo",
      description = "業者情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<VendorResultDto> listVendorInfo = new ArrayList<>();

  /* 業種リスト */
  @Schema(
      name = "listJobTypeInfo",
      description = "業種リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private List<MItemListSetting> listJobTypeInfo = new ArrayList<>();

}
