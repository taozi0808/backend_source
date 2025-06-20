package com.daitoj.tkms.modules.apis0130.web.rest;

import com.daitoj.tkms.modules.apis0130.service.S0130Service;
import com.daitoj.tkms.modules.apis0130.service.dto.S0130ApiResult;
import com.daitoj.tkms.modules.apis0130.service.dto.S0130ReturnData;
import com.daitoj.tkms.modules.apis0130.service.dto.S0130S01Dto;
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
 * 承認一覧（会社管理）コントローラ.
 */
@RestController
@Tag(name = "S0130", description = "承認一覧（会社管理）のAPI")
@RequestMapping("/api/v1/wf-requests/emp-org-rev")
public class S0130Resource {

  private static final Logger LOG = LoggerFactory.getLogger(S0130Resource.class);

  /**
   * サービス.
   */
  private final S0130Service s0130Service;

  /**
   * コンストラクタ.
   */
  public S0130Resource(S0130Service s0130Service) {
    this.s0130Service = s0130Service;
  }

  /**
   * 初期表示.
   *
   * @param inDto パラメータ
   * @return 承認一覧（会社管理）情報
   */
  @Operation(
      summary = "承認一覧（会社管理）の初期表示",
      description = "承認一覧（会社管理）の初期表示情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0130ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody S0130S01Dto inDto) {
    // 初期表示
    ApiResult<S0130ReturnData> result = s0130Service.getInitInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 承認一覧（会社管理）情報
   */
  @Operation(
      summary = "承認一覧（会社管理）の検索処理",
      description = "承認一覧（会社管理）の検索情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0130ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getEmpInfo(@Valid @RequestBody S0130S01Dto inDto) {
    // 検索処理
    ApiResult<S0130ReturnData> result = s0130Service.getEmpInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
