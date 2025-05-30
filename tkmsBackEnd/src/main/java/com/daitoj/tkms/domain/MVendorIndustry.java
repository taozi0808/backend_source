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
import java.time.Instant;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 業者業種情報. */
@Entity
@lombok.Setter
@lombok.Getter
@Table(name = "m_vendor_industry")
@Where(clause = "del_flg = '0'")
public class MVendorIndustry {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "vendor_industry_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "vendor_id", nullable = false)
  private MVendor vendor;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @Size(max = 2)
  @Column(name = "industry_cd1", length = 2)
  private String industryCd1;

  @Size(max = 2)
  @Column(name = "industry_cd2", length = 2)
  private String industryCd2;

  @Size(max = 2)
  @Column(name = "industry_cd3", length = 2)
  private String industryCd3;

  @Size(max = 2)
  @Column(name = "industry_cd4", length = 2)
  private String industryCd4;

  @Size(max = 2)
  @Column(name = "industry_cd5", length = 2)
  private String industryCd5;

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
