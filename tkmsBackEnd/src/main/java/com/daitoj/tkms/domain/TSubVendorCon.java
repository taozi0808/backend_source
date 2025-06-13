package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_sub_vendor_con")
public class TSubVendorCon {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "sub_vendor_con_id", nullable = false)
  private Long id;
}
