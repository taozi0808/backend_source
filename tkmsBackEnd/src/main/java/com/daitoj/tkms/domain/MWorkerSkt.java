package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "m_worker_skt")
public class MWorkerSkt {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "worker_skt_id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "worker_id")
  private MWorker worker;
}
