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
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

/** メール送信添付ファイル履歴 */
@Getter
@Setter
@Entity
@Table(name = "m_mail_attach_his")
@Where(clause = "del_flg = '0'")
public class MMailAttachHis extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "m_mail_attach_his_mail_attach_his_id_seq_generator")
  @SequenceGenerator(
      name = "m_mail_attach_his_mail_attach_his_id_seq_generator",
      sequenceName = "m_mail_attach_his_mail_attach_his_id_seq",
      allocationSize = 1)
  @Column(name = "mail_attach_his_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "mail_his_id", nullable = false)
  private MMailHis mailHis;

  @NotNull
  @Column(name = "attach_file_nm", nullable = false, length = Integer.MAX_VALUE)
  private String attachFileNm;

  @NotNull
  @Column(name = "attach_file_path", nullable = false, length = Integer.MAX_VALUE)
  private String attachFilePath;

  @NotNull
  @Column(name = "attach_file_type", nullable = false, length = Integer.MAX_VALUE)
  private String attachFileType;

  @NotNull
  @Column(name = "attach_file_size", nullable = false)
  private Integer attachFileSize;
}
