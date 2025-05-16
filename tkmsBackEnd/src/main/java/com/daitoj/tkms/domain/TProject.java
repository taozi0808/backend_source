package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 案件情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_project")
@Where(clause = "del_flg = '0' AND newest_flg ='1'")
public class TProject extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_project_project_id_seq_generator")
  @SequenceGenerator(
      name = "t_project_project_id_seq_generator",
      sequenceName = "t_project_project_id_seq",
      allocationSize = 1)
  @Column(name = "project_id", nullable = false)
  private Long id;

  @Size(max = 9)
  @NotNull
  @Column(name = "project_cd", nullable = false, length = 9)
  private String projectCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "his_no", nullable = false, length = 2)
  private String hisNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @NotNull
  @Column(name = "project_nm", nullable = false, length = Integer.MAX_VALUE)
  private String projectNm;

  @NotNull
  @Column(name = "project_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String projectKnNm;

  @NotNull
  @Column(name = "order_st_cd", nullable = false, length = Integer.MAX_VALUE)
  private String orderStCd;

  @Size(max = 9)
  @NotNull
  @Column(name = "customer_branch_cd", nullable = false, length = 9)
  private String customerBranchCd;

  @NotNull
  @Column(name = "expect_amt", nullable = false, precision = 11)
  private BigDecimal expectAmt;

  @Size(max = 7)
  @NotNull
  @Column(name = "post_no", nullable = false, length = 7)
  private String postNo;

  @NotNull
  @Column(name = "constr_site_addr1", nullable = false, length = Integer.MAX_VALUE)
  private String constrSiteAddr1;

  @NotNull
  @Column(name = "constr_site_addr2", nullable = false, length = Integer.MAX_VALUE)
  private String constrSiteAddr2;

  @Size(max = 8)
  @NotNull
  @Column(name = "order_expected_ymd", nullable = false, length = 8)
  private String orderExpectedYmd;

  @Size(max = 8)
  @NotNull
  @Column(name = "start_hope_ymd", nullable = false, length = 8)
  private String startHopeYmd;

  @Size(max = 8)
  @NotNull
  @Column(name = "comp_hope_ymd", nullable = false, length = 8)
  private String compHopeYmd;

  @Size(max = 8)
  @NotNull
  @Column(name = "sales_dept_start_dt", nullable = false, length = 8)
  private String salesDeptStartDt;

  @NotNull
  @Column(name = "sales_org_id", nullable = false)
  private Long salesOrgId;

  @Size(max = 6)
  @NotNull
  @Column(name = "sales_mgr_cd", nullable = false, length = 6)
  private String salesMgrCd;

  @Size(max = 6)
  @NotNull
  @Column(name = "sales_pic_cd", nullable = false, length = 6)
  private String salesPicCd;

  @NotNull
  @Column(name = "design_vender", nullable = false, length = Integer.MAX_VALUE)
  private String designVender;

  @NotNull
  @Column(name = "design_pic_nm", nullable = false, length = Integer.MAX_VALUE)
  private String designPicNm;

  @Size(max = 2)
  @NotNull
  @Column(name = "progress_cd", nullable = false, length = 2)
  private String progressCd;

  @NotNull
  @Column(name = "project_k", nullable = false, length = Integer.MAX_VALUE)
  private String projectK;

  @NotNull
  @Column(name = "gov_peo_k", nullable = false, length = Integer.MAX_VALUE)
  private String govPeoK;

  @NotNull
  @Column(name = "green_site_flg", nullable = false, length = Integer.MAX_VALUE)
  private String greenSiteFlg;

  @Size(max = 8)
  @NotNull
  @Column(name = "est_submit_due_dt", nullable = false, length = 8)
  private String estSubmitDueDt;

  @NotNull
  @Column(name = "site_area", nullable = false, precision = 9, scale = 2)
  private BigDecimal siteArea;

  @NotNull
  @Column(name = "building_area", nullable = false, precision = 9, scale = 2)
  private BigDecimal buildingArea;

  @NotNull
  @Column(name = "gross_floor_area", nullable = false, precision = 9, scale = 2)
  private BigDecimal grossFloorArea;

  @NotNull
  @Column(name = "buildup_area", nullable = false, precision = 9, scale = 2)
  private BigDecimal buildupArea;

  @NotNull
  @Column(name = "occupied_area", nullable = false, precision = 9, scale = 2)
  private BigDecimal occupiedArea;

  @NotNull
  @Column(name = "constr_area", nullable = false, precision = 9, scale = 2)
  private BigDecimal constrArea;

  @NotNull
  @Column(name = "households", nullable = false, precision = 7)
  private BigDecimal households;

  @NotNull
  @Column(name = "floor_cnt", nullable = false, precision = 3)
  private BigDecimal floorCnt;

  @NotNull
  @Column(name = "basement_cnt", nullable = false, precision = 3)
  private BigDecimal basementCnt;

  @NotNull
  @Column(name = "closing_day", nullable = false, length = Integer.MAX_VALUE)
  private String closingDay;

  @NotNull
  @Column(name = "payment_k", nullable = false, length = Integer.MAX_VALUE)
  private String paymentK;

  @NotNull
  @Column(name = "payment_d", nullable = false, length = Integer.MAX_VALUE)
  private String paymentD;

  @NotNull
  @Column(name = "rejection_reason", nullable = false, length = Integer.MAX_VALUE)
  private String rejectionReason;
}
