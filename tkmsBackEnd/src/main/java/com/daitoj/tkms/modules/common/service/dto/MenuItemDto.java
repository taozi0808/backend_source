package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** メニュー情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.MenuItemDto", description = "メニュー情報")
public class MenuItemDto {
  /** メニュー項目ID */
  @Schema(description = "メニュー項目ID")
  private Integer id;

  /** 上位メニュー項目ID */
  @Schema(description = "上位メニュー項目ID")
  private Integer parentMenuItemId;

  /** 表示名 */
  @Schema(description = "表示名")
  private String menuItemNm;

  /** 表示PATH */
  @Schema(description = "表示PATH")
  private String menuItemPath;

  /** 表示ICON */
  @Schema(description = "表示ICON")
  private String menuItemIcon;

  /** 表示順 */
  @Schema(description = "表示順")
  private Integer displayOrder;

  /** アカウント区分 */
  @Schema(description = "説明チップ")
  private String menuItemTooltip;

  /** 子メニュー */
  @Schema(description = "子メニュー")
  private List<MenuItemDto> children = new ArrayList<>();
}
