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
@Table(name = "m_sp_health_checkup_type")
public class MSpHealthCheckupType {
  @Id
  @Size(max = 2)
  @Column(name = "sp_health_checkup_type_cd", nullable = false, length = 2)
  private String spHealthCheckupTypeCd;
}
