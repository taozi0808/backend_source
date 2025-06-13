package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** 一般申請情報. */
@lombok.Setter
@lombok.Getter
@Entity
@Table(name = "t_dsb_gen_appl")
public class TDsbGenAppl extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "dsb_gen_appl_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "gen_appl_title", nullable = false, length = Integer.MAX_VALUE)
  private String genApplTitle;

  @NotNull
  @Column(name = "gen_appl_content", nullable = false, length = Integer.MAX_VALUE)
  private String genApplContent;

  @NotNull
  @Column(name = "gen_appl_k", nullable = false, length = Integer.MAX_VALUE)
  private String genApplK;

  @Size(max = 8)
  @NotNull
  @Column(name = "answer_hope_ymd", nullable = false, length = 8)
  private String answerHopeYmd;

}
