package com.daitoj.tkms.modules.common.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

/** メールサービス */
@Service
public class CustomMailService {
  private static final Logger LOG = LoggerFactory.getLogger(CustomMailService.class);

  private final JHipsterProperties jhipsterProperties;

  private final JavaMailSender javaMailSender;

  private final MessageSource messageSource;

  private final SpringTemplateEngine templateEngine;

  /** コンストラクタ */
  public CustomMailService(
      JHipsterProperties jhipsterProperties,
      JavaMailSender javaMailSender,
      MessageSource messageSource,
      SpringTemplateEngine templateEngine) {
    this.jhipsterProperties = jhipsterProperties;
    this.javaMailSender = javaMailSender;
    this.messageSource = messageSource;
    this.templateEngine = templateEngine;
  }

  /**
   * テンプレートより、メールを送信する
   *
   * @param templateName テンプレート名
   * @param to 宛先
   * @param titleKey タイトル
   * @param varMap 変数
   * @param isMultipart マルチパート形式
   * @param isHtml Html形式
   * @throws MessagingException 例外
   */
  public void sendEmailFromTemplate(
      String templateName,
      String to,
      String titleKey,
      Map<String, Object> varMap,
      boolean isMultipart,
      boolean isHtml)
      throws MessagingException {

    // ロケール
    Locale locale = LocaleContextHolder.getLocale();
    // コンテキスト
    Context context = new Context(locale);
    // 変数を設定
    for (String key : varMap.keySet()) {
      context.setVariable(key, varMap.get(key));
    }

    // 内容を作成
    String content = templateEngine.process(templateName, context);
    // タイトル
    String subject = messageSource.getMessage(titleKey, null, locale);

    // メールを送信する
    sendEmail(to, subject, content, isMultipart, isHtml);
  }

  /**
   * メールを送信する
   *
   * @param to 宛先
   * @param subject タイトル
   * @param content 内容
   * @param isMultipart マルチパート形式
   * @param isHtml Html形式
   * @throws MessagingException 例外
   */
  public void sendEmail(
      String to, String subject, String content, boolean isMultipart, boolean isHtml)
      throws MessagingException {
    // mimeメッセージ
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    // メッセージ
    MimeMessageHelper message =
        new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
    // 宛先
    message.setTo(to);
    // 差出人
    message.setFrom(jhipsterProperties.getMail().getFrom());
    // タイトル
    message.setSubject(subject);
    // 内容
    message.setText(content, isHtml);

    // メールを送信する
    javaMailSender.send(mimeMessage);
  }
}
