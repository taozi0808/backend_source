package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import org.hibernate.annotations.Where;

/** 実行予算明細 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_exec_bgt_dtl")
@Where(clause = "del_flg = '0' ")
public class TExecBgtDtl extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_exec_bgt_dtl_exec_bgt_did_seq_generator")
  @SequenceGenerator(
      name = "t_exec_bgt_dtl_exec_bgt_did_seq_generator",
      sequenceName = "t_exec_bgt_dtl_exec_bgt_did_seq",
      allocationSize = 1)
  @Column(name = "exec_bgt_did", nullable = false)
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
  @Column(name = "target_bgt_amt", nullable = false, precision = 11)
  private BigDecimal targetBgtAmt;

  @NotNull
  @Column(name = "remarks", nullable = false, length = Integer.MAX_VALUE)
  private String remarks;

  @Size(max = 3)
  @NotNull
  @Column(name = "work_seq_no", nullable = false, length = 3)
  private String workSeqNo;

  @NotNull
  @Column(name = "price", nullable = false, precision = 10)
  private BigDecimal price;

  @NotNull
  @Column(name = "chg_total_amt", nullable = false, precision = 11)
  private BigDecimal chgTotalAmt;
}
