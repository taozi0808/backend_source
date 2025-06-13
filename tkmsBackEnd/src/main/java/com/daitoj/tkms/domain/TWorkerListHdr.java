package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import java.time.OffsetDateTime;

/** 作業員名簿ヘッダ */
@Entity
@lombok.Getter
@lombok.Setter
@Table(name = "t_worker_list_hdr")
@Where(clause = "del_flg = '0' AND newest_flg ='1'")
public class TWorkerListHdr {


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "worker_list_hid", nullable = false)
  private Long id;

  @Size(max = 7)
  @NotNull
  @Column(name = "project_site_cd", nullable = false, length = 7)
  private String projectSiteCd;

  @Size(max = 9)
  @NotNull
  @Column(name = "pri_partner_vendor_cd", nullable = false, length = 9)
  private String priPartnerVendorCd;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

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

  @NotNull
  @Column(name = "his_no", nullable = false)
  private Integer hisNo;

}
