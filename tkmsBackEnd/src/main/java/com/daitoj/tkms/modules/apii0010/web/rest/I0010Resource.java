package com.daitoj.tkms.modules.apii0010.web.rest;

import com.daitoj.tkms.modules.apii0010.service.I0010Service;
import com.daitoj.tkms.modules.apii0010.service.dto.I0010ApiResult;
import com.daitoj.tkms.modules.apii0010.service.dto.I0010ReturnData;
import com.daitoj.tkms.modules.apii0010.service.dto.I0010S01Dto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 出来高シュミレーション一覧コントローラ */
@RestController
@Tag(name = "I0010", description = "出来高シュミレーション一覧のAPI")
@RequestMapping("/api/v1/evsims")
public class I0010Resource {
  private static final Logger LOG = LoggerFactory.getLogger(I0010Resource.class);

  /** サービス */
  private final I0010Service i0010Service;

  /** コンストラクタ */
  public I0010Resource(I0010Service i0010Service) {
    this.i0010Service = i0010Service;
  }

  /**
   * 初期表示
   *
   * @param inDto パラメータ
   * @return 出来高シュミレーション情報
   */
  @Operation(
      summary = "出来高シュミレーション情報の初期表示",
      description = "出来高シュミレーション一覧の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = I0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody I0010S01Dto inDto) {
    // ページパラメータ
    Pageable pageable = PageUtils.createPageable(inDto.getPage(), inDto.getSize());

    // 初期表示
    ApiResult<I0010ReturnData> result = i0010Service.getInitInfo(inDto, pageable);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理
   *
   * @param inDto パラメータ
   * @return 出来高シュミレーション情報
   */
  @Operation(
      summary = "出来高シュミレーション情報の検索処理",
      description = "出来高シュミレーション一覧の検索情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = I0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getDekidakaShimyurēsyonInfo(@Valid @RequestBody I0010S01Dto inDto) {
    // ページパラメータ
    Pageable pageable = PageUtils.createPageable(inDto.getPage(), inDto.getSize());

    // 検索処理
    ApiResult<I0010ReturnData> result = i0010Service.getDekidakaShimyurēsyonInfo(inDto, pageable);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
