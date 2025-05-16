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

  @Size(max = 12)
  @NotNull
  @Column(name = "constr_site_cd", nullable = false, length = 12)
  private String constrSiteCd;

  @NotNull
  @Column(name = "constr_site_nm", nullable = false, length = Integer.MAX_VALUE)
  private String constrSiteNm;

  @NotNull
  @Column(name = "constr_site_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String constrSiteKnNm;

  @Size(max = 3)
  @NotNull
  @Column(name = "related_work_cd", nullable = false, length = 3)
  private String relatedWorkCd;

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
  @Column(name = "floor_cnt", nullable = false, precision = 3)
  private BigDecimal floorCnt;

  @NotNull
  @Column(name = "basement_cnt", nullable = false, precision = 3)
  private BigDecimal basementCnt;

  @NotNull
  @Column(name = "structure_k", nullable = false, length = Integer.MAX_VALUE)
  private String structureK;

  @Size(max = 8)
  @NotNull
  @Column(name = "constr_site_start_ymd", nullable = false, length = 8)
  private String constrSiteStartYmd;

  @Size(max = 8)
  @NotNull
  @Column(name = "constr_site_delivery_ymd", nullable = false, length = 8)
  private String constrSiteDeliveryYmd;

  @NotNull
  @Column(name = "excl_tax_co_amt", nullable = false, precision = 11)
  private BigDecimal exclTaxCoAmt;

  @NotNull
  @Column(name = "incl_tax_co_amt", nullable = false, precision = 11)
  private BigDecimal inclTaxCoAmt;

  @NotNull
  @Column(name = "co_sales_tax_amt", nullable = false, precision = 11)
  private BigDecimal coSalesTaxAmt;

  @NotNull
  @Column(name = "gross_profit_amt", nullable = false, length = Integer.MAX_VALUE)
  private String grossProfitAmt;

  @NotNull
  @Column(name = "gross_profit_rate", nullable = false, length = Integer.MAX_VALUE)
  private String grossProfitRate;

  @NotNull
  @Column(name = "memo", nullable = false, length = Integer.MAX_VALUE)
  private String memo;
}
