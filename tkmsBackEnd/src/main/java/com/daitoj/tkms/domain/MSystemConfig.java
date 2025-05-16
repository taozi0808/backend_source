package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Where;

/** システム設定 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_system_config")
@Where(clause = "del_flg = '0'")
public class MSystemConfig extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId private MSystemConfigId id;

  @NotNull
  @Column(name = "config_value", nullable = false, length = Integer.MAX_VALUE)
  private String configValue;

  @Column(name = "config_desc", length = Integer.MAX_VALUE)
  private String configDesc;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    MSystemConfig that = (MSystemConfig) o;

    return Objects.equals(id, that.id)
        && Objects.equals(configValue, that.configValue)
        && Objects.equals(configDesc, that.configDesc)
        && Objects.equals(delFlg, that.delFlg)
        && Objects.equals(regTs, that.regTs)
        && Objects.equals(regUserId, that.regUserId)
        && Objects.equals(regPgId, that.regPgId)
        && Objects.equals(updTs, that.updTs)
        && Objects.equals(updUserId, that.updUserId)
        && Objects.equals(updPgId, that.updPgId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, configValue, configDesc, delFlg, regTs, regUserId, regPgId, updTs, updUserId, updPgId);
  }
}
