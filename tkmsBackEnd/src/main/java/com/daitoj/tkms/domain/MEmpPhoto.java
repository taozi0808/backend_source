package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;

/** 従業員顔写真情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_emp_photo")
@Where(clause = "del_flg = '0'")
public class MEmpPhoto extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "m_emp_photo_emp_photo_id_seq_generator")
  @SequenceGenerator(
      name = "m_emp_photo_emp_photo_id_seq_generator",
      sequenceName = "m_emp_photo_emp_photo_id_seq",
      allocationSize = 1)
  @Column(name = "emp_photo_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "emp_id", nullable = false)
  private MEmp emp;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @NotNull
  @Column(name = "photo_url", nullable = false, length = Integer.MAX_VALUE)
  private String photoUrl;
}
