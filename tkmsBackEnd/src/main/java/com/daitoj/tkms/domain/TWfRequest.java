package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.annotations.SQLRestriction;

/** WF申請情報. */
@Entity
@lombok.Getter
@lombok.Setter
@SQLRestriction("del_flg = '0'")
@Table(name = "t_wf_request")
public class TWfRequest extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

  @NotNull
  @Column(name = "current_step", nullable = false)
  private Integer currentStep;

  @NotNull
  @Column(name = "request_ts", nullable = false)
  private OffsetDateTime requestTs;

  @NotNull
  @Column(name = "business_data_st", nullable = false, length = Integer.MAX_VALUE)
  private String businessDataSt;

  @Column(name = "final_appr_ts")
  private OffsetDateTime finalApprTs;

  @Size(max = 6)
  @Column(name = "final_appr_emp_cd", length = 6)
  private String finalApprEmpCd;

  @Size(max = 14)
  @NotNull
  @Column(name = "request_app_cd", nullable = false, length = 14)
  private String requestAppCd;

  @NotNull
  @Column(name = "app_account_k", nullable = false, length = Integer.MAX_VALUE)
  private String appAccountK;

  @OneToMany(mappedBy = "wfRequest")
  private Set<TWfApprStep> tWfApprSteps = new LinkedHashSet<>();

  @OneToMany(mappedBy = "wfRequest")
  private Set<TWfRequestFiles> tWfRequestFiles = new LinkedHashSet<>();

  @Column(name = "request_comment", length = Integer.MAX_VALUE)
  private String requestComment;
}
