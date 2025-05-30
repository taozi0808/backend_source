package com.daitoj.tkms.modules.apia0020.web.rest;

import com.daitoj.tkms.modules.apia0020.service.A0020Service;
import com.daitoj.tkms.modules.apia0020.service.dto.A0020Dto;
import com.daitoj.tkms.modules.common.constants.KeyConstants;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import com.daitoj.tkms.modules.common.utils.ContextUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** パスワード通知 */
@RestController
@Tag(name = "A0020", description = "パスワード通知のAPI")
@RequestMapping("/api/v1/reset")
public class A0020Resource {
  private static final Logger LOG = LoggerFactory.getLogger(A0020Resource.class);

  @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds:0}")
  private long tokenValidityInSeconds;

  /** 機能ID */
  public static final String PG_ID = "Web";

  // 認証マネージャ
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  // エンコード
  private final JwtEncoder jwtEncoder;

  /** サービス */
  private final A0020Service a0020Service;

  /** コンストラクタ */
  public A0020Resource(
      AuthenticationManagerBuilder authenticationManagerBuilder,
      JwtEncoder jwtEncoder,
      A0020Service a0020Service) {
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.jwtEncoder = jwtEncoder;
    this.a0020Service = a0020Service;
  }

  /**
   * パスワード通知処理
   *
   * @param inDto パラメータ
   * @return パスワード通知情報
   */
  @Operation(
      summary = "パスワード通知処理",
      description = "パスワード通知情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/auth")
  public ResponseEntity<?> getPwdSaitsuchiInfo(@Valid @RequestBody A0020Dto inDto) {
    // ログインID
    ContextUtils.setParameter(KeyConstants.HTTP_HEADER_LOGIN_ID, inDto.getUserId());
    // 機能ID
    ContextUtils.setParameter(KeyConstants.HTTP_HEADER_PG_ID, String.join("", PG_ID, "A0020"));
    // 成功の場合
    return a0020Service.getPwdSaitsuchiInfo(inDto);
  }
}
