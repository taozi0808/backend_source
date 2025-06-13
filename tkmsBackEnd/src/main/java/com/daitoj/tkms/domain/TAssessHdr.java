package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 査定ヘッダ情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_assess_hdr")
@Where(clause = "del_flg = '0' AND newest_flg ='1'")
public class TAssessHdr {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "assess_hid", nullable = false)
  private Long id;

  @Size(max = 12)
  @NotNull
  @Column(name = "constr_site_cd", nullable = false, length = 12)
  private String constrSiteCd;

  @Size(max = 6)
  @NotNull
  @Column(name = "assess_ym", nullable = false, length = 6)
  private String assessYm;

  @NotNull
  @Column(name = "mtd_assess_total_amt", nullable = false, precision = 11)
  private BigDecimal mtdAssessTotalAmt;

  @NotNull
  @Column(name = "assess_total_amt", nullable = false, precision = 11)
  private BigDecimal assessTotalAmt;

  @NotNull
  @Column(name = "mtd_payment_total_amt", nullable = false, precision = 11)
  private BigDecimal mtdPaymentTotalAmt;

  @NotNull
  @Column(name = "ayment_total_amt", nullable = false, precision = 11)
  private BigDecimal aymentTotalAmt;

  @NotNull
  @Column(name = "mtd_de_total_amt", nullable = false, precision = 11)
  private BigDecimal mtdDeTotalAmt;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "del_flg", nullable = false, length = Integer.MAX_VALUE)
  private String delFlg;

  @NotNull
  @Column(name = "reg_ts", nullable = false)
  private OffsetDateTime regTs;

  @Size(max = 255)
  @NotNull
  @Column(name = "reg_user_id", nullable = false)
  private String regUserId;

  @Size(max = 50)
  @Column(name = "reg_pg_id", length = 50)
  private String regPgId;

  @Column(name = "upd_ts")
  private OffsetDateTime updTs;

  @Size(max = 255)
  @Column(name = "upd_user_id")
  private String updUserId;

  @Size(max = 50)
  @Column(name = "upd_pg_id", length = 50)
  private String updPgId;

}
