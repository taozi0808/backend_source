package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.annotations.Where;

/** 出来高シミュレーション */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_ev_sim")
@Where(clause = "del_flg = '0'")
public class TEvSim extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_ev_sim_ev_sim_id_seq_generator")
  @SequenceGenerator(
      name = "t_ev_sim_ev_sim_id_seq_generator",
      sequenceName = "t_ev_sim_ev_sim_id_seq",
      allocationSize = 1)
  @Column(name = "ev_sim_id", nullable = false)
  private Long id;

  @Column(name = "rough_est_hid", nullable = false)
  private Long roughEstHid;

  @Size(max = 12)
  @Column(name = "constr_site_cd", length = 12)
  private String constrSiteCd;

  @Size(max = 8)
  @Column(name = "ev_reg_dt", length = 8)
  private String evRegDt;

  @Size(max = 12)
  @Column(name = "rough_est_cd", length = 12)
  private String roughEstCd;

  @OneToMany(mappedBy = "evSim")
  private Set<TEvSimBgt> tEvSimBgts = new LinkedHashSet<>();
}
