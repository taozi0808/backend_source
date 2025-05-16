package com.daitoj.tkms.modules.common.interceptor;

import com.daitoj.tkms.domain.MMenuItem;
import com.daitoj.tkms.domain.MProgram;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.KeyConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MProgramRepository;
import com.daitoj.tkms.modules.common.service.MenuItemService;
import com.daitoj.tkms.modules.common.service.PositionService;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.ContextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/** リクエストパラメータチエックインターセプター */
@Component
public class PermissionInterceptor implements HandlerInterceptor {
  private static final Logger LOG = LoggerFactory.getLogger(PermissionInterceptor.class);

  /** 役職サービス */
  private final PositionService positionService;

  /** メニュー項目情報サービス */
  private final MenuItemService menuItemService;

  /** 機能情報サービス */
  private final MProgramRepository programRepository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** fasterxml.jacksonのObjectMapper */
  private final ObjectMapper objectMapper;

  /** コントローラ名 */
  public static final String RESOURCE_NAME = "Resource";

  /** 機能ID */
  public static final String PG_ID = "Web";

  /** コンストラクタ */
  @Autowired
  public PermissionInterceptor(
      PositionService positionService,
      MenuItemService menuItemService,
      MProgramRepository programRepository,
      MessageSource messageSource,
      ObjectMapper objectMapper) {
    this.positionService = positionService;
    this.menuItemService = menuItemService;
    this.programRepository = programRepository;
    this.messageSource = messageSource;
    this.objectMapper = objectMapper;
  }

  /**
   * HTTPリクエストが処理される前に、認証トークンと権限チェックを実施
   *
   * @param request リクエスト
   * @param response レスポンス
   * @param handler ハンドラ
   */
  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    try {
      if (handler instanceof HandlerMethod) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // コントローラ
        Class<?> controllerClass = handlerMethod.getBeanType();

        // コントローラ確認
        if (!controllerClass.getSimpleName().endsWith(RESOURCE_NAME)) {
          LOG.error("controllerName:{}", controllerClass.getName());

          response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

          return false;
        }

        // 機能ID
        String pgId = request.getHeader(KeyConstants.HTTP_HEADER_PG_ID);
        // コントローラ名
        String controllerName = controllerClass.getSimpleName().replace(RESOURCE_NAME, "");

        // 機能ID確認
        if (pgId != null && !String.join("", PG_ID, controllerName).equalsIgnoreCase(pgId)) {
          // エラー結果設定
          setErrorResult(response);

          return false;
        }

        // Authorization ヘッダーがない場合
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
          LOG.warn(
              messageSource.getMessage(Message.MSGID_A0003, null, LocaleContextHolder.getLocale()));

          response.setStatus(HttpStatus.UNAUTHORIZED.value());

          return false;
        }

        // JWT トークン
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // ログイン情報
        Map<String, Object> claims = jwt.getClaims();

        // ログイン情報が空または null の場合
        if (claims == null) {
          LOG.warn(
              messageSource.getMessage(Message.MSGID_A0003, null, LocaleContextHolder.getLocale()));

          response.setStatus(HttpStatus.UNAUTHORIZED.value());

          return false;
        }

        // ログインID
        String loginId = (String) claims.get(KeyConstants.HTTP_HEADER_LOGIN_ID);
        // アカウント区分
        String accountK = (String) claims.get(KeyConstants.HTTP_HEADER_ACCOUNT_K);
        // メニューID
        String menuId = request.getHeader(KeyConstants.HTTP_HEADER_MENU_ID);

        // 必要な情報が欠けている場合
        if (StringUtils.isBlank(loginId) || StringUtils.isBlank(accountK)) {
          LOG.warn(
              messageSource.getMessage(Message.MSGID_A0003, null, LocaleContextHolder.getLocale()));

          response.setStatus(HttpStatus.UNAUTHORIZED.value());

          return false;
        }

