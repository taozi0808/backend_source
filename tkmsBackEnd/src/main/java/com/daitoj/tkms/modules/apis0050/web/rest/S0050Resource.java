package com.daitoj.tkms.modules.apis0050.web.rest;

import com.daitoj.tkms.modules.apis0050.service.S0050Service;
import com.daitoj.tkms.modules.apis0050.service.dto.S0050ApiResult;
import com.daitoj.tkms.modules.apis0050.service.dto.S0050ReturnData;
import com.daitoj.tkms.modules.apis0050.service.dto.S0050S01Dto;
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
 * 承認一覧（査定管理）コントローラ.
 */
@RestController
@Tag(name = "S0050", description = "承認一覧（査定管理）のAPI")
@RequestMapping("/api/v1/apprAssess")
public class S0050Resource {

  private static final Logger LOG = LoggerFactory.getLogger(S0050Resource.class);

  /**
   * サービス.
   */
  private final S0050Service s0050Service;

  /**
   * コンストラクタ.
   */
  public S0050Resource(S0050Service s0050Service) {
    this.s0050Service = s0050Service;
  }

  /**
   * 初期表示.
   *
   * @param inDto パラメータ
   * @return 承認一覧（査定管理）情報
   */
  @Operation(
      summary = "承認一覧（査定管理）の初期表示",
      description = "承認一覧（査定管理）の初期表示情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0050ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody S0050S01Dto inDto) {
    // 初期表示
    ApiResult<S0050ReturnData> result = s0050Service.getInitInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 承認一覧（査定管理）情報
   */
  @Operation(
      summary = "承認一覧（査定管理）の検索処理",
      description = "承認一覧（査定管理）の検索情報を取得する",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "成功",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = S0050ApiResult.class))),
          @ApiResponse(
              responseCode = "500",
              description = "システムエラー",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getProjectSiteInfo(@Valid @RequestBody S0050S01Dto inDto) {
    // 検索処理
    ApiResult<S0050ReturnData> result = s0050Service.getAssessInfo(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
