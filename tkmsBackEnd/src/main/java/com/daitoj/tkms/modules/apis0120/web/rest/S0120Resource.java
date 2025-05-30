package com.daitoj.tkms.modules.apis0120.web.rest;

import com.daitoj.tkms.modules.apis0120.service.S0120Service;
import com.daitoj.tkms.modules.apis0120.service.dto.S0120ApiResult;
import com.daitoj.tkms.modules.apis0120.service.dto.S0120ReturnData;
import com.daitoj.tkms.modules.apis0120.service.dto.S0120S01Dto;
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
 * 承認一覧（顧客管理）コントローラ.
 */
@RestController
@Tag(name = "S0120", description = "承認一覧（顧客管理）のAPI")
@RequestMapping("/api/v1/apprCustomer")
public class S0120Resource {

  private static final Logger LOG = LoggerFactory.getLogger(S0120Resource.class);

  /**
   * サービス.
   */
  private final S0120Service s0120Service;

  /**
   * コンストラクタ.
   */
  public S0120Resource(S0120Service s0120Service) {
    this.s0120Service = s0120Service;
  }

  /**
   * 初期表示.
   *
   * @param inDto パラメータ
   * @return 承認一覧（顧客管理）情報
   */
  @Operation(
      summary = "承認一覧（顧客管理）の初期表示",
      description = "承認一覧（顧客管理）の初期表示情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0120ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody S0120S01Dto inDto) {
    // 初期表示
    ApiResult<S0120ReturnData> result = s0120Service.getInitInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 承認一覧（顧客管理）情報
   */
  @Operation(
      summary = "承認一覧（顧客管理）の検索処理",
      description = "承認一覧（顧客管理）の検索情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0120ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getCustomerInfo(@Valid @RequestBody S0120S01Dto inDto) {
    // 検索処理
    ApiResult<S0120ReturnData> result = s0120Service.getCustomerInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
