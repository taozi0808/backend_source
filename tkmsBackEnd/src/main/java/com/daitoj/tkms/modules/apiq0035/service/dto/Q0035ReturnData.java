package com.daitoj.tkms.modules.apiq0035.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 作業員名簿物件情報一覧検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "Q0035ReturnData", description = "作業員名簿物件情報一覧検索結果")
public class Q0035ReturnData {

  /** 作業員名簿物件情報情報リスト. */
  @Schema(
      name = "listGenbaInfo",
      description = "作業員名簿物件情報リスト",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("listGenbaInfo")
  private List<GenbaInfoDto> genbaInfo = new ArrayList<>();

}
