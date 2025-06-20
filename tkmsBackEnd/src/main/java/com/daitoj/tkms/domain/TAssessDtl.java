package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_assess_dtl")
@Where(clause = "del_flg = '0'")
public class TAssessDtl extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "assess_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "assess_hid", nullable = false)
  private TAssessHdr assessHid;

  @Size(max = 9)
  @NotNull
  @Column(name = "partner_vendor_cd", nullable = false, length = 9)
  private String partnerVendorCd;

  @Size(max = 3)
  @NotNull
  @Column(name = "major_work_cd", nullable = false, length = 3)
  private String majorWorkCd;

  @Size(max = 4)
  @NotNull
  @Column(name = "minor_work_cd", nullable = false, length = 4)
  private String minorWorkCd;

  @Size(max = 10)
  @NotNull
  @Column(name = "po_no", nullable = false, length = 10)
  private String poNo;

  @NotNull
  @Column(name = "mtd_assess_amt", nullable = false, precision = 11)
  private BigDecimal mtdAssessAmt;

  @NotNull
  @Column(name = "mtd_payment_amt", nullable = false, precision = 11)
  private BigDecimal mtdPaymentAmt;

  @NotNull
  @Column(name = "mtd_de_amt", nullable = false, precision = 11)
  private BigDecimal mtdDeAmt;

  @Size(max = 8)
  @Column(name = "assess_payment_d", length = 8)
  private String assessPaymentD;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "payment_data_create_flg", nullable = false, length = Integer.MAX_VALUE)
  private String paymentDataCreateFlg;

  @Size(max = 3)
  @NotNull
  @Column(name = "work_seq_no", nullable = false, length = 3)
  private String workSeqNo;
}
