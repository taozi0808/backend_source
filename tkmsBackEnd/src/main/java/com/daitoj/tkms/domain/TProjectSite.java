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

/** 物件情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_project_site")
@Where(clause = "del_flg = '0' AND newest_flg ='1'")
public class TProjectSite extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_project_site_project_site_id_seq_generator")
  @SequenceGenerator(
      name = "t_project_site_project_site_id_seq_generator",
      sequenceName = "t_project_site_project_site_id_seq",
      allocationSize = 1)
  @Column(name = "project_site_id", nullable = false)
  private Long id;

  @Size(max = 7)
  @NotNull
  @Column(name = "project_site_cd", nullable = false, length = 7)
  private String projectSiteCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "his_no", nullable = false, length = 2)
  private String hisNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @NotNull
  @Column(name = "project_site_nm", nullable = false, length = Integer.MAX_VALUE)
  private String projectSiteNm;

  @NotNull
  @Column(name = "project_site_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String projectSiteKnNm;

  @Size(max = 9)
  @NotNull
  @Column(name = "project_cd", nullable = false, length = 9)
  private String projectCd;

  @Size(max = 9)
  @NotNull
  @Column(name = "customer_branch_cd", nullable = false, length = 9)
  private String customerBranchCd;

  @Size(max = 8)
  @NotNull
  @Column(name = "order_ymd", nullable = false, length = 8)
  private String orderYmd;

  @NotNull
  @Column(name = "excl_tax_co_total_amt", nullable = false, precision = 11)
  private BigDecimal exclTaxCoTotalAmt;

  @NotNull
  @Column(name = "incl_tax_co_total_amt", nullable = false, precision = 11)
  private BigDecimal inclTaxCoTotalAmt;

  @NotNull
  @Column(name = "sales_tax_total_amt", nullable = false, precision = 11)
  private BigDecimal salesTaxTotalAmt;

  @Size(max = 8)
  @NotNull
  @Column(name = "constr_start_ymd", nullable = false, length = 8)
  private String constrStartYmd;

  @Size(max = 8)
  @NotNull
  @Column(name = "constr_comp_ymd", nullable = false, length = 8)
  private String constrCompYmd;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ic_office_cd", nullable = false)
  private MOffice icOfficeCd;

  @Size(max = 8)
  @NotNull
  @Column(name = "constr_dept_start_dt", nullable = false, length = 8)
  private String constrDeptStartDt;

  @NotNull
  @Column(name = "constr_org_id", nullable = false)
  private Long constrOrgId;

  @Size(max = 6)
  @NotNull
  @Column(name = "const_mgr_cd", nullable = false, length = 6)
  private String constMgrCd;

  @Size(max = 6)
  @NotNull
  @Column(name = "ft_engineer_cd", nullable = false, length = 6)
  private String ftEngineerCd;

  @Size(max = 6)
  @NotNull
  @Column(name = "constr_pic_cd", nullable = false, length = 6)
  private String constrPicCd;

  @Size(max = 7)
  @NotNull
  @Column(name = "project_site_post_no", nullable = false, length = 7)
  private String projectSitePostNo;

  @NotNull
  @Column(name = "project_site_addr1", nullable = false, length = Integer.MAX_VALUE)
  private String projectSiteAddr1;

  @NotNull
  @Column(name = "project_site_addr2", nullable = false, length = Integer.MAX_VALUE)
  private String projectSiteAddr2;

  @Column(name = "constr_site_office_addr", length = Integer.MAX_VALUE)
  private String constrSiteOfficeAddr;

  @Size(max = 6)
  @NotNull
  @Column(name = "constr_site_director_cd", nullable = false, length = 6)
  private String constrSiteDirectorCd;

  @Size(max = 6)
  @NotNull
  @Column(name = "deputy_director_cd", nullable = false, length = 6)
  private String deputyDirectorCd;

  @Size(max = 6)
  @NotNull
  @Column(name = "vice_director_cd", nullable = false, length = 6)
  private String viceDirectorCd;

  @Size(max = 6)
  @NotNull
  @Column(name = "constr_site_supv_cd", nullable = false, length = 6)
  private String constrSiteSupvCd;

  @Size(max = 6)
  @NotNull
  @Column(name = "constr_site_pic_cd1", nullable = false, length = 6)
  private String constrSitePicCd1;

  @Size(max = 6)
  @NotNull
  @Column(name = "constr_site_pic_cd2", nullable = false, length = 6)
  private String constrSitePicCd2;

  @Size(max = 6)
  @NotNull
  @Column(name = "constr_site_pic_cd3", nullable = false, length = 6)
  private String constrSitePicCd3;

  @Size(max = 6)
  @NotNull
  @Column(name = "constr_site_pic_cd4", nullable = false, length = 6)
  private String constrSitePicCd4;

  @Size(max = 6)
  @NotNull
  @Column(name = "constr_site_pic_cd5", nullable = false, length = 6)
  private String constrSitePicCd5;

  @NotNull
  @Column(name = "design_vender_nm", nullable = false, length = Integer.MAX_VALUE)
  private String designVenderNm;

  @NotNull
  @Column(name = "design_pic_nm", nullable = false, length = Integer.MAX_VALUE)
  private String designPicNm;

  @Size(max = 2)
  @NotNull
  @Column(name = "zoning_k", nullable = false, length = 2)
  private String zoningK;

  @NotNull
  @Column(name = "fire_area_k", nullable = false, length = Integer.MAX_VALUE)
  private String fireAreaK;
}
