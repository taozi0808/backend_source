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
import java.math.BigDecimal;
import org.hibernate.annotations.SQLRestriction;

/** 実行予算公共明細. */
@lombok.Getter
@lombok.Setter
@Entity
@SQLRestriction("del_flg = '0'")
@Table(name = "t_exec_bgt_pub_dtl")
public class TExecBgtPubDtl extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "exec_bgt_pub_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "exec_bgt_hid", nullable = false)
  private TExecBgtHdr execBgtHid;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

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
  @Column(name = "qty", nullable = false, precision = 10)
  private BigDecimal qty;

  @Size(max = 2)
  @Column(name = "unit_k", length = 2)
  private String unitK;

  @NotNull
  @Column(name = "exec_bgt_amt", nullable = false, precision = 11)
  private BigDecimal execBgtAmt;

  @NotNull
  @Column(name = "assessed_amt", nullable = false, precision = 11)
  private BigDecimal assessedAmt;

  @NotNull
  @Column(name = "remarks", nullable = false, length = Integer.MAX_VALUE)
  private String remarks;

  @Size(max = 3)
  @NotNull
  @Column(name = "work_seq_no", nullable = false, length = 3)
  private String workSeqNo;
}
