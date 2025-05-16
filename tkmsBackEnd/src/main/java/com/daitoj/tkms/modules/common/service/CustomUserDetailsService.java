package com.daitoj.tkms.modules.common.service;

import com.daitoj.tkms.domain.MLogin;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MLoginRepository;
import com.daitoj.tkms.modules.common.service.dto.CustomUserDetails;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/** ユーザ情報取得サービス */
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

  private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

  /** M_Login情報リポジトリ */
  private final MLoginRepository mloginRepository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** コンストラクタ */
  public CustomUserDetailsService(MLoginRepository mloginRepository, MessageSource messageSource) {
    this.mloginRepository = mloginRepository;
    this.messageSource = messageSource;
  }

  /**
   * ユーザ情報を取得
   *
   * @param loginId ログインID
   * @return ログイン情報
   * @throws UsernameNotFoundException ユーザ存在しない
   */
  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    try {
      // ログイン情報を取得する
      Optional<MLogin> loginInfo =
          mloginRepository.findLoginInfo(loginId, DateUtils.formatNow(DateUtils.DATE_FORMAT));

      // 取得件数が0件の場合
      if (loginInfo.isEmpty()) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_A0003, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        throw new InvalidUserException(Message.MSGID_A0003, msg);
      }

      // ユーザ情報設定
      CustomUserDetails userDetails =
          new CustomUserDetails(loginInfo.get().getLoginId(), loginInfo.get().getPassword());

      // アカウント区分
      userDetails.setAccountKubun(loginInfo.get().getAccountK());

      return userDetails;

    } catch (InvalidUserException ex) {
      throw ex;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }
}
