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
import java.time.Instant;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 現場経費 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_constr_site_exp_dtl")
@Where(clause = "del_flg = '0'")
public class TConstrSiteExpDtl extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "constr_site_exp_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "constr_site_exp_hid", nullable = false)
  private TConstrSiteExpHdr constrSiteExpHid;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @Size(max = 8)
  @NotNull
  @Column(name = "exp_payment_d", nullable = false, length = 8)
  private String expPaymentD;

  @NotNull
  @Column(name = "payee_k", nullable = false, length = Integer.MAX_VALUE)
  private String payeeK;

  @Size(max = 9)
  @Column(name = "payee_partner_vendor_cd", length = 9)
  private String payeePartnerVendorCd;

  @Size(max = 6)
  @Column(name = "payee_emp_cd", length = 6)
  private String payeeEmpCd;

  @Size(max = 3)
  @NotNull
  @Column(name = "major_work_cd", nullable = false, length = 3)
  private String majorWorkCd;

  @Size(max = 4)
  @NotNull
  @Column(name = "minor_work_cd", nullable = false, length = 4)
  private String minorWorkCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "exp_item_cd", nullable = false, length = 2)
  private String expItemCd;

  @NotNull
  @Column(name = "incl_tax_payment_amt", nullable = false, precision = 11)
  private BigDecimal inclTaxPaymentAmt;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "payment_data_create_flg", nullable = false, length = Integer.MAX_VALUE)
  private String paymentDataCreateFlg;

  @Column(name = "remarks", length = Integer.MAX_VALUE)
  private String remarks;

  @Size(max = 3)
  @NotNull
  @Column(name = "work_seq_no", nullable = false, length = 3)
  private String workSeqNo;
}
