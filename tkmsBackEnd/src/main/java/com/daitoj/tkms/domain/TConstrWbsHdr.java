package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/** 工事予実ヘッダ. */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_constr_wbs_hdr")
@SQLRestriction("del_flg = '0' and newest_flg = '1'")
public class TConstrWbsHdr extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "constr_wbs_hid", nullable = false)
  private Long id;

  @Size(max = 12)
  @NotNull
  @Column(name = "constr_site_cd", nullable = false, length = 12)
  private String constrSiteCd;

  @Size(max = 6)
  @NotNull
  @Column(name = "create_pic_cd", nullable = false, length = 6)
  private String createPicCd;

  @NotNull
  @Column(name = "constr_site_id", nullable = false)
  private Long constrSiteId;

  @Size(max = 6)
  @Column(name = "under_constr_process_cd", length = 6)
  private String underConstrProcessCd;

  @NotNull
  @Column(name = "his_no", nullable = false)
  private Integer hisNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @NotNull
  @Column(name = "wbs_create_ts", nullable = false)
  private Instant wbsCreateTs;

  @OneToMany(mappedBy = "constrWbsHid")
  private Set<TConstrWbsDtl> tConstrWbsDtls = new LinkedHashSet<>();
}
