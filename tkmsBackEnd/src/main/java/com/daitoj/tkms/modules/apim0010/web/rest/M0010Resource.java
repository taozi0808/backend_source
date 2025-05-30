package com.daitoj.tkms.modules.apim0010.web.rest;

import com.daitoj.tkms.modules.apim0010.service.M0010Service;
import com.daitoj.tkms.modules.apim0010.service.dto.M0010ApiResult;
import com.daitoj.tkms.modules.apim0010.service.dto.M0010ReturnData;
import com.daitoj.tkms.modules.apim0010.service.dto.M0010S01Dto;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import com.daitoj.tkms.modules.common.utils.PageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 現場経費一覧コントローラ */
@RestController
@Tag(name = "M0010", description = "現場経費一覧のAPI")
@RequestMapping("/api/v1/constrSiteExp")
public class M0010Resource {

  private static final Logger LOG = LoggerFactory.getLogger(M0010Resource.class);

  /** サービス */
  private final M0010Service m0010Service;

  /** コンストラクタ */
  public M0010Resource(M0010Service m0010Service) {
    this.m0010Service = m0010Service;
  }

  /**
   * 初期表示
   *
   * @param inDto パラメータ
   * @return 現場経費情報
   */
  @Operation(
      summary = "現場経費情報の初期表示",
      description = "現場経費一覧の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = M0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody M0010S01Dto inDto) {
    // ページパラメータ
    Pageable pageable = PageUtils.createPageable(inDto.getPage(), inDto.getSize());
    // 初期表示
    ApiResult<M0010ReturnData> result = m0010Service.getInitInfo(inDto, pageable);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理
   *
   * @param inDto パラメータ
   * @return 現場経費情報
   */
  @Operation(
      summary = "現場経費情報の検索処理",
      description = "現場経費一覧の検索情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = M0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getGenbakeihiInfo(@Valid @RequestBody M0010S01Dto inDto) {
    // ページパラメータ
    Pageable pageable = PageUtils.createPageable(inDto.getPage(), inDto.getSize());
    // 検索処理
    ApiResult<M0010ReturnData> result = m0010Service.getGenbakeihiInfo(inDto, pageable);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
