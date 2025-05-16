package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Where;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_job_type")
@Where(clause = "del_flg = '0'")
public class MJobType extends BaseEntity {
  @Id
  @Size(max = 4)
  @Column(name = "job_type_cd", nullable = false, length = 4)
  private String jobTypeCd;

  @NotNull
  @Column(name = "job_type_nm", nullable = false, length = Integer.MAX_VALUE)
  private String jobTypeNm;

  @NotNull
  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;
}
