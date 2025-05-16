package com.daitoj.tkms.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "m_bank_banch")
public class MBankBanch {
  @EmbeddedId private MBankBanchId id;

  // TODO [Reverse Engineering] generate columns from DB
}
