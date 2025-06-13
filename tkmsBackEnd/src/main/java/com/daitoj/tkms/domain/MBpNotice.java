package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Where;

@lombok.Setter
@lombok.Getter
@Entity
@Table(name = "m_bp_notice")
@Where(clause = "del_flg = '0'")
public class MBpNotice extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "m_bp_notice_id", nullable = false)
  private Long id;

  @Size(max = 8)
  @NotNull
  @Column(name = "effective_start_dt", nullable = false, length = 8)
  private String effectiveStartDt;

  @Size(max = 8)
  @Column(name = "effective_end_dt", length = 8)
  private String effectiveEndDt;

  @NotNull
  @Column(name = "notice_content_html", nullable = false, length = Integer.MAX_VALUE)
  private String noticeContentHtml;

  @Column(name = "comment", length = Integer.MAX_VALUE)
  private String comment;

}
