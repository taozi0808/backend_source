package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "m_vendor_work")
public class MVendorWork {
  @EmbeddedId private MVendorWorkId id;

  @MapsId("workerId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "worker_id")
  private MWorker MWorker;
}
