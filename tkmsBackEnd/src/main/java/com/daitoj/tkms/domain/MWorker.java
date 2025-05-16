package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 作業員情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_worker")
@Where(clause = "del_flg = '0'")
public class MWorker extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "worker_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "vendor_id", nullable = false)
  private MVendor vendor;

  @Size(max = 255)
  @NotNull
  @Column(name = "mail_address", nullable = false)
  private String mailAddress;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "login_id", nullable = false)
  private MLogin login;

  @Size(max = 14)
  @NotNull
  @Column(name = "worker_cd", nullable = false, length = 14)
  private String workerCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "his_no", nullable = false, length = 2)
  private String hisNo;

  @NotNull
  @Column(name = "worker_nm", nullable = false, length = Integer.MAX_VALUE)
  private String workerNm;

  @NotNull
  @Column(name = "worker_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String workerKnNm;

  @Size(max = 8)
  @NotNull
  @Column(name = "employ_ymd", nullable = false, length = 8)
  private String employYmd;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "job_type_cd", nullable = false)
  private MJobType jobTypeCd;

  @Size(max = 8)
  @NotNull
  @Column(name = "birth_date", nullable = false, length = 8)
  private String birthDate;

  @NotNull
  @Column(name = "sex", nullable = false, length = Integer.MAX_VALUE)
  private String sex;

  @Column(name = "blood_type_k", length = Integer.MAX_VALUE)
  private String bloodTypeK;

  @Size(max = 14)
  @NotNull
  @Column(name = "ccus_cert_no", nullable = false, length = 14)
  private String ccusCertNo;

  @NotNull
  @ColumnDefault("'1'")
  @Column(name = "non_japanese_k", nullable = false, length = Integer.MAX_VALUE)
  private String nonJapaneseK;

  @NotNull
  @Column(name = "worker_tel_no", nullable = false, length = Integer.MAX_VALUE)
  private String workerTelNo;

  @Size(max = 7)
  @Column(name = "worker_post_no", length = 7)
  private String workerPostNo;

  @NotNull
  @Column(name = "worker_addr1", nullable = false, length = Integer.MAX_VALUE)
  private String workerAddr1;

  @Column(name = "worker_addr2", length = Integer.MAX_VALUE)
  private String workerAddr2;

  @Column(name = "ecp_nm", length = Integer.MAX_VALUE)
  private String ecpNm;

  @Column(name = "ecp_rel", length = Integer.MAX_VALUE)
  private String ecpRel;

  @Column(name = "ecp_addr", length = Integer.MAX_VALUE)
  private String ecpAddr;

  @Column(name = "ecp_tel_no", length = Integer.MAX_VALUE)
  private String ecpTelNo;

  @Size(max = 8)
  @Column(name = "health_checkup_day", length = 8)
  private String healthCheckupDay;

  @Column(name = "dbp")
  private Integer dbp;

  @Column(name = "sbp")
  private Integer sbp;

  @Size(max = 8)
  @Column(name = "sp_health_checkup_day", length = 8)
  private String spHealthCheckupDay;

  @Column(name = "sp_health_checkup_type", length = Integer.MAX_VALUE)
  private String spHealthCheckupType;

  @Column(name = "sne_cd", length = Integer.MAX_VALUE)
  private String sneCd;

  @Column(name = "skt_cd", length = Integer.MAX_VALUE)
  private String sktCd;

  @Column(name = "cert_cd", length = Integer.MAX_VALUE)
  private String certCd;

  @Column(name = "employ_k", length = Integer.MAX_VALUE)
  private String employK;

  @Size(max = 8)
  @Column(name = "employ_edu_ymd", length = 8)
  private String employEduYmd;

  @Size(max = 8)
  @Column(name = "foreman_edu_ymd", length = 8)
  private String foremanEduYmd;

  @Size(max = 6)
  @Column(name = "chief_engineer_cd", length = 6)
  private String chiefEngineerCd;

  @Column(name = "wci_spec_no", length = Integer.MAX_VALUE)
  private String wciSpecNo;

  @Column(name = "ehi_type_k", length = Integer.MAX_VALUE)
  private String ehiTypeK;

  @Column(name = "ehi_no", length = Integer.MAX_VALUE)
  private String ehiNo;

  @Column(name = "ehi_deselect_reason_k", length = Integer.MAX_VALUE)
  private String ehiDeselectReasonK;

  @Column(name = "wpi_type_k", length = Integer.MAX_VALUE)
  private String wpiTypeK;

  @Column(name = "wpi_no", length = Integer.MAX_VALUE)
  private String wpiNo;

  @Column(name = "wpi_deselect_reason_k", length = Integer.MAX_VALUE)
  private String wpiDeselectReasonK;

  @Column(name = "eis_type_k", length = Integer.MAX_VALUE)
  private String eisTypeK;

  @Column(name = "eis_no", length = Integer.MAX_VALUE)
  private String eisNo;

  @Column(name = "eis_deselect_reason_k", length = Integer.MAX_VALUE)
  private String eisDeselectReasonK;
}
