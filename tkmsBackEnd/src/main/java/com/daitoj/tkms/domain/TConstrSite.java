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
import org.hibernate.annotations.Where;

/** 現場情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_constr_site")
@Where(clause = "del_flg = '0'")
public class TConstrSite extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_constr_site_constr_site_id_seq_generator")
  @SequenceGenerator(
      name = "t_constr_site_constr_site_id_seq_generator",
      sequenceName = "t_constr_site_constr_site_id_seq",
      allocationSize = 1)
  @Column(name = "constr_site_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "project_site_id", nullable = false)
  private TProjectSite projectSite;

  @Size(max = 2)
  @NotNull
  @Column(name = "building_cd", nullable = false, length = 2)
  private String buildingCd;

  @Size(max = 12)
  @NotNull
  @Column(name = "constr_site_cd", nullable = false, length = 12)
  private String constrSiteCd;

  @Column(name = "constr_site_nm", length = Integer.MAX_VALUE)
  private String constrSiteNm;

  @Column(name = "constr_site_kn_nm", length = Integer.MAX_VALUE)
  private String constrSiteKnNm;

  @Size(max = 3)
  @NotNull
  @Column(name = "related_work_cd", nullable = false, length = 3)
  private String relatedWorkCd;

  @Column(name = "building_area", precision = 9, scale = 2)
  private BigDecimal buildingArea;

  @Column(name = "gross_floor_area", precision = 9, scale = 2)
  private BigDecimal grossFloorArea;

  @Column(name = "buildup_area", precision = 9, scale = 2)
  private BigDecimal buildupArea;

  @NotNull
  @Column(name = "floor_cnt", nullable = false, precision = 3)
  private BigDecimal floorCnt;

  @NotNull
  @Column(name = "basement_cnt", nullable = false, precision = 3)
  private BigDecimal basementCnt;

  @Column(name = "structure_k", length = Integer.MAX_VALUE)
  private String structureK;

  @Size(max = 8)
  @Column(name = "constr_site_start_ymd", length = 8)
  private String constrSiteStartYmd;

  @Size(max = 8)
  @Column(name = "constr_site_delivery_ymd", length = 8)
  private String constrSiteDeliveryYmd;

  @Column(name = "excl_tax_co_amt", precision = 11)
  private BigDecimal exclTaxCoAmt;

  @Column(name = "incl_tax_co_amt", precision = 11)
  private BigDecimal inclTaxCoAmt;

  @Column(name = "co_sales_tax_amt", precision = 11)
  private BigDecimal coSalesTaxAmt;

  @Column(name = "gross_profit_amt", length = Integer.MAX_VALUE)
  private String grossProfitAmt;

  @Column(name = "gross_profit_rate", precision = 5, scale = 2)
  private BigDecimal grossProfitRate;

  @Column(name = "memo", length = Integer.MAX_VALUE)
  private String memo;
}
