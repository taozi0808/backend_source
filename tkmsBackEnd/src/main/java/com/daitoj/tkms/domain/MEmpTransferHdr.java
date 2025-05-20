package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import org.hibernate.annotations.Where;

/** 従業員異動ヘッダ情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_emp_transfer_hdr")
@Where(clause = "del_flg = '0'")
public class MEmpTransferHdr extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "emp_transfer_hid", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "emp_id", nullable = false)
  private MEmp emp;

  @Size(max = 8)
  @NotNull
  @Column(name = "effective_start_dt", nullable = false, length = 8)
  private String effectiveStartDt;

  @Size(max = 2)
  @NotNull
  @Column(name = "position_cd", nullable = false, length = 2)
  private String positionCd;

  @OneToMany(mappedBy = "empTransferHid", fetch = FetchType.LAZY)
  private Set<MEmpTransferDtl> dtlList;
}
