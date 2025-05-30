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
public class MPriceRegion extends BaseEntity {

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
}
