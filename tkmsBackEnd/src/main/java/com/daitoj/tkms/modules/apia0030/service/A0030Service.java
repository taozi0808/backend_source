package com.daitoj.tkms.modules.apia0030.service;

import com.daitoj.tkms.domain.MLogin;
import com.daitoj.tkms.modules.apia0030.repository.A0030Repository;
import com.daitoj.tkms.modules.apia0030.service.dto.A0030ApiResult;
import com.daitoj.tkms.modules.apia0030.service.dto.A0030S01Dto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 個人設定ビジネスロジック */
@Service
@Transactional
public class A0030Service {

  private static final Logger LOG = LoggerFactory.getLogger(A0030Service.class);

  /** 個人設定リポジトリ */
  private final A0030Repository a0030Repository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** コンストラクタ */
  public A0030Service(A0030Repository a0030Repository, MessageSource messageSource) {
    this.a0030Repository = a0030Repository;
    this.messageSource = messageSource;
  }

  /**
   * 更新処理
   *
   * @param inDto 個人設定取得パラメータ
   * @return 個人設定取得結果
   */
  public ApiResult<A0030ApiResult> updateLoginInfo(A0030S01Dto inDto) {
    try {

      BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();

      // ログイン情報を取得
      MLogin mlogin =
          a0030Repository.findByLoginIdAndDelFlg(
              inDto.getLoginId(), CommonConstants.DELETE_FLAG_VALID);

      // メッセージ
      String msg =
          messageSource.getMessage(Message.MSGID_A00009, null, LocaleContextHolder.getLocale());

      // 取得件数が０件だった場合
      if (mlogin != null) {

        boolean matches = bcryptPasswordEncoder.matches(inDto.getPassword(), mlogin.getPassword());

        if (!matches) {

          LOG.warn(msg);

          // 結果情報
          return ApiResult.error(Message.MSGID_A00009, msg);
        }

      } else {

        LOG.warn(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_A00009, msg);
      }

      // パスワード暗号化処理
      String newPassword = bcryptPasswordEncoder.encode(inDto.getNewPassword());

      // ログイン情報の更新処理
      mlogin.setPassword(newPassword);
      a0030Repository.save(mlogin);

      A0030ApiResult a0030ApiResult = new A0030ApiResult();

      // メッセージ
      String successMsg =
          messageSource.getMessage(Message.MSGID_K00003, null, LocaleContextHolder.getLocale());

      LOG.warn(successMsg);
      a0030ApiResult.setMessage(successMsg);

      return ApiResult.success(a0030ApiResult);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_K00011, null, LocaleContextHolder.getLocale()));
    }
  }
}
