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

/** 案件請求条件情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_project_payment_terms")
@Where(clause = "del_flg = '0'")
public class TProjectPaymentTerms extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_project_payment_terms_project_payment_terms_id_seq_generator")
  @SequenceGenerator(
      name = "t_project_payment_terms_project_payment_terms_id_seq_generator",
      sequenceName = "t_project_payment_terms_project_payment_terms_id_seq",
      allocationSize = 1)
  @Column(name = "project_payment_terms_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "project_id", nullable = false)
  private TProject project;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @Size(max = 2)
  @NotNull
  @Column(name = "building_cd", nullable = false, length = 2)
  private String buildingCd;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "payment_terms_cd", nullable = false)
  private MPaymentTerm paymentTermsCd;

  @NotNull
  @Column(name = "payment_ratio", nullable = false, length = Integer.MAX_VALUE)
  private String paymentRatio;

  @NotNull
  @Column(name = "excl_tax_payment_amt", nullable = false, precision = 11)
  private BigDecimal exclTaxPaymentAmt;

  @NotNull
  @Column(name = "incl_tax_payment_amt", nullable = false, precision = 11)
  private BigDecimal inclTaxPaymentAmt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "tax_rate_id", nullable = false)
  private MTaxRate taxRate;

  @NotNull
  @Column(name = "sales_tax_amt", nullable = false, precision = 11)
  private BigDecimal salesTaxAmt;
}
