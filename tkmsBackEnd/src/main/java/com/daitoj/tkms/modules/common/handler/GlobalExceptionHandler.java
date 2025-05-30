package com.daitoj.tkms.modules.common.handler;

import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.ConflictException;
import com.daitoj.tkms.modules.common.service.InvalidUserException;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** コントローラ例外処理 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /** メッセージ */
  private final MessageSource messageSource;

  /** コンストラクタ */
  public GlobalExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * システム例外処理
   *
   * @param ex SystemException
   * @param request HttpServletRequest
   * @return システムエラー情報
   */
  @ExceptionHandler(SystemException.class)
  public ResponseEntity<?> handleSystemException(SystemException ex, HttpServletRequest request) {

    // 例外処理
    ApiResult<?> response =
        ApiResult.error(
            String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
            ex.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  /**
   * パスワード例外処理
   *
   * @param ex Exception
   * @return ログインエラー情報
   */
  @ExceptionHandler({
    InternalAuthenticationServiceException.class,
    InvalidUserException.class,
    BadCredentialsException.class
  })
  public ResponseEntity<?> handleBadCredentialsException(Exception ex) {

    // 例外処理
    ApiResult<?> response =
        ApiResult.error(
            Message.MSGID_A0003,
            messageSource.getMessage(Message.MSGID_A0003, null, LocaleContextHolder.getLocale()));

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  /**
   * 排他例外処理
   *
   * @param ex ConflictException
   * @return 排他エラー情報
   */
  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<?> handleConflictException(ConflictException ex) {

    // 例外処理
    ApiResult<?> response =
        ApiResult.error(
            Message.MSGID_P00003,
            messageSource.getMessage(Message.MSGID_P00003, null, LocaleContextHolder.getLocale()));

    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  /**
   * メールの送信例外処理
   *
   * @param ex Exception
   * @return メールの送信エラー情報
   */
  @ExceptionHandler({MailSendException.class, MessagingException.class})
  public ResponseEntity<?> handleMailException(Exception ex) {

    // 例外処理
    ApiResult<?> response =
        ApiResult.error(
            Message.MSGID_A00007,
            messageSource.getMessage(Message.MSGID_A00007, null, LocaleContextHolder.getLocale()));

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  /**
   * ログインエラー情報例外処理
   *
   * @param ex Exception
   * @param request HttpServletRequest
   * @return ログインエラーエラー情報
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    BindingResult bindingResult = ex.getBindingResult();
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();

    List<ErrorResponse.ValidationError> validationErrors =
        fieldErrors.stream()
            .map(
                error ->
                    new ErrorResponse.ValidationError(error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());

    ErrorResponse response =
        new ErrorResponse(
            String.valueOf(HttpStatus.BAD_REQUEST),
            "無効なリクエストです",
            "入力値に誤りがあります",
            request.getRequestURI(),
            validationErrors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  /**
   * システム例外処理
   *
   * @param ex Exception
   * @param request HttpServletRequest
   * @return システムエラー情報
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {

    LOG.error(ex.toString(), ex);

    // 例外処理
    ApiResult<?> response =
        ApiResult.error(
            String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
            ex.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
