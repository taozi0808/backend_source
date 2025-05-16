package com.daitoj.tkms.domain;

import com.daitoj.tkms.modules.common.constants.KeyConstants;
import com.daitoj.tkms.modules.common.utils.ContextUtils;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;

/** ベースエンティティクラス */
@MappedSuperclass
@lombok.Getter
@lombok.Setter
public class BaseEntity {

  @Size(max = 1)
  @ColumnDefault("'0'")
  @Column(name = "del_flg", nullable = false, length = 1)
  protected String delFlg = "0";

  @NotNull
  @Column(name = "reg_ts", nullable = false)
  protected Instant regTs;

  @Size(max = 255)
  @NotNull
  @Column(name = "reg_user_id", nullable = false)
  protected String regUserId;

  @Size(max = 50)
  @Column(name = "reg_pg_id", length = 50)
  protected String regPgId;

  @Column(name = "upd_ts")
  protected Instant updTs;

  @Size(max = 255)
  @Column(name = "upd_user_id")
  protected String updUserId;

  @Size(max = 50)
  @Column(name = "upd_pg_id", length = 50)
  protected String updPgId;

  /** 作成者を設定 */
  @PrePersist
  public void prePersist() {
    regUserId = ContextUtils.getParameter(KeyConstants.HTTP_HEADER_LOGIN_ID);
    regPgId = ContextUtils.getParameter(KeyConstants.HTTP_HEADER_PG_ID);

    regTs = DateUtils.getDbDateTime();
  }

  /** 更新者を設定 */
  @PreUpdate
  public void preUpdate() {
    updUserId = ContextUtils.getParameter(KeyConstants.HTTP_HEADER_LOGIN_ID);
    updPgId = ContextUtils.getParameter(KeyConstants.HTTP_HEADER_PG_ID);

    updTs = DateUtils.getDbDateTime();
  }
}
