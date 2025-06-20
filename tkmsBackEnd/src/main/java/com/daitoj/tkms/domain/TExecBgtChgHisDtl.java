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
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;

/** 実行予算変更履歴明細. */
@lombok.Getter
@lombok.Setter
@Entity
@SQLRestriction("del_flg = '0'")
@Table(name = "t_exec_bgt_chg_his_dtl")
public class TExecBgtChgHisDtl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "exec_bgt_chg_his_did", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exec_bgt_chg_his_hid", nullable = false)
    private TExecBgtChgHisHdr execBgtChgHisHid;

    @NotNull
    @Column(name = "chg_amt", nullable = false, precision = 11)
    private BigDecimal chgAmt;

    @NotNull
    @Column(name = "chg_k", nullable = false, length = Integer.MAX_VALUE)
    private String chgK;

    @NotNull
    @Column(name = "seq_no", nullable = false)
    private Integer seqNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exec_bgt_did", nullable = false)
    private TExecBgtDtl execBgtDid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exec_bgt_pub_did", nullable = false)
    private TExecBgtPubDtl execBgtPubDid;

}
