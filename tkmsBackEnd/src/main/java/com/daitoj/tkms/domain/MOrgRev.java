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

/** 組織改定情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_org_rev")
@Where(clause = "del_flg = '0'")
public class MOrgRev extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "org_rev_id", nullable = false)
  private Long id;

  @Size(max = 8)
  @NotNull
  @Column(name = "effective_start_dt", nullable = false, length = 8)
  private String effectiveStartDt;
}
