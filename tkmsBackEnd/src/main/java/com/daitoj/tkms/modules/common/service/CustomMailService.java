package com.daitoj.tkms.modules.common.service;

import com.daitoj.tkms.domain.MMailAttachHis;
import com.daitoj.tkms.domain.MMailHis;
import com.daitoj.tkms.domain.MMailSetting;
import com.daitoj.tkms.modules.common.constants.MailSt;
import com.daitoj.tkms.modules.common.repository.MMailAttachHisRepository;
import com.daitoj.tkms.modules.common.repository.MMailHisRepository;
import com.daitoj.tkms.modules.common.repository.MMailSettingRepository;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.spring6.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

/** メールサービス */
@Service
public class CustomMailService {
  private static final Logger LOG = LoggerFactory.getLogger(CustomMailService.class);

  private final JHipsterProperties jhipsterProperties;

  private final JavaMailSender javaMailSender;

  private final SpringTemplateEngine templateEngine;

  /** メッセージ */
  private final MessageSource messageSource;

  /** メール送信設定リポジトリ */
  private final MMailSettingRepository mmailSettingRepository;

  /** メール送信履歴リポジトリ */
  private final MMailHisRepository mmailHisRepository;

  /** メール送信添付ファイル履歴リポジトリ */
  private final MMailAttachHisRepository mmailAttachHisRepository;

  /** テンプレート */
  private static final String MAIL_TEMPLATE = "mail/";

  /** メールタイプ: テキスト */
  private static final String MAIL_TYPE_TEXT = "1";

  /** コンストラクタ */
  public CustomMailService(
      JHipsterProperties jhipsterProperties,
      JavaMailSender javaMailSender,
      MessageSource messageSource,
      SpringTemplateEngine templateEngine,
      MMailSettingRepository mmailSettingRepository,
      MMailAttachHisRepository mmailAttachHisRepository,
      MMailHisRepository mmailHisRepository) {
    this.jhipsterProperties = jhipsterProperties;
    this.javaMailSender = javaMailSender;
    this.messageSource = messageSource;
    this.templateEngine = templateEngine;
    this.mmailSettingRepository = mmailSettingRepository;
    this.mmailAttachHisRepository = mmailAttachHisRepository;
    this.mmailHisRepository = mmailHisRepository;
  }

  /**
   * メール送信処理
   *
   * @param pgId 送信機能
   * @param titleKey 件名キー
   * @param to TO
   * @param ccList CCリスト
   * @param bccList BCCリスト
   * @param varMap メール本文埋め字マップ
   * @param files 添付ファイルリスト
   * @return メール送信履歴ID
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
  public Long sendMail(
      String pgId,
      String titleKey,
      String to,
      List<String> ccList,
      List<String> bccList,
      Map<String, Object> varMap,
      List<File> files)
      throws MessagingException {
    // ロケール
    Locale locale = LocaleContextHolder.getLocale();

    JavaMailSenderImpl sender = (JavaMailSenderImpl) javaMailSender;

    // 件名
    String titleName = null;
    if (StringUtils.isNotBlank(titleKey)) {
      titleName = messageSource.getMessage(titleKey, null, locale);
    }

    // メール送信設定システム情報を取得できない
    if (StringUtils.isBlank(sender.getHost())
        || sender.getPort() == 0
        || StringUtils.isBlank(sender.getUsername())
        || StringUtils.isBlank(sender.getPassword())
        || StringUtils.isBlank(sender.getProtocol())
        || StringUtils.isBlank(jhipsterProperties.getMail().getFrom())) {
      // メール送信履歴登録
      saveMailHis(titleName, to, ccList, bccList, MailSt.ERROR.getSts(), "メール送信設定システム情報が存在しない");

      return -1L;
    }

    // メール送信設定情報を取得
    Optional<MMailSetting> mailSetting = mmailSettingRepository.findByMailFunction(pgId);

    // メール送信設定情報を取得できない
    if (mailSetting.isEmpty()) {
      // メール送信履歴登録
      saveMailHis(titleName, to, ccList, bccList, MailSt.ERROR.getSts(), "メール送信設定情報が存在しない");
      return -1L;
    }

    MMailHis mailHis = null;
    // 件名
    String subjectValue = null;
    // TO
    String toValue = null;
    // CC
    String[] ccValue = null;
    // BCC
    String[] bccValue = null;
    // テキスト
    String mailText = null;

    try {
      // メールタイプ
      boolean isHtml = true;

      // メールタイプ: テキスト
      if (MAIL_TYPE_TEXT.equals(mailSetting.get().getMailType())) {
        isHtml = false;
      }

      // multipart
      boolean isMultipart = !CollectionUtils.isEmpty(files) || isHtml;

      // 添付ファイルが設定されている、または、Htmlメールの場合

      // mimeメッセージ
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      // メッセージ
      MimeMessageHelper message =
          new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());

      // 差出人
      message.setFrom(jhipsterProperties.getMail().getFrom());

      // 引数.件名が設定されている場合
      if (StringUtils.isNotBlank(titleKey)) {
        subjectValue = titleName;
      } else {
        subjectValue = mailSetting.get().getDefMailSubject();
      }
      message.setSubject(subjectValue);

      // 引数.TOが設定されている場合
      if (StringUtils.isNotBlank(to)) {
        toValue = to;
      } else {
        toValue = mailSetting.get().getDefMailTo();
      }
      message.setTo(toValue);

      // 引数.CCが設定されている場合
      if (!CollectionUtils.isEmpty(ccList)) {
        ccValue = ccList.toArray(new String[0]);
      } else {
        if (StringUtils.isNotBlank(mailSetting.get().getDefMailCc())) {
          ccValue = mailSetting.get().getDefMailCc().split("\\s*[,;]\\s*");
        }
      }
      if (ccValue != null) {
        message.setCc(ccValue);
      }

      // 引数.BCCが設定されている場合
      if (!CollectionUtils.isEmpty(bccList)) {
        bccValue = bccList.toArray(new String[0]);
      } else {
        if (StringUtils.isNotBlank(mailSetting.get().getDefMailBcc())) {
          bccValue = mailSetting.get().getDefMailBcc().split("\\s*[,;]\\s*");
        }
      }
      if (bccValue != null) {
        message.setBcc(bccValue);
      }

      // 添付ファイルが設定されている場合
      if (!CollectionUtils.isEmpty(files)) {
        for (File file : files) {
          FileSystemResource resource = new FileSystemResource(file);
          message.addAttachment(file.getName(), resource);
        }
      }

      // コンテキスト
      Context context = new Context(locale);
      // 変数を設定
      for (String key : varMap.keySet()) {
        context.setVariable(key, varMap.get(key));
      }

      // 内容を作成
      mailText =
          templateEngine.process(MAIL_TEMPLATE + mailSetting.get().getMailTemplNm(), context);
      message.setText(mailText, isHtml);

      // メール送信
      javaMailSender.send(mimeMessage);

      // メール送信履歴登録
      mailHis =
          saveMailHis(
              subjectValue,
              mailText,
              toValue,
              ccValue,
              bccValue,
              MailSt.SUCCESS.getSts(),
              DateUtils.getDbDateTime(),
              null);

      if (!CollectionUtils.isEmpty(files)) {
        // メール送信添付ファイル履歴登録
        saveMailAttachHis(files, mailHis);
      }

      return mailHis.getId();
    } catch (MessagingException e) {
      // メール送信処理が失敗の場合
      // メール送信履歴登録
      saveMailHis(
          subjectValue,
          mailText,
          toValue,
          ccValue,
          bccValue,
          MailSt.ERROR.getSts(),
          null,
          e.getMessage());
      throw e;
    } catch (TemplateInputException ex) {
      // メールテンプレートが存在しない場合
      // メール送信履歴登録
      saveMailHis(
          subjectValue,
          null,
          toValue,
          ccValue,
          bccValue,
          MailSt.ERROR.getSts(),
          null,
          "メールテンプレート（" + mailSetting.get().getMailTemplNm() + "）が存在しない");
    }

    return -1L;
  }

  /**
   * メール送信履歴登録
   *
   * @param title 件名
   * @param to TO
   * @param ccList CCリスト
   * @param bccList BCCリスト
   * @param sts メール送信ステータス
   * @param errMsg エラーメッセージ
   */
  private void saveMailHis(
      String title,
      String to,
      List<String> ccList,
      List<String> bccList,
      String sts,
      String errMsg) {
    // メール送信履歴
    MMailHis his = new MMailHis();

    // 送信TO
    his.setMailTo(to);
    // 送信CC
    if (!CollectionUtils.isEmpty(ccList)) {
      his.setMailCc(String.join(";", ccList));
    }

    // 送信BCC
    if (!CollectionUtils.isEmpty(bccList)) {
      his.setMailBcc(String.join(";", bccList));
    }

    // 送信件名
    his.setMailSubject(title);
    // メール送信ステータス
    his.setMailSt(sts);
    // エラーメッセージ
    his.setErrMsg(errMsg);

    // メール送信履歴登録
    mmailHisRepository.save(his);
  }

