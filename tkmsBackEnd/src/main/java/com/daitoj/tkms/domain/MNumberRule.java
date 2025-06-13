package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Where;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_number_rule")
@Where(clause = "del_flg = '0'")
public class MNumberRule extends BaseEntity {

  @Id
  @Size(max = 255)
  @Column(name = "field_id", nullable = false)
  private String fieldId;

  @NotNull
  @Column(name = "number_pat", nullable = false, length = Integer.MAX_VALUE)
  private String numberPat;

  @Size(max = 20)
  @Column(name = "prefix", length = 20)
  private String prefix;

    @NotNull
    @Column(name = "seq_from", nullable = false)
    private Long seqFrom;

    @NotNull
    @Column(name = "seq_to", nullable = false)
    private Long seqTo;

    @Column(name = "remarks", length = Integer.MAX_VALUE)
    private String remarks;

}
