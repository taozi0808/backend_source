package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

/** WF承認情報ビュー*/
@Getter
@Setter
@Entity
@Table(name = "v_wf_appr")
public class VWfAppr {

  @Column(name = "appr_st", length = Integer.MAX_VALUE)
  private String apprSt;

  @Id
  @Column(name = "wf_request_id")
  private Long wfRequestId;

  @Size(max = 6)
  @Column(name = "business_type_cd", length = 6)
  private String businessTypeCd;

  @Column(name = "business_tbl_id", length = Integer.MAX_VALUE)
  private String businessTblId;

  @Column(name = "business_data_id")
  private Long businessDataId;

  @Size(max = 14)
  @Column(name = "request_app_cd", length = 14)
  private String requestAppCd;

  @Column(name = "app_account_k", length = Integer.MAX_VALUE)
  private String appAccountK;

  @Column(name = "current_step")
  private Integer currentStep;

  @Column(name = "request_ts")
  private OffsetDateTime requestTs;

  @Column(name = "request_comment", length = Integer.MAX_VALUE)
  private String requestComment;

  @Column(name = "business_data_st", length = Integer.MAX_VALUE)
  private String businessDataSt;

  @Column(name = "final_appr_ts")
  private OffsetDateTime finalApprTs;

  @Size(max = 6)
  @Column(name = "final_appr_emp_cd", length = 6)
  private String finalApprEmpCd;

  @Column(name = "wf_request_reg_ts")
  private OffsetDateTime wfRequestRegTs;

  @Column(name = "wf_appr_step_id")
  private Long wfApprStepId;

  @Column(name = "appr_step_order")
  private Integer apprStepOrder;

  @Size(max = 6)
  @Column(name = "appr_emp_cd", length = 6)
  private String apprEmpCd;

  @Column(name = "decision_ts")
  private OffsetDateTime decisionTs;

  @Column(name = "decision_comment", length = Integer.MAX_VALUE)
  private String decisionComment;

}
