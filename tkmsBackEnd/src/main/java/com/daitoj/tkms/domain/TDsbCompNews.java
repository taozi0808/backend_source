package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;

/** 社報情報. */
@lombok.Setter
@lombok.Getter
@Entity
@Table(name = "t_dsb_comp_news")
@Where(clause = "del_flg = '0'")
public class TDsbCompNews extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "dsb_comp_news_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "comp_news_title", nullable = false, length = Integer.MAX_VALUE)
  private String compNewsTitle;

  @NotNull
  @Column(name = "comp_news_content", nullable = false, length = Integer.MAX_VALUE)
  private String compNewsContent;

  @NotNull
  @Column(name = "comp_news_type_k", nullable = false, length = Integer.MAX_VALUE)
  private String compNewsTypeK;

}
