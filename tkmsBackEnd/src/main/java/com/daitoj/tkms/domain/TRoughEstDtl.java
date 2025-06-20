package com.daitoj.tkms.domain;

import jakarta.persistence.*;
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
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_rough_est_dtl_rough_est_did_seq_generator")
  @SequenceGenerator(
      name = "t_rough_est_dtl_rough_est_did_seq_generator",
      sequenceName = "t_rough_est_dtl_rough_est_did_seq",
      allocationSize = 1)
  @Column(name = "rough_est_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "rough_est_hid", nullable = false)
  private TRoughEstHdr roughEstHid;

  @Size(max = 3)
  @NotNull
  @Column(name = "major_work_cd", nullable = false, length = 3)
  private String majorWorkCd;

  @Size(max = 4)
  @NotNull
  @Column(name = "minor_work_cd", nullable = false, length = 4)
  private String minorWorkCd;

  @Column(name = "spec", length = Integer.MAX_VALUE)
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

  @Size(max = 2)
  @Column(name = "unit_k", length = 2)
  private String unitK;

  @NotNull
  @Column(name = "rough_est_amt", nullable = false, precision = 11)
  private BigDecimal roughEstAmt;

  @NotNull
  @Column(name = "remarks", nullable = false, length = Integer.MAX_VALUE)
  private String remarks;

  @Size(max = 14)
  @Column(name = "vendor_est_no", length = 14)
  private String vendorEstNo;

  @Size(max = 9)
  @Column(name = "partner_vendor_cd", length = 9)
  private String partnerVendorCd;

  @Column(name = "vendor_est_did")
  private Long vendorEstDid;

  @Size(max = 3)
  @NotNull
  @Column(name = "work_seq_no", nullable = false, length = 3)
  private String workSeqNo;
}
