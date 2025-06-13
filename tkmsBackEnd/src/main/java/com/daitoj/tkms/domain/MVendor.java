package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;

/** 業者情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_vendor")
@SQLRestriction("del_flg = '0' AND newest_flg = '1'")
public class MVendor extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "vendor_id", nullable = false)
  private Long id;

  @Size(max = 6)
  @NotNull
  @Column(name = "vendor_cd", nullable = false, length = 6)
  private String vendorCd;

  @Size(max = 3)
  @NotNull
  @Column(name = "branch_cd", nullable = false, length = 3)
  private String branchCd;

  @Size(max = 9)
  @NotNull
  @Column(name = "partner_vendor_cd", nullable = false, length = 9)
  private String partnerVendorCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "his_no", nullable = false, length = 2)
  private String hisNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @NotNull
  @Column(name = "trading_stop_k", nullable = false, length = Integer.MAX_VALUE)
  private String tradingStopK;

  @NotNull
  @Column(name = "comp_nm", nullable = false, length = Integer.MAX_VALUE)
  private String compNm;

  @NotNull
  @Column(name = "comp_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String compKnNm;

  @Column(name = "branch_nm", length = Integer.MAX_VALUE)
  private String branchNm;

  @Column(name = "branch_kn_nm", length = Integer.MAX_VALUE)
  private String branchKnNm;

  @NotNull
  @Column(name = "ceo_nm", nullable = false, length = Integer.MAX_VALUE)
  private String ceoNm;

  @Column(name = "ceo_kn_nm", length = Integer.MAX_VALUE)
  private String ceoKnNm;

  @Size(max = 255)
  @NotNull
  @Column(name = "mail_address", nullable = false)
  private String mailAddress;

  @Size(max = 7)
  @Column(name = "post_no", length = 7)
  private String postNo;

  @Size(max = 6)
  @NotNull
  @Column(name = "initial_reg_user_cd", nullable = false, length = 6)
  private String initialRegUserCd;

  @NotNull
  @Column(name = "vendor_addr1", nullable = false, length = Integer.MAX_VALUE)
  private String vendorAddr1;

  @Column(name = "vendor_addr2", length = Integer.MAX_VALUE)
  private String vendorAddr2;

  @Column(name = "vendor_tel_no", length = Integer.MAX_VALUE)
  private String vendorTelNo;

  @Column(name = "vendor_fax_no", length = Integer.MAX_VALUE)
  private String vendorFaxNo;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns({
    @JoinColumn(name = "bank_cd", referencedColumnName = "bank_cd", nullable = false),
    @JoinColumn(name = "bank_branch_nm", referencedColumnName = "bank_banch_cd", nullable = false)
  })
  private MBankBanch mBankBanch;

  @NotNull
  @Column(name = "deposit_type", nullable = false, length = Integer.MAX_VALUE)
  private String depositType;

  @NotNull
  @Column(name = "bank_account_no", nullable = false, length = Integer.MAX_VALUE)
  private String bankAccountNo;

  @NotNull
  @Column(name = "bank_account_holder_nm", nullable = false, length = Integer.MAX_VALUE)
  private String bankAccountHolderNm;

  @NotNull
  @Column(name = "bank_account_holder_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String bankAccountHolderKnNm;

  @Size(max = 8)
  @Column(name = "comp_found_dt", length = 8)
  private String compFoundDt;

  @NotNull
  @Column(name = "comp_k", nullable = false, length = Integer.MAX_VALUE)
  private String compK;

  @Column(name = "capital", length = Integer.MAX_VALUE)
  private String capital;

  @Size(max = 14)
  @Column(name = "invoice_reg_no", length = 14)
  private String invoiceRegNo;

  @Column(name = "office_no", length = Integer.MAX_VALUE)
  private String officeNo;

  @Column(name = "insurance_rate", precision = 3)
  private BigDecimal insuranceRate;

  @Column(name = "employee_number", length = Integer.MAX_VALUE)
  private String employeeNumber;

  @Column(name = "mgr_nop", precision = 6)
  private BigDecimal mgrNop;

  @Column(name = "engineer_nop", precision = 6)
  private BigDecimal engineerNop;

  @Column(name = "clerk_nop", precision = 6)
  private BigDecimal clerkNop;

  @Column(name = "comp_his", length = Integer.MAX_VALUE)
  private String compHis;

  @NotNull
  @Column(name = "demol_perm_flg", nullable = false, length = Integer.MAX_VALUE)
  private String demolPermFlg;

  @NotNull
  @Column(name = "demol_perm_pref", nullable = false, length = Integer.MAX_VALUE)
  private String demolPermPref;

  @NotNull
  @Column(name = "demol_perm_no1", nullable = false, length = Integer.MAX_VALUE)
  private String demolPermNo1;

  @NotNull
  @Column(name = "demol_perm_no2", nullable = false, length = Integer.MAX_VALUE)
  private String demolPermNo2;

  @Size(max = 8)
  @NotNull
  @Column(name = "demol_perm_dt", nullable = false, length = 8)
  private String demolPermDt;

  @NotNull
  @Column(name = "security_cert_flg", nullable = false, length = Integer.MAX_VALUE)
  private String securityCertFlg;

  @NotNull
  @Column(name = "security_cert_pref", nullable = false, length = Integer.MAX_VALUE)
  private String securityCertPref;

  @NotNull
  @Column(name = "security_cert_no", nullable = false, length = Integer.MAX_VALUE)
  private String securityCertNo;

  @Size(max = 8)
  @NotNull
  @Column(name = "security_cert_dt", nullable = false, length = 8)
  private String securityCertDt;

  @Column(name = "ccus_office_id", length = Integer.MAX_VALUE)
  private String ccusOfficeId;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "login_id", nullable = false)
  private MLogin login;
}
