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
public class MPriceRegionPriceId implements java.io.Serializable {
  private static final long serialVersionUID = 7499126085023137898L;

  @NotNull
  @Column(name = "price_region_id", nullable = false)
  private Long priceRegionId;

  @NotNull
  @Column(name = "price_id", nullable = false)
  private Long priceId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MPriceRegionPriceId entity = (MPriceRegionPriceId) o;
    return Objects.equals(this.priceId, entity.priceId)
        && Objects.equals(this.priceRegionId, entity.priceRegionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(priceId, priceRegionId);
  }
}
