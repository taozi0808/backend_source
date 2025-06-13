package com.daitoj.tkms.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import org.hibernate.annotations.Where;

/** 通知グループユーザー情報. */
@lombok.Setter
@lombok.Getter
@Entity
@Table(name = "t_dsb_notice_group_user")
@Where(clause = "del_flg = '0'")
public class TDsbNoticeGroupUser extends BaseEntity {

    @EmbeddedId
    private TDsbNoticeGroupUserId id;

    @MapsId("dsbNoticeGroupId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dsb_notice_group_id", nullable = false)
    private TDsbNoticeGroup dsbNoticeGroup;

}
