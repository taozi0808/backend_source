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
import org.hibernate.annotations.Where;

/** 実行予算明細発注情報. */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_exec_bgt_dtl_po")
@Where(clause = "del_flg = '0'")
public class TExecBgtDtlPo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "exec_bgt_dtl_po_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "exec_bgt_did", nullable = false)
  private TExecBgtDtl execBgtDid;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @Size(max = 10)
  @Column(name = "po_no", length = 10)
  private String poNo;

  @NotNull
  @Column(name = "po_amt", nullable = false, precision = 11)
  private BigDecimal poAmt;

  @Size(max = 6)
  @Column(name = "partner_vendor_cd", length = 6)
  private String partnerVendorCd;
}
