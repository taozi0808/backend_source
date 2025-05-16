package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Embeddable
public class MNumberId implements Serializable {
  private static final long serialVersionUID = -5813354770552825868L;

  @Size(max = 255)
  @NotNull
  @Column(name = "field_id", nullable = false)
  private String fieldId;

  @Size(max = 255)
  @NotNull
  @Column(name = "pk_no", nullable = false)
  private String pkNo;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MNumberId entity = (MNumberId) o;
    return Objects.equals(this.pkNo, entity.pkNo) && Objects.equals(this.fieldId, entity.fieldId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pkNo, fieldId);
  }
}
