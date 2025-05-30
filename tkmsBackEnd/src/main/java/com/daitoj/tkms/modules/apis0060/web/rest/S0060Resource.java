package com.daitoj.tkms.modules.apis0060.web.rest;

import com.daitoj.tkms.modules.apis0060.service.S0060Service;
import com.daitoj.tkms.modules.apis0060.service.dto.S0060ApiResult;
import com.daitoj.tkms.modules.apis0060.service.dto.S0060ReturnData;
import com.daitoj.tkms.modules.apis0060.service.dto.S0060S01Dto;
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
 * 承認一覧（現場経費管理）コントローラ.
 */
@RestController
@Tag(name = "S0060", description = "承認一覧（現場経費管理）のAPI")
@RequestMapping("/api/v1/apprConstrSiteExp")
public class S0060Resource {

  private static final Logger LOG = LoggerFactory.getLogger(S0060Resource.class);

  /**
   * サービス.
   */
  private final S0060Service s0060Service;

  /**
   * コンストラクタ.
   */
  public S0060Resource(S0060Service s0060Service) {
    this.s0060Service = s0060Service;
  }

  /**
   * 初期表示.
   *
   * @param inDto パラメータ
   * @return 承認一覧（現場経費管理）情報
   */
  @Operation(
      summary = "承認一覧（現場経費管理）の初期表示",
      description = "承認一覧（現場経費管理）の初期表示情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0060ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody S0060S01Dto inDto) {
    // 初期表示
    ApiResult<S0060ReturnData> result = s0060Service.getInitInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 承認一覧（現場経費管理）情報
   */
  @Operation(
      summary = "承認一覧（現場経費管理）の検索処理",
      description = "承認一覧（現場経費管理）の検索情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0060ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getConstrSiteExpInfo(@Valid @RequestBody S0060S01Dto inDto) {
    // 検索処理
    ApiResult<S0060ReturnData> result = s0060Service.getConstrSiteExpInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
