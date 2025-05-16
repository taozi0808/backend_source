package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/** ページパラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "BasePageRequest", description = "ページパラメータ")
public class BasePageRequest {
  /** ページ数(1から) 0：ページネーションなし */
  @Schema(
      name = "page",
      description = "ページ数(1から) 0：ページネーションなし",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private Integer page;

  /** ページ`サイズ` 0：ページネーションなし */
  @Schema(
      name = "size",
      description = "ページサイズ 0：ページネーションなし",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private Integer size;

  /** ソート情報 */
  @Schema(name = "sortBy", description = "ソート情報")
  private List<SortItem> sortBy;
}
