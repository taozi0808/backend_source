package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

/** 単価地域・単価・対照表 */
@Getter
@Setter
@Entity
@Table(name = "m_price_region_price")
@Where(clause = "del_flg = '0'")
public class MPriceRegionPrice extends BaseEntity {

  @EmbeddedId private MPriceRegionPriceId id;

  @MapsId
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "price_region_id", nullable = false)
  private MPriceRegion priceRegion;

  @MapsId
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "price_id", nullable = false)
  private MPrice price;
}
