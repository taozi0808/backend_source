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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import java.time.OffsetDateTime;

/** 再下請負通知書情報. */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_subcon_notif")
@Where(clause = "del_flg = '0' AND newest_flg = '1'")
public class TSubconNotif {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "subcon_notif_id", nullable = false)
  private Long id;

  @Size(max = 7)
  @NotNull
  @Column(name = "project_site_cd", nullable = false, length = 7)
  private String projectSiteCd;

  @Size(max = 9)
  @NotNull
  @Column(name = "parent_partner_vendor_cd", nullable = false, length = 9)
  private String parentPartnerVendorCd;

  @Size(max = 9)
  @NotNull
  @Column(name = "child_partner_vendor_cd", nullable = false, length = 9)
  private String childPartnerVendorCd;

  @NotNull
  @Column(name = "his_no", nullable = false)
  private Integer hisNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "parent_sub_vendor_con_id", nullable = false)
  private TSubVendorCon parentSubVendorCon;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "child_sub_vendor_con_id", nullable = false)
  private TSubVendorCon childSubVendorCon;

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
