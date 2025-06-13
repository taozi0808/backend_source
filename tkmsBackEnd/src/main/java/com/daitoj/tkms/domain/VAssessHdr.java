package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/** 査定情報ビュー */
@Getter
@Setter
@Entity
@Table(name = "v_assess_hdr")
public class VAssessHdr {

  @Id
  @Size(max = 12)
  @Column(name = "constr_site_cd", length = 12)
  private String constrSiteCd;

  @Id
  @Size(max = 6)
  @Column(name = "assess_ym", length = 6)
  private String assessYm;

  @Column(name = "bef_assess_total_amt")
  private BigDecimal befAssessTotalAmt;

  @Column(name = "bef_payment_total_amt")
  private BigDecimal befPaymentTotalAmt;

}
