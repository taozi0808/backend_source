package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 顧客請求ヘッダ. */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_customer_invoice_hdr")
@Where(clause = "del_flg = '0' and newest_flg = '1'")
public class TCustomerInvoiceHdr extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "customer_invoice_hid", nullable = false)
  private Long id;

  @Size(max = 9)
  @NotNull
  @Column(name = "invoice_no", nullable = false, length = 9)
  private String invoiceNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @Size(max = 9)
  @NotNull
  @Column(name = "customer_branch_cd", nullable = false, length = 9)
  private String customerBranchCd;

  @Size(max = 9)
  @NotNull
  @Column(name = "project_cd", nullable = false, length = 9)
  private String projectCd;

  @Size(max = 7)
  @NotNull
  @Column(name = "project_site_cd", nullable = false, length = 7)
  private String projectSiteCd;

  @Size(max = 8)
  @NotNull
  @Column(name = "invoice_dt", nullable = false, length = 8)
  private String invoiceDt;

  @Column(name = "invoice_total_amt", precision = 11)
  private BigDecimal invoiceTotalAmt;

  @Size(max = 8)
  @NotNull
  @Column(name = "exp_payment_dt", nullable = false, length = 8)
  private String expPaymentDt;

  @Size(max = 7)
  @Column(name = "invoice_post_no", length = 7)
  private String invoicePostNo;

  @Column(name = "invoice_addr1", length = Integer.MAX_VALUE)
  private String invoiceAddr1;

  @Column(name = "invoice_addr2", length = Integer.MAX_VALUE)
  private String invoiceAddr2;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "invoice_addr_k", nullable = false, length = Integer.MAX_VALUE)
  private String invoiceAddrK;

  @NotNull
  @Column(name = "invoice_issue_flg", nullable = false, length = Integer.MAX_VALUE)
  private String invoiceIssueFlg;

  @NotNull
  @Column(name = "his_no", nullable = false)
  private Integer hisNo;

  @OneToMany(mappedBy = "customerInvoiceHid")
  private Set<TCustomerInvoiceDtl> tCustomerInvoiceDtls = new LinkedHashSet<>();

}
