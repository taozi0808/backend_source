package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Where;

/** 組織・メニュー項目・対照表 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_org_menu_item")
@Where(clause = "del_flg = '0'")
public class MOrgMenuItem extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId private MOrgMenuItemId id;

  @MapsId
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "org_id", nullable = false)
  private MOrg mOrg;

  @MapsId
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "menu_item_id", nullable = false)
  private MMenuItem menuItem;

  @NotNull
  @Column(name = "refer_perm", nullable = false, length = Integer.MAX_VALUE)
  private String referPerm;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    MOrgMenuItem that = (MOrgMenuItem) o;
    return Objects.equals(id, that.id)
        && Objects.equals(mOrg, that.mOrg)
        && Objects.equals(menuItem, that.menuItem)
        && Objects.equals(referPerm, that.referPerm)
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
        id, mOrg, menuItem, referPerm, delFlg, regTs, regUserId, regPgId, updTs, updUserId,
        updPgId);
  }
}
