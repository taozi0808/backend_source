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
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

/** 顧客請求明細. */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_customer_invoice_dtl")
@Where(clause = "del_flg = '0'")
public class TCustomerInvoiceDtl extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "customer_invoice_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "customer_invoice_hid", nullable = false)
  private TCustomerInvoiceHdr customerInvoiceHid;

  @Size(max = 12)
  @NotNull
  @Column(name = "constr_site_cd", nullable = false, length = 12)
  private String constrSiteCd;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "project_payment_terms_id", nullable = false)
  private TProjectPaymentTerms projectPaymentTerms;

  @NotNull
  @Column(name = "payment_amt", nullable = false, precision = 11)
  private BigDecimal paymentAmt;

  @Size(max = 2)
  @NotNull
  @Column(name = "building_cd", nullable = false, length = 2)
  private String buildingCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "payment_terms_cd", nullable = false, length = 2)
  private String paymentTermsCd;

}
