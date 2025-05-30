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
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 精積算情報 */
@Getter
@Setter
@Entity
@Table(name = "t_detailed_est_hdr")
@Where(clause = "del_flg = '0' AND newest_flg ='1'")
public class TDetailedEstHdr extends BaseEntity {
  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_detailed_est_hdr_detailed_est_hid_seq_generator")
  @SequenceGenerator(
      name = "t_detailed_est_hdr_detailed_est_hid_seq_generator",
      sequenceName = "t_detailed_est_hdr_detailed_est_hid_seq",
      allocationSize = 1)
  @Column(name = "detailed_est_hid", nullable = false)
  private Long id;

  @Size(max = 12)
  @NotNull
  @Column(name = "detailed_est_cd", nullable = false, length = 12)
  private String detailedEstCd;

  @NotNull
  @Column(name = "his_no", nullable = false)
  private Integer hisNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @Size(max = 9)
  @NotNull
  @Column(name = "project_cd", nullable = false, length = 9)
  private String projectCd;

  @Size(max = 9)
  @NotNull
  @Column(name = "customer_branch_cd", nullable = false, length = 9)
  private String customerBranchCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "building_cd", nullable = false, length = 2)
  private String buildingCd;

  @Column(name = "detailed_est_total_amt", nullable = false, precision = 11)
  private BigDecimal detailedEstTotalAmt;

  @Size(max = 8)
  @Column(name = "detailed_est_ymd", nullable = false, length = 8)
  private String detailedEstYmd;

  @Column(name = "detailed_est_org_id")
  private Long detailedEstOrgId;

  @Size(max = 6)
  @Column(name = "detailed_est_pic_cd", nullable = false, length = 6)
  private String detailedEstPicCd;

  @Size(max = 3)
  @Column(name = "region_cd", nullable = false, length = 3)
  private String regionCd;

  @Column(name = "constr_site_k", nullable = false, length = Integer.MAX_VALUE)
  private String constrSiteK;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "price_region_id")
  private MPriceRegion priceRegion;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "detailed_est_create_flg", nullable = false, length = Integer.MAX_VALUE)
  private String detailedEstCreateFlg = "0";
}
