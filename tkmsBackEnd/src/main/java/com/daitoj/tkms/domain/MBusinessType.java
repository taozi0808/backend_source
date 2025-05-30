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

/** 業務種類情報 */
@Getter
@Setter
@Entity
@Table(name = "m_business_type")
@Where(clause = "del_flg = '0'")
public class MBusinessType extends BaseEntity {

  @Id
  @Size(max = 6)
  @Column(name = "business_type_cd", nullable = false, length = 6)
  private String businessTypeCd;

  @NotNull
  @Column(name = "business_type_nm", nullable = false, length = Integer.MAX_VALUE)
  private String businessTypeNm;

  @NotNull
  @Column(name = "business_tbl_id", nullable = false, length = Integer.MAX_VALUE)
  private String businessTblId;

  @NotNull
  @Column(name = "business_type_k", nullable = false, length = Integer.MAX_VALUE)
  private String businessTypeK;
}
