package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 概算情報 */
@Entity
@lombok.Getter
@lombok.Setter
@Table(name = "t_rough_est_dtl")
@Where(clause = "del_flg = '0'")
public class TRoughEstDtl extends BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "rough_est_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "rough_est_hid", nullable = false)
  private TRoughEstHdr roughEstHid;

  @NotNull
  @Column(name = "price_id", nullable = false)
  private Long priceId;

  @Size(max = 3)
  @NotNull
  @Column(name = "major_work_cd", nullable = false, length = 3)
  private String majorWorkCd;

  @Size(max = 4)
  @NotNull
  @Column(name = "minor_work_cd", nullable = false, length = 4)
  private String minorWorkCd;

  @NotNull
  @Column(name = "spec", nullable = false, length = Integer.MAX_VALUE)
  private String spec;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @NotNull
  @Column(name = "qty", nullable = false, precision = 10)
  private BigDecimal qty;

  @NotNull
  @Column(name = "price", nullable = false, precision = 10)
  private BigDecimal price;

  @Column(name = "unit", length = Integer.MAX_VALUE)
  private String unit;

  @NotNull
  @Column(name = "rough_est_amt", nullable = false, precision = 11)
  private BigDecimal roughEstAmt;

  @NotNull
  @Column(name = "remarks", nullable = false, length = Integer.MAX_VALUE)
  private String remarks;
}
