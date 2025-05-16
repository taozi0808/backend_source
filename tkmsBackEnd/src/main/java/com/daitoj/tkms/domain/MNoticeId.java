package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Embeddable
public class MNoticeId implements java.io.Serializable {
  private static final long serialVersionUID = -5352132602523784748L;

  @Size(max = 255)
  @NotNull
  @Column(name = "pg_id", nullable = false)
  private String pgId;

  @Size(max = 8)
  @NotNull
  @Column(name = "effective_start_dt", nullable = false, length = 8)
  private String effectiveStartDt;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MNoticeId entity = (MNoticeId) o;
    return Objects.equals(this.pgId, entity.pgId)
        && Objects.equals(this.effectiveStartDt, entity.effectiveStartDt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pgId, effectiveStartDt);
  }
}
