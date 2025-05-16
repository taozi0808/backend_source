package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "m_constr_type")
public class MConstrType {
  @Id
  @Size(max = 3)
  @Column(name = "constr_type_cd", nullable = false, length = 3)
  private String constrTypeCd;
}
