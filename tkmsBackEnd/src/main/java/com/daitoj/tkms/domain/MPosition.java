package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 役職情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_position")
@Where(clause = "del_flg = '0'")
public class MPosition extends BaseEntity {

  @Id
  @Size(max = 2)
  @Column(name = "position_cd", nullable = false, length = 2)
  private String positionCd;

  @NotNull
  @Column(name = "position_nm", nullable = false, length = Integer.MAX_VALUE)
  private String positionNm;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "init_position_k", nullable = false, length = Integer.MAX_VALUE)
  private String initPositionK;

  @NotNull
  @Column(name = "confirm_perm", nullable = false, length = Integer.MAX_VALUE)
  private String confirmPerm;

  @NotNull
  @Column(name = "delete_perm", nullable = false, length = Integer.MAX_VALUE)
  private String deletePerm;

  @NotNull
  @Column(name = "edit_perm", nullable = false, length = Integer.MAX_VALUE)
  private String editPerm;

  @NotNull
  @Column(name = "refer_perm", nullable = false, length = Integer.MAX_VALUE)
  private String referPerm;

  @NotNull
  @Column(name = "po_perm", nullable = false, length = Integer.MAX_VALUE)
  private String poPerm;
}
