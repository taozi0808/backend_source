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
@Table(name = "m_bank")
@Where(clause = "del_flg = '0'")
public class MBank extends BaseEntity {
  @Id
  @Size(max = 5)
  @Column(name = "bank_cd", nullable = false, length = 5)
  private String bankCd;

  @NotNull
  @Column(name = "bank_nm", nullable = false, length = Integer.MAX_VALUE)
  private String bankNm;

  @NotNull
  @Column(name = "bank_kn_nm", nullable = false, length = Integer.MAX_VALUE)
  private String bankKnNm;
}