  /**
   * メール送信履歴登録
   *
   * @param title 件名
   * @param body 送信本文
   * @param to TO
   * @param ccList CCリスト
   * @param bccList BCCリスト
   * @param sts メール送信ステータス
   * @param sentSt 送信日時
   * @param errMsg エラーメッセージ
   */
  private MMailHis saveMailHis(
      String title,
      String body,
      String to,
      String[] ccList,
      String[] bccList,
      String sts,
      Instant sentSt,
      String errMsg) {
    // メール送信履歴
    MMailHis his = new MMailHis();

    // 送信TO
    his.setMailTo(to);
    // 送信CC
    if (ccList != null) {
      his.setMailCc(String.join(";", ccList));
    }

    // 送信BCC
    if (bccList != null) {
      his.setMailBcc(String.join(";", bccList));
    }

    // 送信件名
    his.setMailSubject(title);
    // 送信本文
    his.setMailBody(body);
    // メール送信ステータス
    his.setMailSt(sts);
    // 送信日時
    his.setMailSentTs(sentSt);
    // エラーメッセージ
    his.setErrMsg(errMsg);

    // メール送信履歴登録
    return mmailHisRepository.save(his);
  }

  /**
   * メール送信添付ファイル履歴登録
   *
   * @param files 添付ファイル
   * @param mailHis メール送信履歴
   */
  private void saveMailAttachHis(List<File> files, MMailHis mailHis) {
    List<MMailAttachHis> hisList = new ArrayList<>();

    // 引数.添付ファイルリストを繰り返し
    for (File file : files) {
      MMailAttachHis mailAttaHis = new MMailAttachHis();
      hisList.add(mailAttaHis);

      // メール送信履歴ID
      mailAttaHis.setMailHis(mailHis);
      // 添付ファイル名：ファイル対象から取得したファイル名
      mailAttaHis.setAttachFileNm(file.getName());
      // 添付ファイル保存パス：ファイル対象から取得しフルパス
      mailAttaHis.setAttachFilePath(file.getPath());
      // 添付ファイルのタイプ：ファイル対象から取得した拡張子
      mailAttaHis.setAttachFileType(FilenameUtils.getExtension(file.getName()));
      // 添付ファイルのサイズ：ファイル対象から取得したサイズ
      mailAttaHis.setAttachFileSize((int) file.length());
    }

    mmailAttachHisRepository.saveAll(hisList);
  }
}
