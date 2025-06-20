package com.daitoj.tkms.modules.apij0010.web.rest;

import com.daitoj.tkms.modules.apij0010.service.J0010Service;
import com.daitoj.tkms.modules.apij0010.service.dto.J0010ApiResult;
import com.daitoj.tkms.modules.apij0010.service.dto.J0010ReturnData;
import com.daitoj.tkms.modules.apij0010.service.dto.J0010S01Dto;
import com.daitoj.tkms.modules.apij0010.service.dto.J0010S02Dto;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import com.daitoj.tkms.modules.common.utils.PageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 連携用支払データ作成（査定）コントローラ */
@RestController
@Tag(name = "J0010", description = "連携用支払データ作成（査定）のAPI")
@RequestMapping("/api/v1/t-assess-dtl")
public class J0010Resource {

  private static final Logger LOG = LoggerFactory.getLogger(J0010Resource.class);

  /** CSV拡張子 */
  private static final String CSV_EXT = ".csv";

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /** サービス */
  private final J0010Service j0010Service;

  public J0010Resource(J0010Service j0010Service) {
    this.j0010Service = j0010Service;
  }

  /**
   * 検索処理
   *
   * @param inDto パラメータ
   * @return 連携用支払データ作成
   */
  @Operation(
      summary = "連携用支払データ作成（査定）の検索処理",
      description = "査定支払情報取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = J0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/get")
  public ResponseEntity<?> getSateiShiharai(@Valid @RequestBody J0010S01Dto inDto) {
    // 検索処理
    ApiResult<J0010ReturnData> result = j0010Service.getSateiShiharai(inDto);
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * CSV出力
   *
   * @param inDto パラメータ
   * @param response response
   */
  @Operation(
      summary = "連携用支払データ作成（査定）のCSV出力処理",
      description = "連携用支払データ作成（査定）のCSV情報を出力する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/octet-stream",
                    schema = @Schema(implementation = ApiResult.class))),
        @ApiResponse(
            responseCode = "422",
            description = "最大件数を超えた",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/download")
  public ResponseEntity<?> downloadCsv(
      @Valid @RequestBody J0010S02Dto inDto, HttpServletResponse response) {
    // ファイル名
    String fileName =
        J0010Service.APP_NAME
            + inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT))
            + CSV_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
    // CSV出力
    ApiResult<?> result = j0010Service.downLoadCsv(inDto, encodedFileName, response);
     // 最大件数を超えた場合
    if (result.getError() != null) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }

    // 出力結果
    return ResponseEntity.ok().body(null);
  }
}
