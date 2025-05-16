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
public class MMinorWorkId implements java.io.Serializable {
  private static final long serialVersionUID = 2345914110858553510L;

  @Size(max = 3)
  @NotNull
  @Column(name = "major_work_cd", nullable = false, length = 3)
  private String majorWorkCd;

  @Size(max = 4)
  @NotNull
  @Column(name = "minor_work_cd", nullable = false, length = 4)
  private String minorWorkCd;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MMinorWorkId entity = (MMinorWorkId) o;
    return Objects.equals(this.minorWorkCd, entity.minorWorkCd)
        && Objects.equals(this.majorWorkCd, entity.majorWorkCd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minorWorkCd, majorWorkCd);
  }
}
