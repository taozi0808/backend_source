package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import jakarta.validation.constraints.Size;
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
  @Column(name = "price_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "major_work_cd", nullable = false)
  private MMajorWork majorWorkCd;

  @Size(max = 4)
  @NotNull
  @Column(name = "minor_work_cd", nullable = false, length = 4)
  private String minorWorkCd;

  @Column(name = "spec", length = Integer.MAX_VALUE)
  private String spec;

  @NotNull
  @Column(name = "price", nullable = false, precision = 10)
  private BigDecimal price;

  @Size(max = 2)
  @Column(name = "unit_k", length = 2)
  private String unitK;
}
