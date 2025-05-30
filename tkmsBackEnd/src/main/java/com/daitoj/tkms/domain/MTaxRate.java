package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

/** 消費税率情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_tax_rate")
@Where(clause = "del_flg = '0'")
public class MTaxRate {

  @Id
  @Column(name = "tax_rate_id", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "tax_rate", nullable = false, precision = 5, scale = 2)
  private BigDecimal taxRate;

  @Size(max = 8)
  @NotNull
  @Column(name = "effective_start_dt", nullable = false, length = 8)
  private String effectiveStartDt;

  @Size(max = 8)
  @Column(name = "effective_end_dt", length = 8)
  private String effectiveEndDt;

  @NotNull
  @Column(name = "display_content", nullable = false, length = Integer.MAX_VALUE)
  private String displayContent;

  @NotNull
  @Column(name = "display_order", nullable = false)
  private Integer displayOrder;

  @Column(name = "remarks", length = Integer.MAX_VALUE)
  private String remarks;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "del_flg", nullable = false, length = Integer.MAX_VALUE)
  private String delFlg;

  @NotNull
  @Column(name = "reg_ts", nullable = false)
  private OffsetDateTime regTs;

  @Size(max = 255)
  @NotNull
  @Column(name = "reg_user_id", nullable = false)
  private String regUserId;

  @Size(max = 50)
  @Column(name = "reg_pg_id", length = 50)
  private String regPgId;

  @Column(name = "upd_ts")
  private OffsetDateTime updTs;

  @Size(max = 255)
  @Column(name = "upd_user_id")
  private String updUserId;

  @Size(max = 50)
  @Column(name = "upd_pg_id", length = 50)
  private String updPgId;
}
