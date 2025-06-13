package com.daitoj.tkms.modules.apis0070.web.rest;

import com.daitoj.tkms.modules.apis0070.service.S0070Service;
import com.daitoj.tkms.modules.apis0070.service.dto.S0070ApiResult;
import com.daitoj.tkms.modules.apis0070.service.dto.S0070ReturnData;
import com.daitoj.tkms.modules.apis0070.service.dto.S0070S01Dto;
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
 * 承認一覧（工事予実管理）コントローラ.
 */
@RestController
@Tag(name = "S0070", description = "承認一覧（工事予実管理）のAPI")
@RequestMapping("/api/v1/wf-requests/constr-wbs")
public class S0070Resource {

  private static final Logger LOG = LoggerFactory.getLogger(S0070Resource.class);

  /**
   * サービス.
   */
  private final S0070Service s0070Service;

  /**
   * コンストラクタ.
   */
  public S0070Resource(S0070Service s0070Service) {
    this.s0070Service = s0070Service;
  }

  /**
   * 初期表示.
   *
   * @param inDto パラメータ
   * @return 承認一覧（工事予実管理）情報
   */
  @Operation(
      summary = "承認一覧（工事予実管理）の初期表示",
      description = "承認一覧（工事予実管理）の初期表示情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0070ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody S0070S01Dto inDto) {
    // 初期表示
    ApiResult<S0070ReturnData> result = s0070Service.getInitInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 承認一覧（工事予実管理）情報
   */
  @Operation(
      summary = "承認一覧（工事予実管理）の検索処理",
      description = "承認一覧（工事予実管理）の検索情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0070ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getConstrWbsInfo(@Valid @RequestBody S0070S01Dto inDto) {
    // 検索処理
    ApiResult<S0070ReturnData> result = s0070Service.getConstrWbsInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
