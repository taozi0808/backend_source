package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/** 項目リスト設定のキー */
@lombok.Getter
@lombok.Setter
@Embeddable
public class MItemListSettingId implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @Size(max = 5)
  @NotNull
  @Column(name = "item_class_cd", nullable = false, length = 5)
  private String itemClassCd;

  @Size(max = 8)
  @NotNull
  @Column(name = "effective_start_dt", nullable = false, length = 8)
  private String effectiveStartDt;

  @Size(max = 10)
  @NotNull
  @Column(name = "item_cd", nullable = false, length = 10)
  private String itemCd;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    MItemListSettingId that = (MItemListSettingId) o;
    return Objects.equals(itemClassCd, that.itemClassCd)
        && Objects.equals(effectiveStartDt, that.effectiveStartDt)
        && Objects.equals(itemCd, that.itemCd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemClassCd, effectiveStartDt, itemCd);
  }
}
