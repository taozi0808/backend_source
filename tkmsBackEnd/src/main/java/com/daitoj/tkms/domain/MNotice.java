package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Where;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "m_notice")
@Where(clause = "del_flg = '0'")
public class MNotice extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId private MNoticeId id;

  @NotNull
  @Column(name = "notice_content_html", nullable = false, length = Integer.MAX_VALUE)
  private String noticeContentHtml;

  @Column(name = "comment", length = Integer.MAX_VALUE)
  private String comment;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    MNotice mNotice = (MNotice) o;
    return Objects.equals(id, mNotice.id)
        && Objects.equals(noticeContentHtml, mNotice.noticeContentHtml)
        && Objects.equals(comment, mNotice.comment)
        && Objects.equals(delFlg, mNotice.delFlg)
        && Objects.equals(regTs, mNotice.regTs)
        && Objects.equals(regUserId, mNotice.regUserId)
        && Objects.equals(regPgId, mNotice.regPgId)
        && Objects.equals(updTs, mNotice.updTs)
        && Objects.equals(updUserId, mNotice.updUserId)
        && Objects.equals(updPgId, mNotice.updPgId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        noticeContentHtml,
        comment,
        delFlg,
        regTs,
        regUserId,
        regPgId,
        updTs,
        updUserId,
        updPgId);
  }
}
