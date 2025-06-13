package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Embeddable
public class MVendorWorkId implements java.io.Serializable {
  private static final long serialVersionUID = -8958807563159602178L;

  @NotNull
  @Column(name = "vendor_id", nullable = false)
  private Long vendorId;

  @NotNull
  @Column(name = "worker_id", nullable = false)
  private Long workerId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MVendorWorkId entity = (MVendorWorkId) o;
    return Objects.equals(this.workerId, entity.workerId)
        && Objects.equals(this.vendorId, entity.vendorId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(workerId, vendorId);
  }
}
