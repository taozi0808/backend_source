package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Where;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_menu")
@Where(clause = "del_flg = '0'")
public class MMenu extends BaseEntity {
  @Id
  @Column(name = "menu_id", nullable = false)
  private Integer id;

  @Size(max = 255)
  @NotNull
  @Column(name = "menu_nm", nullable = false)
  private String menuNm;
}
