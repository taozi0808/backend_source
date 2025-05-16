package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Where;

/** メニュー項目情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_menu_item")
@Where(clause = "del_flg = '0'")
public class MMenuItem extends BaseEntity {

  @Id
  @Column(name = "menu_item_id", nullable = false)
  private Integer id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "menu_id", nullable = false)
  private MMenu menu;

  @Column(name = "parent_menu_item_id")
  private Integer parentMenuItemId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pg_id")
  private MProgram pg;

  @Size(max = 255)
  @NotNull
  @Column(name = "menu_item_nm", nullable = false)
  private String menuItemNm;

  @Size(max = 255)
  @Column(name = "menu_item_path")
  private String menuItemPath;

  @Size(max = 255)
  @Column(name = "menu_item_icon")
  private String menuItemIcon;

  @NotNull
  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;

  @Column(name = "menu_item_tooltip", length = Integer.MAX_VALUE)
  private String menuItemTooltip;
}
