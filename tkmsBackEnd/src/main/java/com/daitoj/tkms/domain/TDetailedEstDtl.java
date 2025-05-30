package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

/** 精積算明細 */
@Getter
@Setter
@Entity
@Table(name = "t_detailed_est_dtl")
@Where(clause = "del_flg = '0'")
public class TDetailedEstDtl extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "detailed_est_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "detailed_est_hid", nullable = false)
  private TDetailedEstHdr detailedEstHid;

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

  @Size(max = 2)
  @Column(name = "unit_k", length = 2)
  private String unitK;

  @NotNull
  @Column(name = "detailed_est_amt", nullable = false, precision = 11)
  private BigDecimal detailedEstAmt;

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
