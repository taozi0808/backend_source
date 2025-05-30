package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Where;

/** 工事予実ヘッダ */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_constr_wbs_hdr")
@Where(clause = "del_flg = '0'")
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

  @Size(max = 8)
  @NotNull
  @Column(name = "wbs_create_dt", nullable = false, length = 8)
  private String wbsCreateDt;

  @NotNull
  @Column(name = "constr_site_id", nullable = false)
  private Long constrSiteId;
}
