package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Where;

/** 項目リスト設定 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_item_list_setting")
@Where(clause = "del_flg = '0'")
public class MItemListSetting extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId private MItemListSettingId id;

  @NotNull
  @Column(name = "item_value", nullable = false, length = Integer.MAX_VALUE)
  private String itemValue;

  @Column(name = "display_order")
  private Integer displayOrder;

  @Column(name = "item_desc", length = Integer.MAX_VALUE)
  private String itemDesc;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    MItemListSetting that = (MItemListSetting) o;
    return Objects.equals(id, that.id)
        && Objects.equals(itemValue, that.itemValue)
        && Objects.equals(displayOrder, that.displayOrder)
        && Objects.equals(itemDesc, that.itemDesc)
        && Objects.equals(delFlg, that.delFlg)
        && Objects.equals(regTs, that.regTs)
        && Objects.equals(regUserId, that.regUserId)
        && Objects.equals(regPgId, that.regPgId)
        && Objects.equals(updTs, that.updTs)
        && Objects.equals(updUserId, that.updUserId)
        && Objects.equals(updPgId, that.updPgId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        itemValue,
        displayOrder,
        itemDesc,
        delFlg,
        regTs,
        regUserId,
        regPgId,
        updTs,
        updUserId,
        updPgId);
  }
}
