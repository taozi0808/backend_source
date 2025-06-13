package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_number")
@Where(clause = "del_flg = '0'")
public class MNumber extends BaseEntity {

  @EmbeddedId private MNumberId id;

    @NotNull
    @Column(name = "seq_no", nullable = false)
    private Long seqNo;

}
