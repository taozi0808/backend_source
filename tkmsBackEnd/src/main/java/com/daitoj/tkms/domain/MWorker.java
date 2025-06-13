package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import java.util.LinkedHashSet;
import java.util.Set;

/** 作業員情報 */
@Getter
@Setter
@Entity
@Table(name = "m_worker")
@Where(clause = "del_flg = '0' AND newest_flg = '1'")
public class MWorker extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "worker_id", nullable = false)
  private Long id;

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

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

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
  @Column(name = "ccus_cert_no", length = 14)
  private String ccusCertNo;

  @NotNull
  @ColumnDefault("'1'")
  @Column(name = "non_japanese_flg", nullable = false, length = Integer.MAX_VALUE)
  private String nonJapaneseFlg;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nationality_cd")
  private MNationality nationalityCd;

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
  @Column(name = "sp_health_checkup_ymd", length = 8)
  private String spHealthCheckupYmd;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sp_health_checkup_type_cd")
  private MSpHealthCheckupType spHealthCheckupTypeCd;

  @Size(max = 5)
  @Column(name = "sne_cd", length = 5)
  private String sneCd;

  @Size(max = 5)
  @Column(name = "skt_cd", length = 5)
  private String sktCd;

  @Size(max = 5)
  @Column(name = "cert_cd", length = 5)
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

  @NotNull
  @Column(name = "his_no", nullable = false)
  private Integer hisNo;

  @Size(max = 3)
  @Column(name = "ccus_position_k", length = 3)
  private String ccusPositionK;

  @OneToMany(mappedBy = "MWorker")
  private Set<MVendorWork> mVendorWorks = new LinkedHashSet<>();

  @OneToMany(mappedBy = "worker")
  private Set<MWorkerCert> mWorkerCerts = new LinkedHashSet<>();

  @OneToMany(mappedBy = "worker")
  private Set<MWorkerJobType> mWorkerJobTypes = new LinkedHashSet<>();

  @OneToMany(mappedBy = "worker")
  private Set<MWorkerPhone> mWorkerPhones = new LinkedHashSet<>();

  @OneToMany(mappedBy = "worker")
  private Set<MWorkerSkt> mWorkerSkts = new LinkedHashSet<>();

  @OneToMany(mappedBy = "worker")
  private Set<MWorkerSne> mWorkerSnes = new LinkedHashSet<>();


}
