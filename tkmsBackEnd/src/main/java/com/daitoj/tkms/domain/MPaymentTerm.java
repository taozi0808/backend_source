package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/** 請求条件 */
@Getter
@Setter
@Entity
@Table(name = "m_payment_terms")
public class MPaymentTerm extends BaseEntity {

  @Id
  @Size(max = 2)
  @Column(name = "payment_terms_cd", nullable = false, length = 2)
  private String paymentTermsCd;

  @NotNull
  @Column(name = "payment_terms_nm", nullable = false, length = Integer.MAX_VALUE)
  private String paymentTermsNm;

  @NotNull
  @Column(name = "payment_trigger_k", nullable = false, length = Integer.MAX_VALUE)
  private String paymentTriggerK;

  @NotNull
  @Column(name = "payment_amt_cal_k", nullable = false, length = Integer.MAX_VALUE)
  private String paymentAmtCalK;
}
