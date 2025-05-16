package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import org.hibernate.Hibernate;

@lombok.Getter
@lombok.Setter
@Embeddable
public class MSystemConfigId implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @Size(max = 5)
  @NotNull
  @Column(name = "sys_cd", nullable = false, length = 5)
  private String sysCd;

  @Size(max = 50)
  @NotNull
  @Column(name = "config_key", nullable = false, length = 50)
  private String configKey;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MSystemConfigId entity = (MSystemConfigId) o;
    return Objects.equals(this.configKey, entity.configKey)
        && Objects.equals(this.sysCd, entity.sysCd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(configKey, sysCd);
  }
}
