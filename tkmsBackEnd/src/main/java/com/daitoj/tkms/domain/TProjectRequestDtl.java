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
import org.hibernate.annotations.Where;

/** 案件要望明細情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_project_request_dtl")
@Where(clause = "del_flg = '0'")
public class TProjectRequestDtl extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_project_request_dtl_project_request_did_seq_generator")
  @SequenceGenerator(
      name = "t_project_request_dtl_project_request_did_seq_generator",
      sequenceName = "t_project_request_dtl_project_request_did_seq",
      allocationSize = 1)
  @Column(name = "project_request_did", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "project_id", nullable = false)
  private Long projectId;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @Size(max = 8)
  @NotNull
  @Column(name = "request_ymd", nullable = false, length = 8)
  private String requestYmd;

  @NotNull
  @Column(name = "request_content", nullable = false, length = Integer.MAX_VALUE)
  private String requestContent;

  @Column(name = "request_answer", length = Integer.MAX_VALUE)
  private String requestAnswer;
}