        if (!StringUtils.isBlank(menuId) && StringUtils.isNumeric(menuId)) {
          // ログインIDとアカウント区分でメニュー項目情報を取得
          List<MMenuItem> menuItems = menuItemService.getMenuItem(loginId, accountK);

          // メニュー項目情報が存在しない場合
          if (CollectionUtils.isEmpty(menuItems)) {
            // エラー結果設定
            setErrorResult(response);

            return false;
          }

          // メニューIDチェック
          Optional<MMenuItem> menuItem =
              menuItems.stream()
                  .filter(menu -> Integer.parseInt(menuId) == menu.getId())
                  .findFirst();

          // 一致するメニューがない場合
          if (menuItem.isEmpty()) {
            // エラー結果設定
            setErrorResult(response);

            return false;
          }
        }

        if (!StringUtils.isBlank(pgId)) {
          // 機能IDを取得
          Optional<MProgram> pg = programRepository.findById(pgId);

          // 一致する機能IDがない場合
          if (pg.isEmpty()) {
            // エラー結果設定
            setErrorResult(response);

            return false;
          }

          // アカウント区分が"1（社員）の場合
          if (CommonConstants.ACCOUNT_K_EMP.equals(accountK)) {
            // 役職コード
            String positionCode = (String) claims.get(KeyConstants.HTTP_HEADER_POSITION_CD);

            // 役職コードが存在しない場合
            if (StringUtils.isBlank(positionCode)) {
              LOG.warn(
                  messageSource.getMessage(
                      Message.MSGID_A0003, null, LocaleContextHolder.getLocale()));

              response.setStatus(HttpStatus.UNAUTHORIZED.value());

              return false;
            }

            // 承認権限チェック要否フラグ
            String confirmCheckFlg = pg.get().getConfirmPermChkFlg();
            // 削除権限チェック要否フラグ
            String deleteCheckFlg = pg.get().getDeletePermChkFlg();
            // 編集権限チェック要否フラグ
            String editCheckFlg = pg.get().getEditPermChkFlg();
            // 参照権限チェック要否フラグ
            String referCheckFlg = pg.get().getReferPermChkFlg();
            // 注文書権限チェック要否フラグ
            String poCheckFlg = pg.get().getPoPermChkFlg();

            // チェックしない場合
            if (CommonConstants.PERM_CHECK_FLG.equals(confirmCheckFlg)
                || CommonConstants.PERM_CHECK_FLG.equals(deleteCheckFlg)
                || CommonConstants.PERM_CHECK_FLG.equals(editCheckFlg)
                || CommonConstants.PERM_CHECK_FLG.equals(referCheckFlg)
                || CommonConstants.PERM_CHECK_FLG.equals(poCheckFlg)) {
              // 役職の権限が有効かどうかをチェック
              boolean permValid =
                  positionService.isPermissionValid(
                      positionCode,
                      confirmCheckFlg,
                      deleteCheckFlg,
                      editCheckFlg,
                      referCheckFlg,
                      poCheckFlg);

              // 権限が無効な場合
              if (!permValid) {
                // エラー結果設定
                setErrorResult(response);

                return false;
              }
            }
          }
        }

        // ログインID
        ContextUtils.setParameter(KeyConstants.HTTP_HEADER_LOGIN_ID, loginId);
        // 機能ID
        ContextUtils.setParameter(
            KeyConstants.HTTP_HEADER_PG_ID, String.join("", PG_ID, controllerName));
      }

      return true;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      // 内部サーバーエラー
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

      return false;
    }
  }

  /**
   * エラー結果設定
   *
   * @param response response
   */
  private void setErrorResult(HttpServletResponse response) throws IOException {
    String msg =
        messageSource.getMessage(Message.MSGID_K00006, null, LocaleContextHolder.getLocale());
    LOG.warn(msg);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    response.setStatus(HttpStatus.FORBIDDEN.value());
    response
        .getWriter()
        .write(objectMapper.writeValueAsString(ApiResult.error(Message.MSGID_K00006, msg)));
  }
}
