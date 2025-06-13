package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "m_worker_job_type")
public class MWorkerJobType {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "worker_job_type_id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "worker_id")
  private MWorker worker;
}
