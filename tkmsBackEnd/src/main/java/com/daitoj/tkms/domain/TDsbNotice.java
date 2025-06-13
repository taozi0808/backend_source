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

/** 通知通達情報. */
@lombok.Setter
@lombok.Getter
@Entity
@Table(name = "t_dsb_notice")
@Where(clause = "del_flg = '0'")
public class TDsbNotice extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "dsb_notice_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "notice_k", nullable = false, length = Integer.MAX_VALUE)
  private String noticeK;

  @NotNull
  @Column(name = "notice_title", nullable = false, length = Integer.MAX_VALUE)
  private String noticeTitle;

  @NotNull
  @Column(name = "notice_content", nullable = false, length = Integer.MAX_VALUE)
  private String noticeContent;

  @NotNull
  @Column(name = "open_flg", nullable = false, length = Integer.MAX_VALUE)
  private String openFlg;

  @NotNull
  @Column(name = "admin_standards_rev_flg", nullable = false, length = Integer.MAX_VALUE)
  private String adminStandardsRevFlg;

  @NotNull
  @Column(name = "answer_req_flg", nullable = false, length = Integer.MAX_VALUE)
  private String answerReqFlg;

  @Column(name = "rel_notice", length = Integer.MAX_VALUE)
  private String relNotice;

  @OneToMany(mappedBy = "dsbNotice")
  private Set<TNoticeSelGroup> tNoticeSelGroups = new LinkedHashSet<>();

}
