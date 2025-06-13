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
import java.time.Instant;
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

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @NotNull
  @Column(name = "project_nm", nullable = false, length = Integer.MAX_VALUE)
  private String projectNm;

  @Column(name = "project_kn_nm", length = Integer.MAX_VALUE)
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

  @Column(name = "constr_site_addr2", length = Integer.MAX_VALUE)
  private String constrSiteAddr2;

  @Size(max = 8)
  @Column(name = "order_expected_ymd", length = 8)
  private String orderExpectedYmd;

  @Size(max = 8)
  @Column(name = "start_hope_ymd", length = 8)
  private String startHopeYmd;

  @Size(max = 8)
  @Column(name = "comp_hope_ymd", length = 8)
  private String compHopeYmd;

  @Column(name = "sales_org_id")
  private Long salesOrgId;

  @Size(max = 6)
  @Column(name = "sales_mgr_cd", length = 6)
  private String salesMgrCd;

  @Size(max = 6)
  @Column(name = "sales_pic_cd", length = 6)
  private String salesPicCd;

  @Column(name = "design_vendor_nm", length = Integer.MAX_VALUE)
  private String designVendorNm;

  @Column(name = "design_pic_nm", length = Integer.MAX_VALUE)
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

  @Column(name = "est_submit_due_ts")
  private Instant estSubmitDueTs;

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

  @Column(name = "constr_area", precision = 9, scale = 2)
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
  @Column(name = "constr_exp_rate", nullable = false, precision = 5, scale = 2)
  private BigDecimal constrExpRate;

  @NotNull
  @Column(name = "constr_exp_amt", nullable = false, precision = 11)
  private BigDecimal constrExpAmt;

  @NotNull
  @Column(name = "sale_mgr_rate", nullable = false, precision = 5, scale = 2)
  private BigDecimal saleMgrRate;

  @NotNull
  @Column(name = "sale_mgr_amt", nullable = false, precision = 11)
  private BigDecimal saleMgrAmt;

  @NotNull
  @Column(name = "tyousei_amt", nullable = false, precision = 11)
  private BigDecimal tyouseiAmt;

  @NotNull
  @Column(name = "eis_rate", nullable = false, precision = 5, scale = 2)
  private BigDecimal eisRate;

  @NotNull
  @Column(name = "ehi_rate", nullable = false, precision = 5, scale = 2)
  private BigDecimal ehiRate;

  @NotNull
  @Column(name = "ltc_rate", nullable = false, precision = 5, scale = 2)
  private BigDecimal ltcRate;

  @NotNull
  @Column(name = "wpi_rate", nullable = false, precision = 5, scale = 2)
  private BigDecimal wpiRate;

  @Size(max = 2)
  @Column(name = "closing_day", length = 2)
  private String closingDay;

  @Column(name = "payment_k", length = Integer.MAX_VALUE)
  private String paymentK;

  @Size(max = 2)
  @Column(name = "payment_d", length = 2)
  private String paymentD;

  @Column(name = "rejection_reason", length = Integer.MAX_VALUE)
  private String rejectionReason;

  @NotNull
  @Column(name = "his_no", nullable = false)
  private Integer hisNo;

  @Size(max = 7)
  @Column(name = "project_site_cd", length = 7)
  private String projectSiteCd;

}
