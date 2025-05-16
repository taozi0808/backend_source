package com.daitoj.tkms.modules.apia0010.web.rest;

import static com.daitoj.tkms.security.SecurityUtils.AUTHORITIES_KEY;
import static com.daitoj.tkms.security.SecurityUtils.JWT_ALGORITHM;

import com.daitoj.tkms.modules.apia0010.service.A0010Service;
import com.daitoj.tkms.modules.apia0010.service.dto.A0010Dto;
import com.daitoj.tkms.modules.apia0010.service.dto.A0010S01ApiResult;
import com.daitoj.tkms.modules.apia0010.service.dto.A0010S01ReturnData;
import com.daitoj.tkms.modules.apia0010.service.dto.A0010S02ApiResult;
import com.daitoj.tkms.modules.apia0010.service.dto.A0010S02ReturnData;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.KeyConstants;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.CustomUserDetails;
import com.daitoj.tkms.modules.common.service.dto.EmpDto;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import com.daitoj.tkms.modules.common.service.dto.PositionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** ログインコントローラ */
@RestController
@Tag(name = "A0010", description = "ログインのAPI")
@RequestMapping("/api/v1/login")
public class A0010Resource {
  private static final Logger LOG = LoggerFactory.getLogger(A0010Resource.class);

  @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds:0}")
  private long tokenValidityInSeconds;

  /** サービス */
  private final A0010Service a0010Service;

  /** 認証マネージャ */
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  /** エンコード */
  private final JwtEncoder jwtEncoder;

  /** コンストラクタ */
  public A0010Resource(
      A0010Service a0010Service,
      AuthenticationManagerBuilder authenticationManagerBuilder,
      JwtEncoder jwtEncoder) {
    this.a0010Service = a0010Service;
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.jwtEncoder = jwtEncoder;
  }

  /**
   * ログイン処理
   *
   * @param inDto パラメータ
   * @return ログイン情報
   */
  @Operation(
      summary = "ログイン情報を取得",
      description = "ログイン情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = A0010S01ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/auth")
  public ResponseEntity<?> authorize(@Valid @RequestBody A0010Dto inDto) {

    // ユーザーIDとパスワードを使って認証トークンを作成
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(inDto.getUserId(), inDto.getPassword());

    // 認証マネージャで認証を実行
    Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // 認証情報をセキュリティコンテキストに設定
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 認証後のユーザー情報を取得
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    // ログイン情報取得
    ApiResult<A0010S01ReturnData> result = a0010Service.getLoginInfo(userDetails);

    if (result.getError() != null) {
      // レスポンスを返却
      return new ResponseEntity<>(result, HttpStatus.OK);
    }

    A0010S01ReturnData data = result.getData();

    // 権限
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    // 社員の場合
    if (data.getEmpInfo() != null) {
      // 役職情報
      PositionDto positionInfo = data.getEmpInfo().getPositionCd();
      if (positionInfo != null) {
        // 承認権限
        if (CommonConstants.HAS_PERMISSION.equals(positionInfo.getConfirmPerm())) {
          authorities.add(new SimpleGrantedAuthority(KeyConstants.CONFIRM_PERM));
        }
        // 削除権限
        if (CommonConstants.HAS_PERMISSION.equals(positionInfo.getDeletePerm())) {
          authorities.add(new SimpleGrantedAuthority(KeyConstants.DELETE_PERM));
        }
        // 編集権限
        if (CommonConstants.HAS_PERMISSION.equals(positionInfo.getEditPerm())) {
          authorities.add(new SimpleGrantedAuthority(KeyConstants.EDIT_PERM));
        }
        // 参照権限
        if (CommonConstants.HAS_PERMISSION.equals(positionInfo.getReferPerm())) {
          authorities.add(new SimpleGrantedAuthority(KeyConstants.REFER_PERM));
        }
        // 注文書権限
        if (CommonConstants.HAS_PERMISSION.equals(positionInfo.getPoPerm())) {
          authorities.add(new SimpleGrantedAuthority(KeyConstants.PO_PERM));
        }
      }
    }

    // ユーザ権限を設定
    userDetails.setAuthorities(authorities);

    Authentication authenticationWithPerm =
        new UsernamePasswordAuthenticationToken(
            authentication.getPrincipal(), authentication.getCredentials(), authorities);

    // 認証情報をセキュリティコンテキストに設定
    SecurityContextHolder.getContext().setAuthentication(authenticationWithPerm);

    // JWTを作成
    String jwt =
        this.createToken(
            authenticationWithPerm, inDto.getUserId(), data.getAccountKubun(), data.getEmpInfo());

    // HTTPヘッダーにJWTを設定
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(jwt);

    // ユーザー情報にJWTをセット
    data.setIdToken(jwt);

    // ユーザー情報とJWT付きのレスポンスを返却
    return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
  }

  /**
   * お知らせ情報取得
   *
   * @return お知らせ情報
   */
  @Operation(
      summary = "お知らせ情報を取得",
      description = "お知らせ情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = A0010S02ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/notices")
  public ResponseEntity<?> getNotices(
      @RequestParam(name = "pg_id", required = true)
          @Size(max = 255)
          @Parameter(
              name = "pg_id",
              description = "呼出元画面の機能ID",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 255))
          String pgId) {
    // お知らせ情報取得
    ApiResult<A0010S02ReturnData> result = a0010Service.getNoticeList(pgId);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * JWTトークンを作成するメソッド
   *
   * @param authentication 認証情報
   * @param accountKubun アカウント区分
   * @param empDto 社員情報
   * @return JWT
   */
  private String createToken(
      Authentication authentication, String loginId, String accountKubun, EmpDto empDto) {

    // 認証されたユーザーの権限
    String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

    // トークンの発行時間と有効期限を設定
    Instant now = Instant.now();
    Instant validity;
    validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);

    // JWTのクレームを作成
    JwtClaimsSet.Builder claimsBuilder =
        JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities);

    // ログインID
    claimsBuilder.claim(KeyConstants.HTTP_HEADER_LOGIN_ID, loginId);

    // アカウント区分
    claimsBuilder.claim(KeyConstants.HTTP_HEADER_ACCOUNT_K, accountKubun);

    // 社員の場合
    if (empDto != null) {
      // 役職情報
      PositionDto positionInfo = empDto.getPositionCd();
      if (positionInfo != null) {
        // 役職コード
        claimsBuilder.claim(KeyConstants.HTTP_HEADER_POSITION_CD, positionInfo.getPositionCd());
      }
    }

    // JWTのヘッダーを作成
    JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
    // JWTをエンコードしてトークンを取得
    return this.jwtEncoder
        .encode(JwtEncoderParameters.from(jwsHeader, claimsBuilder.build()))
        .getTokenValue();
  }
}
