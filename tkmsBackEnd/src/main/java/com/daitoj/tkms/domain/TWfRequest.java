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
import org.hibernate.annotations.Where;

/** WF申請情報 */
@Entity
@lombok.Getter
@lombok.Setter
@Table(name = "t_wf_request")
@Where(clause = "del_flg = '0'")
public class TWfRequest extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_wf_request_wf_request_id_seq_generator")
  @SequenceGenerator(
      name = "t_wf_request_wf_request_id_seq_generator",
      sequenceName = "t_wf_request_wf_request_id_seq",
      allocationSize = 1)
  @Column(name = "wf_request_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "business_type_cd", nullable = false)
  private MBusinessType businessTypeCd;

  @NotNull
  @Column(name = "business_tbl_id", nullable = false, length = Integer.MAX_VALUE)
  private String businessTblId;

  @NotNull
  @Column(name = "business_data_id", nullable = false)
  private Long businessDataId;

  @Size(max = 6)
  @NotNull
  @Column(name = "request_emp_cd", nullable = false, length = 6)
  private String requestEmpCd;

  @NotNull
  @Column(name = "current_step", nullable = false)
  private Integer currentStep;

  @NotNull
  @Column(name = "request_ts", nullable = false)
  private Instant requestTs;

  @NotNull
  @Column(name = "business_data_st", nullable = false, length = Integer.MAX_VALUE)
  private String businessDataSt;

  @Column(name = "final_appr_ts")
  private Instant finalApprTs;

  @Size(max = 6)
  @Column(name = "final_appr_emp_cd", length = 6)
  private String finalApprEmpCd;
}
