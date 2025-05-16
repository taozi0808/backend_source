package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

/** 大工事情報 */
@Getter
@Setter
@Entity
@Table(name = "m_major_work")
@Where(clause = "del_flg = '0'")
public class MMajorWork extends BaseEntity {

  @Id
  @Size(max = 3)
  @Column(name = "major_work_cd", nullable = false, length = 3)
  private String majorWorkCd;

  @NotNull
  @Column(name = "major_work_nm", nullable = false, length = Integer.MAX_VALUE)
  private String majorWorkNm;

  @NotNull
  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "constr_type_cd", nullable = false)
  private MConstrType constrTypeCd;

  @NotNull
  @Column(name = "constr_site_k", nullable = false, length = Integer.MAX_VALUE)
  private String constrSiteK;
}
