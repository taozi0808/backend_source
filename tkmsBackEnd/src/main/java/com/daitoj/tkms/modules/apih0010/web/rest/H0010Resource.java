package com.daitoj.tkms.modules.apih0010.web.rest;

import com.daitoj.tkms.modules.apih0010.service.H0010Service;
import com.daitoj.tkms.modules.apih0010.service.dto.H0010ApiResult;
import com.daitoj.tkms.modules.apih0010.service.dto.H0010ReturnData;
import com.daitoj.tkms.modules.apih0010.service.dto.H0010S01Dto;
import com.daitoj.tkms.modules.apih0010.service.dto.H0010S02Dto;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
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
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 査定一覧コントローラ. */
@RestController
@Tag(name = "tkmsapi", description = "査定一覧のAPI")
@RequestMapping("/api/v1/sateis")
public class H0010Resource {

  private static final Logger LOG = LoggerFactory.getLogger(H0010Resource.class);

  /** CSV拡張子. */
  private static final String CSV_EXT = ".csv";

  /** PDF拡張子. */
  private static final String PDF_EXT = ".pdf";

  /** CSV日付フォーマット. */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /** サービス. */
  private final H0010Service h0010Service;

  /** コンストラクタ. */
  public H0010Resource(H0010Service h0010Service) {
    this.h0010Service = h0010Service;
  }

  /**
   * 初期表示.
   *
   * @param inDto パラメータ
   * @return 査定情報
   */
  @Operation(
      summary = "査定情報の初期表示",
      description = "査定一覧の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = H0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody H0010S01Dto inDto) {
    // 初期表示
    ApiResult<H0010ReturnData> result = h0010Service.getInitInfo(inDto);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 査定情報
   */
  @Operation(
      summary = "査定情報の検索処理",
      description = "査定一覧の検索情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = H0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getSateiInfo(@Valid @RequestBody H0010S01Dto inDto) {
    // 初期表示
    ApiResult<H0010ReturnData> result = h0010Service.getassessInfo(inDto);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * CSV出力.
   *
   * @param inDto パラメータ
   * @param response response
   */
  @Operation(
      summary = "査定情報のCSV出力処理",
      description = "査定一覧のCSV情報を出力する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/download")
  public ResponseEntity<Void> downloadCsv(
      @Valid @RequestBody H0010S02Dto inDto, HttpServletResponse response) {
    // ファイル名
    String fileName =
        H0010Service.APP_NAME
            + inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT))
            + CSV_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);

    // CSV出力
    ApiResult<?> result = h0010Service.downLoadCsv(inDto, encodedFileName, response);

    // 成功の場合
    return ResponseEntity.ok().build();
  }

  /**
   * 印刷処理.
   *
   * @param inDto パラメータ
   */
  @Operation(
      summary = "査定情報の印刷処理",
      description = "査定一覧のPDF情報を出力する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/print")
  public ResponseEntity<?> generateReport(@Valid @RequestBody H0010S02Dto inDto) {
    // ファイル名
    String fileName =
        inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT)) + PDF_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    // pdfファイル
    ApiResult<?> result = h0010Service.exportReportToPdf(inDto);

    // 最大件数を超えた場合
    if (result.getError() != null) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition.inline().filename(encodedFileName).build());

    // 出力結果
    return new ResponseEntity<>(result.getData(), headers, HttpStatus.OK);
  }
}
