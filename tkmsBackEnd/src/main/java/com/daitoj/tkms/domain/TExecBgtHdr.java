package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;


/** 実行予算ヘッダ */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_exec_bgt_hdr")
@Where(clause = "del_flg = '0' AND newest_flg ='1'")
public class TExecBgtHdr extends BaseEntity {
  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_exec_bgt_hdr_exec_bgt_hid_seq_generator")
  @SequenceGenerator(
      name = "t_exec_bgt_hdr_exec_bgt_hid_seq_generator",
      sequenceName = "t_exec_bgt_hdr_exec_bgt_hid_seq",
      allocationSize = 1)
  @Column(name = "exec_bgt_hid", nullable = false)
  private Long id;

  @Size(max = 12)
  @NotNull
  @Column(name = "exec_bgt_cd", nullable = false, length = 12)
  private String execBgtCd;

  @NotNull
  @ColumnDefault("'2'")
  @Column(name = "newest_flg", nullable = false, length = Integer.MAX_VALUE)
  private String newestFlg;

  @Size(max = 12)
  @NotNull
  @Column(name = "constr_site_cd", nullable = false, length = 12)
  private String constrSiteCd;

  @Size(max = 8)
  @Column(name = "bgt_ym_req_dt", length = 8)
  private String bgtYmReqDt;

  @Column(name = "bgt_create_org_id")
  private Long bgtCreateOrgId;

  @Size(max = 6)
  @Column(name = "bgt_create_pic_cd", length = 6)
  private String bgtCreatePicCd;

  @Size(max = 12)
  @NotNull
  @Column(name = "detailed_est_cd", nullable = false, length = 12)
  private String detailedEstCd;

  @NotNull
  @Column(name = "exec_bgt_total_amt", nullable = false, precision = 11)
  private BigDecimal execBgtTotalAmt;

  @NotNull
  @Column(name = "his_no", nullable = false)
  private Integer hisNo;

}
