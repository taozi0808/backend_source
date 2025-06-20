package com.daitoj.tkms.modules.apis0140.web.rest;

import com.daitoj.tkms.modules.apis0140.service.S0140Service;
import com.daitoj.tkms.modules.apis0140.service.dto.S0140ApiResult;
import com.daitoj.tkms.modules.apis0140.service.dto.S0140ReturnData;
import com.daitoj.tkms.modules.apis0140.service.dto.S0140S01Dto;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 承認一覧（自社情報）コントローラ.
 */
@RestController
@Tag(name = "S0140", description = "承認一覧（自社情報）のAPI")
@RequestMapping("/api/v1/wf-requests/partner-vendor")
public class S0140Resource {

  private static final Logger LOG = LoggerFactory.getLogger(S0140Resource.class);

  /**
   * サービス.
   */
  private final S0140Service s0140Service;

  /**
   * コンストラクタ.
   */
  public S0140Resource(S0140Service s0140Service) {
    this.s0140Service = s0140Service;
  }

  /**
   * 初期表示.
   *
   * @param inDto パラメータ
   * @return 承認一覧（自社情報）情報
   */
  @Operation(
      summary = "承認一覧（自社情報）の初期表示",
      description = "承認一覧（自社情報）の初期表示情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0140ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody S0140S01Dto inDto) {
    // 初期表示
    ApiResult<S0140ReturnData> result = s0140Service.getInitInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 承認一覧（自社情報）情報
   */
  @Operation(
      summary = "承認一覧（自社情報）の検索処理",
      description = "承認一覧（自社情報）の検索情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0140ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getCustomerInfo(@Valid @RequestBody S0140S01Dto inDto) {
    // 検索処理
    ApiResult<S0140ReturnData> result = s0140Service.getVendorInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
