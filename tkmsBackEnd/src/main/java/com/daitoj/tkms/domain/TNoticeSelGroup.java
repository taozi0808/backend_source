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
import org.hibernate.annotations.Where;

/** 通知グループ選択情報. */
@lombok.Setter
@lombok.Getter
@Entity
@Table(name = "t_notice_sel_group")
@Where(clause = "del_flg = '0'")
public class TNoticeSelGroup extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "notice_sel_group_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "dsb_notice_id", nullable = false)
  private TDsbNotice dsbNotice;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "dsb_notice_group_id", nullable = false)
  private TDsbNoticeGroup dsbNoticeGroup;

}
