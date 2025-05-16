package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

/** 単価情報 */
@Getter
@Setter
@Entity
@Table(name = "m_price")
@Where(clause = "del_flg = '0'")
public class MPrice extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "m_price_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "m_price_region_id", nullable = false)
  private MPriceRegion mPriceRegion;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "major_work_cd", nullable = false)
  private MMajorWork majorWorkCd;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns({
    @JoinColumn(
        name = "major_work_cd",
        referencedColumnName = "major_work_cd",
        insertable = false,
        updatable = false),
    @JoinColumn(
        name = "minor_work_cd",
        referencedColumnName = "minor_work_cd",
        insertable = false,
        updatable = false)
  })
  private MMinorWork minorWorkCd;

  @NotNull
  @Column(name = "spec", nullable = false, length = Integer.MAX_VALUE)
  private String spec;

  @NotNull
  @Column(name = "price", nullable = false, precision = 10)
  private BigDecimal price;

  @Column(name = "unit", length = Integer.MAX_VALUE)
  private String unit;
}
