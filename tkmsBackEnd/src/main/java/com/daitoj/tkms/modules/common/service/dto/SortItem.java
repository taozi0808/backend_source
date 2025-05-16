package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** ソート情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.SortItem", description = "ソート情報")
public class SortItem {

  /** ソート項目 */
  @Schema(name = "field", description = "ソート項目")
  private String field;

  /** ソート順 */
  @Schema(name = "direction", description = "ソート順")
  private String direction;

  /**
   * コンストラクタ
   *
   * @param field ソート項目
   * @param direction ソート順
   */
  public SortItem(String field, String direction) {
    this.field = field;
    this.direction = direction;
  }
}
