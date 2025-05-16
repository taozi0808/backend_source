package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import org.hibernate.annotations.Where;

/** 見積情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_est")
@Where(clause = "del_flg = '0' AND newest_flg ='1'")
public class TEst extends BaseEntity {

  @Id
  @Column(name = "est_id", nullable = false)
  private Long id;

  @Size(max = 13)
  @NotNull
  @Column(name = "est_no", nullable = false, length = 13)
  private String estNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rough_est_hid")
  private TRoughEstHdr roughEstHid;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "detailed_est_hid")
  private TDetailedEstHdr detailedEstHid;

  @Size(max = 2)
  @NotNull
  @Column(name = "his_no", nullable = false, length = 2)
  private String hisNo;

  @Size(max = 8)
  @NotNull
  @Column(name = "est_ymd", nullable = false, length = 8)
  private String estYmd;

  @NotNull
  @Column(name = "est_nm", nullable = false, length = Integer.MAX_VALUE)
  private String estNm;

  @NotNull
  @Column(name = "est_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String estKnNm;

  @NotNull
  @Column(name = "kouji_keihi_rate", nullable = false, length = Integer.MAX_VALUE)
  private String koujiKeihiRate;

  @NotNull
  @Column(name = "kouji_keihi_amt", nullable = false, precision = 11)
  private BigDecimal koujiKeihiAmt;

  @NotNull
  @Column(name = "sale_mgr_rate", nullable = false, length = Integer.MAX_VALUE)
  private String saleMgrRate;

  @NotNull
  @Column(name = "sale_mgr_amt", nullable = false, precision = 11)
  private BigDecimal saleMgrAmt;

  @NotNull
  @Column(name = "tyousei_amt", nullable = false, precision = 11)
  private BigDecimal tyouseiAmt;

  @NotNull
  @Column(name = "excl_tax_est_amt", nullable = false, precision = 11)
  private BigDecimal exclTaxEstAmt;

  @NotNull
  @Column(name = "incl_tax_est_amt", nullable = false, precision = 11)
  private BigDecimal inclTaxEstAmt;

  @NotNull
  @Column(name = "tax_rate_id", nullable = false)
  private Integer taxRateId;

  @NotNull
  @Column(name = "sales_tax_amt", nullable = false, precision = 11)
  private BigDecimal salesTaxAmt;

  @NotNull
  @Column(name = "ararieki_amt", nullable = false, precision = 11)
  private BigDecimal arariekiAmt;
}
