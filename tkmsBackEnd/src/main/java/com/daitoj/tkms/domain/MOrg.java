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
import org.hibernate.annotations.Where;

/** 組織情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_org")
@Where(clause = "del_flg = '0'")
public class MOrg extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "org_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "org_rev_id", nullable = false)
  private Long orgRevId;

  @Size(max = 6)
  @NotNull
  @Column(name = "org_cd", nullable = false, length = 6)
  private String orgCd;

  @NotNull
  @Column(name = "org_nm", nullable = false, length = Integer.MAX_VALUE)
  private String orgNm;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "parent_org_id", nullable = false)
  private MOrg parentOrg;

  @NotNull
  @Column(name = "lvl", nullable = false)
  private Integer lvl;

  @NotNull
  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;
}
