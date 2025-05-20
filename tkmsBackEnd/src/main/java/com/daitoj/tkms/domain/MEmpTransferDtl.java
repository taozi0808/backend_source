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
import org.hibernate.annotations.Where;

/** 従業員異動明細情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_emp_transfer_dtl")
@Where(clause = "del_flg = '0'")
public class MEmpTransferDtl extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "emp_transfer_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "emp_transfer_hid", nullable = false)
  private MEmpTransferHdr empTransferHid;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "org_id", nullable = false)
  private MOrg org;

  @NotNull
  @Column(name = "org_k", nullable = false, length = Integer.MAX_VALUE)
  private String orgK;
}
