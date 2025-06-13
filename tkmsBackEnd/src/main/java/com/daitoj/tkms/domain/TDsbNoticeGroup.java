package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;

import java.util.LinkedHashSet;
import java.util.Set;

/** 通知グループ情報. */
@lombok.Setter
@lombok.Getter
@Entity
@Table(name = "t_dsb_notice_group")
@Where(clause = "del_flg = '0'")
public class TDsbNoticeGroup extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "dsb_notice_group_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "notice_group_nm", nullable = false, length = Integer.MAX_VALUE)
  private String noticeGroupNm;

  @OneToMany(mappedBy = "dsbNoticeGroup")
  private Set<TDsbNoticeGroupUser> tDsbNoticeGroupUsers = new LinkedHashSet<>();

  @OneToMany(mappedBy = "dsbNoticeGroup")
  private Set<TNoticeSelGroup> tNoticeSelGroups = new LinkedHashSet<>();

}
