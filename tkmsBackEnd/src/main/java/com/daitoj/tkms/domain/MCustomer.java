package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 顧客情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_customer")
@Where(clause = "del_flg = '0' AND newest_flg ='1'")
public class MCustomer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "customer_id", nullable = false)
  private Long id;

  @Size(max = 6)
  @NotNull
  @Column(name = "customer_cd", nullable = false, length = 6)
  private String customerCd;

  @Size(max = 3)
  @NotNull
  @Column(name = "branch_cd", nullable = false, length = 3)
  private String branchCd;

  @Size(max = 9)
  @NotNull
  @Column(name = "customer_branch_cd", nullable = false, length = 9)
  private String customerBranchCd;

  @Size(max = 2)
  @NotNull
  @Column(name = "his_no", nullable = false, length = 2)
  private String hisNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @NotNull
  @Column(name = "customer_nm1", nullable = false, length = Integer.MAX_VALUE)
  private String customerNm1;

  @NotNull
  @Column(name = "customer_nm2", nullable = false, length = Integer.MAX_VALUE)
  private String customerNm2;

  @NotNull
  @Column(name = "customer_ryakusyou", nullable = false, length = Integer.MAX_VALUE)
  private String customerRyakusyou;

  @NotNull
  @Column(name = "customer_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String customerKnNm;

  @NotNull
  @Column(name = "customer_pic_nm", nullable = false, length = Integer.MAX_VALUE)
  private String customerPicNm;

  @NotNull
  @Column(name = "customer_pic_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String customerPicKnNm;

  @NotNull
  @Column(name = "branch_nm", nullable = false, length = Integer.MAX_VALUE)
  private String branchNm;

  @NotNull
  @Column(name = "branch_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String branchKnNm;

  @NotNull
  @Column(name = "trading_k", nullable = false, length = Integer.MAX_VALUE)
  private String tradingK;

  @Size(max = 7)
  @NotNull
  @Column(name = "customer_post_no", nullable = false, length = 7)
  private String customerPostNo;

  @NotNull
  @Column(name = "customer_addr1", nullable = false, length = Integer.MAX_VALUE)
  private String customerAddr1;

  @NotNull
  @Column(name = "customer_addr2", nullable = false, length = Integer.MAX_VALUE)
  private String customerAddr2;

  @NotNull
  @Column(name = "customer_tel_no", nullable = false, length = Integer.MAX_VALUE)
  private String customerTelNo;

  @NotNull
  @Column(name = "customer_pic_phone_no", nullable = false, length = Integer.MAX_VALUE)
  private String customerPicPhoneNo;

  @NotNull
  @Column(name = "customer_fax_no", nullable = false, length = Integer.MAX_VALUE)
  private String customerFaxNo;

  @NotNull
  @Column(name = "bank_nm", nullable = false, length = Integer.MAX_VALUE)
  private String bankNm;

  @NotNull
  @Column(name = "bank_branch_nm", nullable = false, length = Integer.MAX_VALUE)
  private String bankBranchNm;

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
  @Column(name = "capital", nullable = false, length = Integer.MAX_VALUE)
  private String capital;

  @NotNull
  @Column(name = "employee_number", nullable = false, length = Integer.MAX_VALUE)
  private String employeeNumber;

  @NotNull
  @Column(name = "gyousyu_gyoutai", nullable = false, length = Integer.MAX_VALUE)
  private String gyousyuGyoutai;

  @NotNull
  @Column(name = "ceo_nm", nullable = false, length = Integer.MAX_VALUE)
  private String ceoNm;

  @NotNull
  @Column(name = "comp_url", nullable = false, length = Integer.MAX_VALUE)
  private String compUrl;

  @NotNull
  @Column(name = "remarks", nullable = false, length = Integer.MAX_VALUE)
  private String remarks;
}
