package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

/** メール送信設定 */
@Getter
@Setter
@Entity
@Table(name = "m_mail_his")
@Where(clause = "del_flg = '0'")
public class MMailHis extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "m_mail_his_mail_his_id_seq_generator")
  @SequenceGenerator(
      name = "m_mail_his_mail_his_id_seq_generator",
      sequenceName = "m_mail_his_mail_his_id_seq",
      allocationSize = 1)
  @Column(name = "mail_his_id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "mail_setting_id", nullable = false)
  private MMailSetting mailSetting;

  @Column(name = "mail_to", length = Integer.MAX_VALUE)
  private String mailTo;

  @Column(name = "mail_cc", length = Integer.MAX_VALUE)
  private String mailCc;

  @Column(name = "mail_bcc", length = Integer.MAX_VALUE)
  private String mailBcc;

  @Column(name = "mail_subject", length = Integer.MAX_VALUE)
  private String mailSubject;

  @Column(name = "mail_body", length = Integer.MAX_VALUE)
  private String mailBody;

  @Column(name = "mail_st", length = Integer.MAX_VALUE)
  private String mailSt;

  @Column(name = "err_msg", length = Integer.MAX_VALUE)
  private String errMsg;

  @Column(name = "mail_sent_ts")
  private Instant mailSentTs;
}
