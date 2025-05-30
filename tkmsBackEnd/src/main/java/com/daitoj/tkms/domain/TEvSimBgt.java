package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "t_ev_sim_bgt")
@Where(clause = "del_flg = '0'")
public class TEvSimBgt extends BaseEntity {
  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_ev_sim_bgt_ev_sim_bgt_id_seq_generator")
  @SequenceGenerator(
      name = "t_ev_sim_bgt_ev_sim_bgt_id_seq_generator",
      sequenceName = "t_ev_sim_bgt_ev_sim_bgt_id_seq",
      allocationSize = 1)
  @Column(name = "ev_sim_bgt_id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ev_sim_id")
  private TEvSim evSim;

  @Size(max = 3)
  @NotNull
  @Column(name = "major_work_cd", nullable = false, length = 3)
  private String majorWorkCd;

  @Size(max = 6)
  @Column(name = "constr_period_strat_ym", length = 6)
  private String constrPeriodStratYm;

  @Size(max = 6)
  @Column(name = "constr_period_end_ym", length = 6)
  private String constrPeriodEndYm;
}
