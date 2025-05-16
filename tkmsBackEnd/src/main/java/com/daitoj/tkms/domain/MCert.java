package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "m_cert")
@Where(clause = "del_flg = '0'")
public class MCert extends BaseEntity {
  @Id
  @Size(max = 5)
  @Column(name = "cert_cd", nullable = false, length = 5)
  private String certCd;

  @NotNull
  @Column(name = "cert_nm", nullable = false, length = Integer.MAX_VALUE)
  private String certNm;

  @NotNull
  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;
}
