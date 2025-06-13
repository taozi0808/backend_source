package com.daitoj.tkms.modules.apis0100.web.rest;

import com.daitoj.tkms.modules.apis0100.service.S0100Service;
import com.daitoj.tkms.modules.apis0100.service.dto.S0100ApiResult;
import com.daitoj.tkms.modules.apis0100.service.dto.S0100ReturnData;
import com.daitoj.tkms.modules.apis0100.service.dto.S0100S01Dto;
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
 * 承認一覧（下請契約台帳）コントローラ.
 */
@RestController
@Tag(name = "S0100", description = "承認一覧（下請契約台帳）のAPI")
@RequestMapping("/api/v1/wf-requests/sub-con-ledger")
public class S0100Resource {

  private static final Logger LOG = LoggerFactory.getLogger(S0100Resource.class);

  /**
   * サービス.
   */
  private final S0100Service s0100Service;

  /**
   * コンストラクタ.
   */
  public S0100Resource(S0100Service s0100Service) {
    this.s0100Service = s0100Service;
  }

  /**
   * 初期表示.
   *
   * @param inDto パラメータ
   * @return 承認一覧（下請契約台帳）情報
   */
  @Operation(
      summary = "承認一覧（下請契約台帳）の初期表示",
      description = "承認一覧（下請契約台帳）の初期表示情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0100ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody S0100S01Dto inDto) {
    // 初期表示
    ApiResult<S0100ReturnData> result = s0100Service.getInitInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 承認一覧（下請契約台帳）情報
   */
  @Operation(
      summary = "承認一覧（下請契約台帳）の検索処理",
      description = "承認一覧（下請契約台帳）の検索情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0100ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getWorkerInfo(@Valid @RequestBody S0100S01Dto inDto) {
    // 検索処理
    ApiResult<S0100ReturnData> result = s0100Service.getSubConLedgerInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
