package com.daitoj.tkms.modules.apia0020.service;

import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.domain.MLogin;
import com.daitoj.tkms.domain.MVendor;
import com.daitoj.tkms.domain.MWorker;
import com.daitoj.tkms.modules.apia0020.repository.A0020S01Repository;
import com.daitoj.tkms.modules.apia0020.service.dto.A0020Dto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.KeyConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.constants.TemplateName;
import com.daitoj.tkms.modules.common.repository.MEmpRepository;
import com.daitoj.tkms.modules.common.repository.MLoginRepository;
import com.daitoj.tkms.modules.common.repository.MVendorRepository;
import com.daitoj.tkms.modules.common.service.CustomMailService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.PasswordUtils;
import jakarta.mail.MessagingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/** ログインビジネスロジック */
@Service
public class A0020Service {

  private static final Logger LOG = LoggerFactory.getLogger(A0020Service.class);

  /** パスワード再通知ポジトリ */
  private final A0020S01Repository a0020Repository;

  /** 従業員情報リポジトリ */
  private final MEmpRepository mempRepository;

  /** 業者情報リポジトリ */
  private final MVendorRepository vendorRepository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** M_Login情報リポジトリ */
  private final MLoginRepository mloginRepository;

  private final CustomMailService customMailService;

  private final Map<String, Object> varMap = new HashMap<>();

  /** コンストラクタ */
  public A0020Service(
      A0020S01Repository a0020Repository,
      CustomMailService customMailService,
      MLoginRepository mloginRepository,
      MEmpRepository mempRepository,
      MVendorRepository vendorRepository,
      MessageSource messageSource) {
    this.a0020Repository = a0020Repository;
    this.customMailService = customMailService;
    this.mloginRepository = mloginRepository;
    this.mempRepository = mempRepository;
    this.vendorRepository = vendorRepository;
    this.messageSource = messageSource;
  }

  /**
   * ログイン情報を取得する
   *
   * @param inDto ログイン情報取得パラメータ
   * @return ログイン情報
   */
  public ResponseEntity<?> getPwdSaitsuchiInfo(A0020Dto inDto) {
    try {
      // 社員情報
      MEmp empInfo = null;
      // 業者情報
      MVendor vendorHdr = null;
      // 作業員
      MWorker mworker = null;
      // 宛先
      String to = null;

      // ログイン情報を取得する
      Optional<MLogin> loginInfo =
          mloginRepository.findLoginInfo(
              inDto.getUserId(),
              LocalDate.now().format(DateTimeFormatter.ofPattern(DateUtils.DATE_FORMAT)));

      // 取得件数が0件、パスワードが不一致の場合
      if (loginInfo.isEmpty()) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_A00005, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        ApiResult.error(Message.MSGID_A00005, msg);
        // 結果情報
        return ResponseEntity.status(201).body(ApiResult.error(Message.MSGID_A00005, msg));
      }

      // アカウント区分が"1"（従業員）の場合
      if (CommonConstants.HAS_PERMISSION.equals(loginInfo.get().getAccountK())) {
        // 従業員情報
        empInfo = mempRepository.findBylogin_LoginId(inDto.getUserId());
        // 社員情報が取得できない
        if (empInfo == null) {
          // メッセージ
          String msg =
              messageSource.getMessage(Message.MSGID_A00006, null, LocaleContextHolder.getLocale());

          LOG.warn(msg);

          // 結果情報
          return ResponseEntity.status(201).body(ApiResult.error(Message.MSGID_A00006, msg));
        }
        to = empInfo.getMailAddress();
        // アカウント区分が"2"（協力業者）の場合
      } else if (CommonConstants.ACCOUNT_K_VENDOR.equals(loginInfo.get().getAccountK())) {
        vendorHdr = vendorRepository.findByLogin_loginId(inDto.getUserId());

        // 業者情報が取得できない
        if (vendorHdr == null) {
          // メッセージ
          String msg =
              messageSource.getMessage(Message.MSGID_A00006, null, LocaleContextHolder.getLocale());

          LOG.warn(msg);

          // 結果情報
          return ResponseEntity.status(201).body(ApiResult.error(Message.MSGID_A00006, msg));
        }
        to = vendorHdr.getMailAddress();
        // アカウント区分が"3"（協力業者従業員）の場合
      } else if (CommonConstants.ACCOUNT_K_VENDOR_WORKER.equals(loginInfo.get().getAccountK())) {
        // 作業員
        mworker = a0020Repository.findMLoginInfo(inDto.getUserId());

        // 作業員情報が取得できない
        if (mworker == null) {
          // メッセージ
          String msg =
              messageSource.getMessage(Message.MSGID_A00006, null, LocaleContextHolder.getLocale());

          LOG.warn(msg);

          // 結果情報
          return ResponseEntity.status(201).body(ApiResult.error(Message.MSGID_A00006, msg));
        }
        to = mworker.getMailAddress();
      }

      BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
      String password = PasswordUtils.createPassword();
      // パスワード暗号化処理
      String newPassword = bcryptPasswordEncoder.encode(password);

      // ログイン情報の更新処理
      loginInfo.get().setPassword(newPassword);
      mloginRepository.save(loginInfo.get());

      varMap.put(KeyConstants.A0020_VAR_PASSWORD, password);
        Long mailResult =
            customMailService.sendMail(
                TemplateName.A0020_TEMPLATE,
                Message.A0020_MAIL_TITLE,
                to,
                null,
                null,
                varMap,
                null);

        if (mailResult == -1L) {
            throw new SystemException(
                messageSource.getMessage(
                    Message.MSGID_A00007, null, LocaleContextHolder.getLocale()));
        }
      // メッセージ
      String msg =
          messageSource.getMessage(Message.MSGID_A00008, null, LocaleContextHolder.getLocale());

      LOG.warn(msg);
      return ResponseEntity.status(200).body(ApiResult.error(Message.MSGID_A00008, msg));
    } catch (MessagingException | MailSendException ex) {
      // メッセージ
      String msg =
          messageSource.getMessage(Message.MSGID_A00007, null, LocaleContextHolder.getLocale());

      LOG.warn(msg);

      return ResponseEntity.status(400).body(ApiResult.error(Message.MSGID_A00007, msg));

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }
}
