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

/** 現場経費情報ヘッダ */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_constr_site_exp_hdr")
@Where(clause = "del_flg = '0'")
public class TConstrSiteExpHdr {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "constr_site_exp_hid", nullable = false)
  private Long id;

  @Size(max = 9)
  @Column(name = "project_cd", length = 9)
  private String projectCd;

  @Size(max = 12)
  @Column(name = "constr_site_cd", length = 12)
  private String constrSiteCd;

  @Size(max = 6)
  @NotNull
  @Column(name = "payment_ym", nullable = false, length = 6)
  private String paymentYm;

  @Size(max = 8)
  @Column(name = "exp_req_dt", length = 8)
  private String expReqDt;

  @Size(max = 8)
  @Column(name = "exp_apr_dt", length = 8)
  private String expAprDt;

  @Column(name = "final_apr_emp_id")
  private Long finalAprEmpId;

  @Column(name = "total_amt", precision = 11)
  private BigDecimal totalAmt;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "del_flg", nullable = false, length = Integer.MAX_VALUE)
  private String delFlg;

  @NotNull
  @Column(name = "reg_ts", nullable = false)
  private Instant regTs;

  @Size(max = 255)
  @NotNull
  @Column(name = "reg_user_id", nullable = false)
  private String regUserId;

  @Size(max = 50)
  @Column(name = "reg_pg_id", length = 50)
  private String regPgId;

  @Column(name = "upd_ts")
  private Instant updTs;

  @Size(max = 255)
  @Column(name = "upd_user_id")
  private String updUserId;

  @Size(max = 50)
  @Column(name = "upd_pg_id", length = 50)
  private String updPgId;
}
