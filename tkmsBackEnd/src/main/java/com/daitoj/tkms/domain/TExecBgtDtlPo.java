package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 実行予算明細発注情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "T_EXEC_BGT_DTL_PO")
@Where(clause = "del_flg = '0'")
public class TExecBgtDtlPo {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "exec_bgt_dtl_po_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "exec_bgt_did", nullable = false)
  private TExecBgtDtl execBgtDid;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @Size(max = 10)
  @Column(name = "po_no", length = 10)
  private String poNo;

  @NotNull
  @Column(name = "po_amt", nullable = false, precision = 11)
  private BigDecimal poAmt;

  @Size(max = 6)
  @Column(name = "partner_vendor_cd", length = 6)
  private String partnerVendorCd;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "del_flg", nullable = false, length = Integer.MAX_VALUE)
  private String delFlg;

  @NotNull
  @Column(name = "reg_ts", nullable = false)
  private OffsetDateTime regTs;

  @Size(max = 255)
  @NotNull
  @Column(name = "reg_user_id", nullable = false)
  private String regUserId;

  @Size(max = 50)
  @Column(name = "reg_pg_id", length = 50)
  private String regPgId;

  @Column(name = "upd_ts")
  private OffsetDateTime updTs;

  @Size(max = 255)
  @Column(name = "upd_user_id")
  private String updUserId;

  @Size(max = 50)
  @Column(name = "upd_pg_id", length = 50)
  private String updPgId;
}
