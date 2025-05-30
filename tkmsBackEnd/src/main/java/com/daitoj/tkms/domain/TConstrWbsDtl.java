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

/** 先行作業明細 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_constr_wbs_dtl")
@Where(clause = "del_flg = '0'")
public class TConstrWbsDtl extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_constr_wbs_dtl_id_gen")
  @SequenceGenerator(
      name = "t_constr_wbs_dtl_id_gen",
      sequenceName = "t_constr_wbs_dtl_constr_wbs_did_seq",
      allocationSize = 1)
  @Column(name = "constr_wbs_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "constr_wbs_hid", nullable = false)
  private TConstrWbsHdr constrWbsHid;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @NotNull
  @ColumnDefault("'1'")
  @Column(name = "show_flg", nullable = false, length = Integer.MAX_VALUE)
  private String showFlg;

  @Column(name = "floor", precision = 3)
  private BigDecimal floor;

  @Size(max = 8)
  @Column(name = "plan_start_dt", length = 8)
  private String planStartDt;

  @Size(max = 8)
  @Column(name = "plan_end_dt", length = 8)
  private String planEndDt;

  @Size(max = 8)
  @Column(name = "act_start_dt", length = 8)
  private String actStartDt;

  @Size(max = 8)
  @Column(name = "act_end_dt", length = 8)
  private String actEndDt;

  @NotNull
  @Column(name = "constr_process_show_nm", nullable = false, length = Integer.MAX_VALUE)
  private String constrProcessShowNm;

  @NotNull
  @Column(name = "start_dt_edit_k", nullable = false, length = Integer.MAX_VALUE)
  private String startDtEditK;

  @NotNull
  @Column(name = "end_dt_k", nullable = false, length = Integer.MAX_VALUE)
  private String endDtK;

  @Column(name = "partner_vendor_nm", length = Integer.MAX_VALUE)
  private String partnerVendorNm;
}
