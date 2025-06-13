package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.SQLRestriction;

/** 工事工程情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_constr_process")
@SQLRestriction("del_flg = '0'")
public class MConstrProcess extends BaseEntity {
  @Id
  @Size(max = 6)
  @SequenceGenerator(
      name = "m_constr_process_id_gen",
      sequenceName = "t_constr_wbs_dtl_constr_wbs_did_seq",
      allocationSize = 1)
  @Column(name = "constr_process_cd", nullable = false, length = 6)
  private String constrProcessCd;

  @NotNull
  @Column(name = "constr_process_nm", nullable = false, length = Integer.MAX_VALUE)
  private String constrProcessNm;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_terms_cd")
  private MPaymentTerm paymentTermsCd;

  @NotNull
  @Column(name = "floor_create_k", nullable = false, length = Integer.MAX_VALUE)
  private String floorCreateK;

  @NotNull
  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;
}
