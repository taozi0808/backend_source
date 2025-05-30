package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_po_hdr")
public class TPoHdr {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "po_hid", nullable = false)
  private Long id;

  // TODO [Reverse Engineering] generate columns from DB
}
