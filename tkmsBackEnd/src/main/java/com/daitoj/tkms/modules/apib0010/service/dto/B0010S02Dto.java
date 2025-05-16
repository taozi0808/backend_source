package com.daitoj.tkms.modules.apib0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/** 案件一覧検索パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0010S02Dto", description = "案件一覧検索パラメータ")
public class B0010S02Dto extends B0010S01Dto {
  /** 受注状態リスト */
  @Valid
  @NotNull
  @Size(min = 1)
  @Schema(
      name = "listJyucyuuJyoutai",
      description = "受注状態リスト",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private List<String> listJyucyuuJyoutai;
}
