package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Where;

/** 機能情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_program")
@Where(clause = "del_flg = '0'")
public class MProgram extends BaseEntity {

  @Id
  @Size(max = 225)
  @Column(name = "pg_id", nullable = false, length = 225)
  private String pgId;

  @Size(max = 255)
  @NotNull
  @Column(name = "pg_nm", nullable = false)
  private String pgNm;

  @Column(name = "pg_remark", length = Integer.MAX_VALUE)
  private String pgRemark;

  @Size(max = 255)
  @Column(name = "pg_component")
  private String pgComponent;

  @NotNull
  @Column(name = "confirm_perm_chk_flg", nullable = false, length = Integer.MAX_VALUE)
  private String confirmPermChkFlg;

  @NotNull
  @Column(name = "delete_perm_chk_flg", nullable = false, length = Integer.MAX_VALUE)
  private String deletePermChkFlg;

  @NotNull
  @Column(name = "edit_perm_chk_flg", nullable = false, length = Integer.MAX_VALUE)
  private String editPermChkFlg;

  @NotNull
  @Column(name = "refer_perm_chk_flg", nullable = false, length = Integer.MAX_VALUE)
  private String referPermChkFlg;

  @NotNull
  @Column(name = "po_perm_chk_flg", nullable = false, length = Integer.MAX_VALUE)
  private String poPermChkFlg;
}
