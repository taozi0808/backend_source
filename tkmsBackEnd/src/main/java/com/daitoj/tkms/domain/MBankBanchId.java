package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Embeddable
public class MBankBanchId implements Serializable {
  private static final long serialVersionUID = -9049154326099522616L;

  @Size(max = 5)
  @NotNull
  @Column(name = "bank_cd", nullable = false, length = 5)
  private String bankCd;

  @Size(max = 4)
  @NotNull
  @Column(name = "bank_banch_cd", nullable = false, length = 4)
  private String bankBanchCd;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MBankBanchId entity = (MBankBanchId) o;
    return Objects.equals(this.bankCd, entity.bankCd)
        && Objects.equals(this.bankBanchCd, entity.bankBanchCd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bankCd, bankBanchCd);
  }
}
