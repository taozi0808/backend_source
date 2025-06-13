package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;

/** 注文書ヘッダ */
@Getter
@Setter
@Entity
@SQLRestriction("del_flg = '0' AND newest_flg = '1'")
@Table(name = "t_po_hdr")
public class TPoHdr extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "po_hid", nullable = false)
  private Long id;

  @Size(max = 10)
  @NotNull
  @Column(name = "po_no", nullable = false, length = 10)
  private String poNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private TProject project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exec_bgt_hid")
  private TExecBgtHdr execBgtHid;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "constr_site_id")
  private TConstrSite constrSite;

  @NotNull
  @Column(name = "vendor_id", nullable = false)
  private Long vendorId;

  @Size(max = 3)
  @Column(name = "ic_office_cd", length = 3)
  private String icOfficeCd;

  @NotNull
  @Column(name = "po_total_amt", nullable = false, precision = 11)
  private BigDecimal poTotalAmt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "tax_rate_id", nullable = false)
  private MTaxRate taxRate;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "fee_rate_id", nullable = false)
  private MFeeRate feeRate;

  @NotNull
  @Column(name = "fee_amt", nullable = false, precision = 11)
  private BigDecimal feeAmt;

  @NotNull
  @Column(name = "his_no", nullable = false)
  private Integer hisNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @NotNull
  @Column(name = "po_st_k", nullable = false, length = Integer.MAX_VALUE)
  private String poStK;

  @Size(max = 2)
  @NotNull
  @Column(name = "po_no_seq_no", nullable = false, length = 2)
  private String poNoSeqNo;

  @Column(name = "po_create_k", length = Integer.MAX_VALUE)
  private String poCreateK;

  @Column(name = "worker_list_nt_flg", length = Integer.MAX_VALUE)
  private String workerListNtFlg;

  @Size(max = 8)
  @Column(name = "constr_period_strat_ymd", length = 8)
  private String constrPeriodStratYmd;

  @Size(max = 8)
  @Column(name = "constr_period_end_ymd", length = 8)
  private String constrPeriodEndYmd;

  @Size(max = 12)
  @Column(name = "constr_site_cd", length = 12)
  private String constrSiteCd;

  @OneToMany(mappedBy = "poHid")
  private Set<TPoDtl> tPoDtls = new LinkedHashSet<>();

  @OneToMany(mappedBy = "poHid")
  private Set<TProjectPreWorkDtl> tProjectPreWorkDtls = new LinkedHashSet<>();
}
