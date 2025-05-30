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
import java.time.Instant;
import java.time.OffsetDateTime;
import org.hibernate.annotations.Where;

/** WF承認ステップ情報 */
@Entity
@lombok.Getter
@lombok.Setter
@Table(name = "t_wf_appr_step")
@Where(clause = "del_flg = '0'")
public class TWfApprStep extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_wf_appr_step_wf_appr_step_id_seq_generator")
  @SequenceGenerator(
      name = "t_wf_appr_step_wf_appr_step_id_seq_generator",
      sequenceName = "t_wf_appr_step_wf_appr_step_id_seq",
      allocationSize = 1)
  @Column(name = "wf_appr_step_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wf_request_id", nullable = false)
  private TWfRequest wfRequest;

  @NotNull
  @Column(name = "appr_step_order", nullable = false)
  private Integer apprStepOrder;

  @Size(max = 6)
  @NotNull
  @Column(name = "appr_emp_cd", nullable = false, length = 6)
  private String apprEmpCd;

  @NotNull
  @Column(name = "appr_st", nullable = false, length = Integer.MAX_VALUE)
  private String apprSt;

  @Column(name = "decision_ts")
  private Instant decisionTs;

  @Column(name = "decision_comment", length = Integer.MAX_VALUE)
  private String decisionComment;
}
