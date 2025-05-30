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
import java.util.UUID;
import org.hibernate.annotations.ColumnDefault;
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
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "project_id", nullable = false)
  private TProject project;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @Size(max = 3)
  @NotNull
  @Column(name = "major_work_cd", nullable = false, length = 3)
  private String majorWorkCd;

  @Size(max = 4)
  @NotNull
  @Column(name = "minor_work_cd", nullable = false, length = 4)
  private String minorWorkCd;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "detailed_est_did", nullable = false)
  private TDetailedEstDtl detailedEstDid;

  @Size(max = 8)
  @Column(name = "est_ymd", length = 8)
  private String estYmd;

  @NotNull
  @Column(name = "outline", nullable = false, length = Integer.MAX_VALUE)
  private String outline;

  @Column(name = "pre_constr_content", length = Integer.MAX_VALUE)
  private String preConstrContent;

  @Column(name = "constr_cost", length = Integer.MAX_VALUE)
  private String constrCost;

  @Column(name = "payment_terms", length = Integer.MAX_VALUE)
  private String paymentTerms;

  @Column(name = "sonota", length = Integer.MAX_VALUE)
  private String sonota;

  @Column(name = "file_id", nullable = true)
  private UUID fileId;

  @NotNull
  @ColumnDefault("'1'")
  @Column(name = "pre_work_process_k", nullable = false, length = Integer.MAX_VALUE)
  private String preWorkProcessK = "1";

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "po_hid", nullable = false)
  private TPoHdr poHid;

  @Size(max = 3)
  @NotNull
  @Column(name = "work_seq_no", nullable = false, length = 3)
  private String workSeqNo;
}
