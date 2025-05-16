package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "m_price_region")
public class MPriceRegion {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "price_region_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "user_pg_k", nullable = false, length = Integer.MAX_VALUE)
  private String userPgK;

  @NotNull
  @Column(name = "constr_site_k", nullable = false, length = Integer.MAX_VALUE)
  private String constrSiteK;

  @Size(max = 3)
  @NotNull
  @Column(name = "region_cd", nullable = false, length = 3)
  private String regionCd;

  @NotNull
  @Column(name = "region_nm", nullable = false, length = Integer.MAX_VALUE)
  private String regionNm;

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
