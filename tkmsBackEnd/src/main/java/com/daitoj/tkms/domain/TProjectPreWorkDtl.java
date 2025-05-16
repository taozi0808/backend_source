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
import org.hibernate.annotations.Where;

/** 先行作業明細 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_project_pre_work_dtl")
@Where(clause = "del_flg = '0'")
public class TProjectPreWorkDtl extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_project_pre_work_dtl_project_pre_work_did_seq_generator")
  @SequenceGenerator(
      name = "t_project_pre_work_dtl_project_pre_work_did_seq_generator",
      sequenceName = "t_project_pre_work_dtl_project_pre_work_did_seq",
      allocationSize = 1)
  @Column(name = "project_pre_work_did", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "project_id", nullable = false)
  private Long projectId;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "price_id", nullable = true)
  private MPrice priceId;

  @NotNull
  @Column(name = "outline", nullable = false, length = Integer.MAX_VALUE)
  private String outline;

  @NotNull
  @Column(name = "pre_constr_content", nullable = false, length = Integer.MAX_VALUE)
  private String preConstrContent;

  @NotNull
  @Column(name = "constr_cost", nullable = false, length = Integer.MAX_VALUE)
  private String constrCost;

  @NotNull
  @Column(name = "payment_terms", nullable = false, length = Integer.MAX_VALUE)
  private String paymentTerms;

  @NotNull
  @Column(name = "sonota", nullable = false, length = Integer.MAX_VALUE)
  private String sonota;

  @NotNull
  @Column(name = "file_id", nullable = false)
  private Long fileId;
}
