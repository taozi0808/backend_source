package com.daitoj.tkms.modules.apia0030.web.rest;

import com.daitoj.tkms.modules.apia0030.service.A0030Service;
import com.daitoj.tkms.modules.apia0030.service.dto.A0030ApiResult;
import com.daitoj.tkms.modules.apia0030.service.dto.A0030S01Dto;
import com.daitoj.tkms.modules.common.constants.KeyConstants;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import com.daitoj.tkms.modules.common.utils.ContextUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 個人設定コントローラ */
@RestController
@Tag(name = "A0030", description = "個人設定のAPI")
@RequestMapping("/api/v1/passwordUpdate")
public class A0030Resource {

  /** 機能ID */
  public static final String PG_ID = "Web";

  /** サービス */
  private final A0030Service a0030Service;

  /** コンストラクタ */
  public A0030Resource(A0030Service a0030Service) {
    this.a0030Service = a0030Service;
  }

  /**
   * 更新処理
   *
   * @param inDto パラメータ
   * @return 個人設定
   */
  @Operation(
      summary = "個人設定の更新処理",
      description = "ログイン情報テーブルのパスワードを更新する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = A0030ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PutMapping("/update")
  public ResponseEntity<?> updateLoginInfo(@Valid @RequestBody A0030S01Dto inDto) {
    // ログインID
    ContextUtils.setParameter(KeyConstants.HTTP_HEADER_LOGIN_ID, inDto.getLoginId());
    // 機能ID
    ContextUtils.setParameter(KeyConstants.HTTP_HEADER_PG_ID, String.join("", PG_ID, "A0030"));
    // 更新処理
    ApiResult<A0030ApiResult> result = a0030Service.updateLoginInfo(inDto);

    return ResponseEntity.ok().body(result);
  }
}
