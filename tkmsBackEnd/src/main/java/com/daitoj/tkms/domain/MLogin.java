package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** ログイン情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_login")
@Where(clause = "del_flg = '0'")
public class MLogin extends BaseEntity {

  @Id
  @Size(max = 255)
  @Column(name = "login_id", nullable = false)
  private String loginId;

  @NotNull
  @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
  private String password;

  @NotNull
  @Column(name = "account_k", nullable = false, length = Integer.MAX_VALUE)
  private String accountK;

  @Size(max = 8)
  @ColumnDefault("19000101")
  @Column(name = "start_ymd", nullable = false, length = 8)
  private String startYmd = "19000101";

  @Size(max = 8)
  @Column(name = "end_ymd", length = 8)
  private String endYmd;
}
