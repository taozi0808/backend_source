package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Embeddable
public class TDsbNoticeGroupUserId implements java.io.Serializable {
    private static final long serialVersionUID = 1685240493765114124L;
    @NotNull
    @Column(name = "dsb_notice_group_id", nullable = false)
    private Long dsbNoticeGroupId;

    @Size(max = 6)
    @NotNull
    @Column(name = "emp_cd", nullable = false, length = 6)
    private String empCd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TDsbNoticeGroupUserId entity = (TDsbNoticeGroupUserId) o;
        return Objects.equals(this.dsbNoticeGroupId, entity.dsbNoticeGroupId) &&
                Objects.equals(this.empCd, entity.empCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dsbNoticeGroupId, empCd);
    }

}
