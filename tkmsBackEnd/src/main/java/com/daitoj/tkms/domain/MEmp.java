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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 従業員情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_emp")
@Where(clause = "del_flg = '0' AND newest_flg ='1'")
public class MEmp extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_emp_emp_id_seq_generator")
  @SequenceGenerator(
      name = "m_emp_emp_id_seq_generator",
      sequenceName = "m_emp_emp_id_seq",
      allocationSize = 1)
  @Column(name = "emp_id", nullable = false)
  private Long id;

  @Size(max = 6)
  @NotNull
  @Column(name = "emp_cd", nullable = false, length = 6)
  private String empCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "his_no", nullable = false, length = 2)
  private String hisNo;

  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg = "2";

  @NotNull
  @Column(name = "emp_fam_nm", nullable = false, length = Integer.MAX_VALUE)
  private String empFamNm;

  @NotNull
  @Column(name = "emp_per_nm", nullable = false, length = Integer.MAX_VALUE)
  private String empPerNm;

  @NotNull
  @Column(name = "emp_nm", nullable = false, length = Integer.MAX_VALUE)
  private String empNm;

  @NotNull
  @Size(max = 2)
  @Column(name = "emp_job_type_k", nullable = false, length = 2)
  private String empJobTypeCd;

  @Size(max = 8)
  @NotNull
  @Column(name = "birth_date", nullable = false, length = 8)
  private String birthDate;

  @NotNull
  @Column(name = "sex", nullable = false, length = Integer.MAX_VALUE)
  private String sex;

  @Size(max = 8)
  @NotNull
  @Column(name = "employment_ymd", nullable = false, length = 8)
  private String employmentYmd;

  @Size(max = 8)
  @ColumnDefault("NULL")
  @Column(name = "termination_ymd", length = 8)
  private String terminationYmd = null;

  @Size(max = 255)
  @NotNull
  @Column(name = "mail_address", nullable = false)
  private String mailAddress;

  @Column(name = "comp_phone_no", length = Integer.MAX_VALUE)
  private String compPhoneNo;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "login_id", nullable = false)
  private MLogin login;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "belong_office_cd", nullable = false)
  private MOffice belongOfficeCd;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "position_cd", nullable = false)
  private MPosition positionCd;

  @Size(max = 7)
  @Column(name = "emp_post_no", length = 7)
  private String empPostNo;

  @NotNull
  @Column(name = "emp_addr1", nullable = false, length = Integer.MAX_VALUE)
  private String empAddr1;

  @Column(name = "emp_addr2", length = Integer.MAX_VALUE)
  private String empAddr2;

  @Column(name = "idl_phone_no", length = Integer.MAX_VALUE)
  private String idlPhoneNo;

  @Column(name = "home_tel_no", length = Integer.MAX_VALUE)
  private String homeTelNo;

  @Column(name = "ecn_nm", length = Integer.MAX_VALUE)
  private String ecnNm;

  @Column(name = "ecn_rel", length = Integer.MAX_VALUE)
  private String ecnRel;

  @Column(name = "ecn_addr", length = Integer.MAX_VALUE)
  private String ecnAddr;

  @Column(name = "ecn_tel_no", length = Integer.MAX_VALUE)
  private String ecnTelNo;

  @ColumnDefault("'1'")
  @Column(name = "use_time_control_flg", nullable = false, length = Integer.MAX_VALUE)
  private String useTimeControlFlg = "1";

  @OneToMany(mappedBy = "emp", fetch = FetchType.LAZY)
  private Set<MEmpCert> empCertList;

  @OneToMany(mappedBy = "emp", fetch = FetchType.LAZY)
  private Set<MEmpPhoto> empPhotoList;

  @OneToMany(mappedBy = "emp", fetch = FetchType.LAZY)
  private Set<MEmpOrg> empOrgList;
}
