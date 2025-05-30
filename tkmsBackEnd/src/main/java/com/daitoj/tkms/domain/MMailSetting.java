package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

/** メール送信設定 */
@Getter
@Setter
@Entity
@Table(name = "m_mail_setting")
@Where(clause = "del_flg = '0'")
public class MMailSetting extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "mail_setting_id", nullable = false)
  private Long id;

  @Size(max = 255)
  @NotNull
  @Column(name = "mail_function", nullable = false)
  private String mailFunction;

  @Column(name = "mail_templ_nm", length = Integer.MAX_VALUE)
  private String mailTemplNm;

  @Column(name = "def_mail_to", length = Integer.MAX_VALUE)
  private String defMailTo;

  @Column(name = "def_mail_cc", length = Integer.MAX_VALUE)
  private String defMailCc;

  @Column(name = "def_mail_bcc", length = Integer.MAX_VALUE)
  private String defMailBcc;

  @Column(name = "def_mail_subject", length = Integer.MAX_VALUE)
  private String defMailSubject;

  @NotNull
  @Column(name = "mail_type", nullable = false, length = Integer.MAX_VALUE)
  private String mailType;

  @NotNull
  @Column(name = "comment", nullable = false, length = Integer.MAX_VALUE)
  private String comment;
}
