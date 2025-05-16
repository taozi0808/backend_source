package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import org.hibernate.annotations.Where;

/** 小工事情報 */
@Entity
@lombok.Getter
@lombok.Setter
@Table(name = "m_minor_work")
@Where(clause = "del_flg = '0'")
public class MMinorWork extends BaseEntity implements Serializable {

  @EmbeddedId private MMinorWorkId id;

  @NotNull
  @Column(name = "minor_work_nm", nullable = false, length = Integer.MAX_VALUE)
  private String minorWorkNm;

  @NotNull
  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;
}
