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

/** 下請契約台帳 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_sub_con_ledger")
@Where(clause = "del_flg = '0' AND newest_flg = '1'")
public class TSubConLedger extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "sub_con_ledger_id", nullable = false)
  private Long subConLedgerId;

  @Size(max = 7)
  @NotNull
  @Column(name = "project_site_cd", nullable = false)
  private String projectSiteCd;

  @Size(max = 9)
  @NotNull
  @Column(name = "pri_partner_vendor_cd", nullable = false)
  private String priPartnerVendorCd;

  @NotNull
  @Column(name = "his_no", nullable = false, length = Integer.MAX_VALUE)
  private Integer hisNo;

  @Size(max = 1)
  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false)
  private String newestFlg;

}
