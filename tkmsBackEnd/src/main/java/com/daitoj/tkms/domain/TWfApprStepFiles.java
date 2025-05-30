package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.hibernate.annotations.Where;

/** WF承認ステップ添付ファイル情報 */
@Entity
@lombok.Getter
@lombok.Setter
@Table(name = "t_wf_appr_step_files")
@Where(clause = "del_flg = '0'")
public class TWfApprStepFiles extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_wf_appr_step_files_wf_appr_step_files_id_seq_generator")
  @SequenceGenerator(
      name = "t_wf_appr_step_files_wf_appr_step_files_id_seq_generator",
      sequenceName = "t_wf_appr_step_files_wf_appr_step_files_id_seq",
      allocationSize = 1)
  @Column(name = "wf_appr_step_files_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "wf_appr_step_id", nullable = false)
  private Long wfApprStepId;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @NotNull
  @Column(name = "file_id", nullable = false)
  private UUID fileId;
}
