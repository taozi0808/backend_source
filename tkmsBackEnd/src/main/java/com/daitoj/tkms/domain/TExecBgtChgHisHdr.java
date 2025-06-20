package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

/** 実行予算変更履歴ヘッダ. */
@lombok.Getter
@lombok.Setter
@Entity
@SQLRestriction("del_flg = '0'")
@Table(name = "t_exec_bgt_chg_his_hdr")
public class TExecBgtChgHisHdr extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "exec_bgt_chg_his_hid", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "chg_reason", nullable = false, length = Integer.MAX_VALUE)
  private String chgReason;
}
