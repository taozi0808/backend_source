package com.daitoj.tkms.modules.apia0040.web.rest;

import com.daitoj.tkms.modules.apia0040.service.A0040Service;
import com.daitoj.tkms.modules.apia0040.service.dto.A0040ApiResult;
import com.daitoj.tkms.modules.apia0040.service.dto.A0040ReturnData;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** ダッシュボードのビジネスロジック. */
@RestController
@RequestMapping("/api/v1/notices")
public class A0040Resource {

  private static final Logger LOG = LoggerFactory.getLogger(A0040Resource.class);

  /* サービス */
  private final A0040Service a0040Service;

  /**
   * コンストラクタ.
   *
   * @param a0040Service サービス
   */
  public A0040Resource(A0040Service a0040Service) {
    this.a0040Service = a0040Service;
  }

  /**
   * ダッシュボードの初期処理.
   *
   * @param accountK  アカウント区分
   * @param loginId 申請者コード
   * @return ダッシュボード情報
   */
  @Operation(
      summary = "ダッシュボードの初期表示",
      description = "ダッシュボードの初期表示を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = A0040ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/init")
  public ResponseEntity<?> getInitialData(
      @RequestParam String accountK, @RequestParam String loginId) {

    // 初期処理
    ApiResult<A0040ReturnData> result = a0040Service.getInitInfo(accountK, loginId);

    // 成功の場合
    return ResponseEntity.ok().body(result);

  }

}
