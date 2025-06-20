package com.daitoj.tkms.domain;

import jakarta.persistence.*;
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
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "m_customer_customer_id_seq_generator")
  @SequenceGenerator(
      name = "m_customer_customer_id_seq_generator",
      sequenceName = "m_customer_customer_id_seq",
      allocationSize = 1)
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

  @NotNull
  @Column(name = "his_no", nullable = false)
  private Integer hisNo;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @NotNull
  @Column(name = "customer_nm1", nullable = false, length = Integer.MAX_VALUE)
  private String customerNm1;

  @Column(name = "customer_nm2", length = Integer.MAX_VALUE)
  private String customerNm2;

  @Column(name = "customer_ryakusyou", length = Integer.MAX_VALUE)
  private String customerRyakusyou;

  @Column(name = "customer_kn_nm", length = Integer.MAX_VALUE)
  private String customerKnNm;

  @Column(name = "customer_pic_nm", length = Integer.MAX_VALUE)
  private String customerPicNm;

  @Column(name = "customer_pic_kn_nm", length = Integer.MAX_VALUE)
  private String customerPicKnNm;

  @NotNull
  @Column(name = "branch_nm", nullable = false, length = Integer.MAX_VALUE)
  private String branchNm;

  @Column(name = "branch_kn_nm", length = Integer.MAX_VALUE)
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

  @Column(name = "customer_addr2", length = Integer.MAX_VALUE)
  private String customerAddr2;

  @NotNull
  @Column(name = "customer_tel_no", nullable = false, length = Integer.MAX_VALUE)
  private String customerTelNo;

  @Column(name = "customer_pic_phone_no", length = Integer.MAX_VALUE)
  private String customerPicPhoneNo;

  @Column(name = "customer_fax_no", length = Integer.MAX_VALUE)
  private String customerFaxNo;

  @Column(name = "bank_nm", length = Integer.MAX_VALUE)
  private String bankNm;

  @Column(name = "bank_branch_nm", length = Integer.MAX_VALUE)
  private String bankBranchNm;

  @Column(name = "deposit_type", length = Integer.MAX_VALUE)
  private String depositType;

  @Column(name = "bank_account_no", length = Integer.MAX_VALUE)
  private String bankAccountNo;

  @Column(name = "bank_account_holder_nm", length = Integer.MAX_VALUE)
  private String bankAccountHolderNm;

  @Column(name = "capital", length = Integer.MAX_VALUE)
  private String capital;

  @Column(name = "employee_number", length = Integer.MAX_VALUE)
  private String employeeNumber;

  @Column(name = "gyousyu_gyoutai", length = Integer.MAX_VALUE)
  private String gyousyuGyoutai;

  @Column(name = "ceo_nm", length = Integer.MAX_VALUE)
  private String ceoNm;

  @Column(name = "comp_url", length = Integer.MAX_VALUE)
  private String compUrl;

  @Column(name = "remarks", length = Integer.MAX_VALUE)
  private String remarks;
}
