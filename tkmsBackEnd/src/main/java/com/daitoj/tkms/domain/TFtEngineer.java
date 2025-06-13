package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_ft_engineer")
public class TFtEngineer {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_site_id")
  private TProjectSite projectSite;

  @Id
  @NotNull
  @ColumnDefault("nextval('t_ft_engineer_ft_engineer_id_seq')")
  @Column(name = "ft_engineer_id", nullable = false)
  private Long ftEngineerId;

  @Size(max = 8)
  @NotNull
  @Column(name = "hire_start_ymd", nullable = false, length = 8)
  private String hireStartYmd;

  @Size(max = 8)
  @Column(name = "hire_end_ymd", length = 8)
  private String hireEndYmd;

  @Size(max = 6)
  @NotNull
  @Column(name = "pic_cd", nullable = false, length = 6)
  private String picCd;

  @NotNull
  @ColumnDefault("'0'")
  @Column(name = "del_flg", nullable = false, length = Integer.MAX_VALUE)
  private String delFlg;

  @NotNull
  @Column(name = "reg_ts", nullable = false)
  private Instant regTs;

  @Size(max = 255)
  @NotNull
  @Column(name = "reg_user_id", nullable = false)
  private String regUserId;

  @Size(max = 50)
  @Column(name = "reg_pg_id", length = 50)
  private String regPgId;

  @Column(name = "upd_ts")
  private Instant updTs;

  @Size(max = 255)
  @Column(name = "upd_user_id")
  private String updUserId;

  @Size(max = 50)
  @Column(name = "upd_pg_id", length = 50)
  private String updPgId;
}
