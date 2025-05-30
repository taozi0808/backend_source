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
@Table(name = "m_office")
@Where(clause = "del_flg = '0'")
public class MOffice extends BaseEntity {
  @Id
  @Size(max = 3)
  @Column(name = "office_cd", nullable = false, length = 3)
  private String officeCd;

  @NotNull
  @Column(name = "office_nm", nullable = false, length = Integer.MAX_VALUE)
  private String officeNm;
}
