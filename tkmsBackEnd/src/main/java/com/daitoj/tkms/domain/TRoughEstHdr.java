package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 概算ヘッダ */
@Entity
@lombok.Getter
@lombok.Setter
@Table(name = "t_rough_est_hdr")
@Where(clause = "del_flg = '0' AND newest_flg ='1'")
public class TRoughEstHdr extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_rough_est_hdr_rough_est_hid_seq_generator")
  @SequenceGenerator(
      name = "t_rough_est_hdr_rough_est_hid_seq_generator",
      sequenceName = "t_rough_est_hdr_rough_est_hid_seq",
      allocationSize = 1)
  @Column(name = "rough_est_hid", nullable = false)
  private Long id;

  @Size(max = 12)
  @NotNull
  @Column(name = "rough_est_cd", nullable = false, length = 12)
  private String roughEstCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "his_no", nullable = false, length = 2)
  private String hisNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @Size(max = 8)
  @NotNull
  @Column(name = "rough_est_ymd", nullable = false, length = 8)
  private String roughEstYmd;

  @Size(max = 9)
  @NotNull
  @Column(name = "project_cd", nullable = false, length = 9)
  private String projectCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "building_cd", nullable = false, length = 2)
  private String buildingCd;

  @NotNull
  @Column(name = "rough_est_total_amt", nullable = false, precision = 11)
  private BigDecimal roughEstTotalAmt;

  @NotNull
  @Column(name = "rough_est_org_id", nullable = false)
  private Long roughEstOrgId;

  @Size(max = 6)
  @NotNull
  @Column(name = "rough_est_pic_cd", nullable = false, length = 6)
  private String roughEstPicCd;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "tantou_office_cd", nullable = false)
  private MOffice tantouOfficeCd;

  @Size(max = 3)
  @NotNull
  @Column(name = "region_cd", nullable = false, length = 3)
  private String regionCd;

  @NotNull
  @Column(name = "constr_site_k", nullable = false, length = Integer.MAX_VALUE)
  private String constrSiteK;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "price_region_id")
  private MPriceRegion priceRegion;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "rough_est_create_flg", nullable = false, length = Integer.MAX_VALUE)
  private String roughEstCreateFlg;
}
