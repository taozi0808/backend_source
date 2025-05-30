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
import java.time.OffsetDateTime;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 業者産廃許可情報. */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_vendor_iw_perm")
@Where(clause = "del_flg = '0'")
public class MVendorIwPerm {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "vendor_vendor_iw_perm_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "vendor_id", nullable = false)
  private MVendor vendor;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @NotNull
  @Column(name = "iw_perm_pref", nullable = false, length = Integer.MAX_VALUE)
  private String iwPermPref;

  @NotNull
  @Column(name = "iw_perm_no", nullable = false, length = Integer.MAX_VALUE)
  private String iwPermNo;

  @Size(max = 8)
  @NotNull
  @Column(name = "iw_perm_dt", nullable = false, length = 8)
  private String iwPermDt;

  @NotNull
  @Column(name = "iw_perm_file_id", nullable = false)
  private Long iwPermFileId;

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
