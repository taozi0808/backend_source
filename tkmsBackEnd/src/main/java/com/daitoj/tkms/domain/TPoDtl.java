package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;
/** 注文書明細 */
@Getter
@Setter
@Entity
@SQLRestriction("del_flg = '0'")
@Table(name = "t_po_dtl")
public class TPoDtl extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "po_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "po_hid", nullable = false)
  private TPoHdr poHid;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @Size(max = 3)
  @NotNull
  @Column(name = "major_work_cd", nullable = false, length = 3)
  private String majorWorkCd;

  @Size(max = 4)
  @NotNull
  @Column(name = "minor_work_cd", nullable = false, length = 4)
  private String minorWorkCd;

  @NotNull
  @Column(name = "qty", nullable = false, precision = 10)
  private BigDecimal qty;

  @NotNull
  @Column(name = "price", nullable = false, precision = 10)
  private BigDecimal price;

  @Size(max = 2)
  @Column(name = "unit_k", length = 2)
  private String unitK;

  @NotNull
  @Column(name = "amt", nullable = false, precision = 11)
  private BigDecimal amt;
}
