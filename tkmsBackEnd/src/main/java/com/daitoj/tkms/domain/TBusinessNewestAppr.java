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
import java.time.Instant;
import org.hibernate.annotations.Where;

/** 業務データ最新承認情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_business_newest_appr")
@Where(clause = "del_flg = '0'")
public class TBusinessNewestAppr extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_business_newest_appr_business_newest_appr_id_seq_generator")
  @SequenceGenerator(
      name = "t_business_newest_appr_business_newest_appr_id_seq_generator",
      sequenceName = "t_business_newest_appr_business_newest_appr_id_seq",
      allocationSize = 1)
  @Column(name = "business_newest_appr_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "newest_wf_request_id", nullable = false)
  private Long newestWfRequestId;

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
  @Column(name = "request_ts", nullable = false)
  private Instant requestTs;

  @NotNull
  @Column(name = "business_data_st", nullable = false, length = Integer.MAX_VALUE)
  private String businessDataSt;

  @Size(max = 8)
  @Column(name = "final_appr_dt", length = 8)
  private String finalApprDt;

  @Size(max = 6)
  @Column(name = "final_appr_emp_cd", length = 6)
  private String finalApprEmpCd;
}
