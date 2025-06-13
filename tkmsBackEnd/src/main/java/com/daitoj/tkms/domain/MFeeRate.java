package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "m_fee_rate")
public class MFeeRate {
  @Id
  @Column(name = "fee_rate_id", nullable = false)
  private Integer id;
}
