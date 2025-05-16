package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import org.hibernate.Hibernate;

@lombok.Getter
@lombok.Setter
@Embeddable
public class MOrgMenuItemId implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "m_org_id", nullable = false)
  private Long mOrgId;

  @NotNull
  @Column(name = "menu_item_id", nullable = false)
  private Integer menuItemId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MOrgMenuItemId entity = (MOrgMenuItemId) o;
    return Objects.equals(this.mOrgId, entity.mOrgId)
        && Objects.equals(this.menuItemId, entity.menuItemId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mOrgId, menuItemId);
  }
}
