package com.daitoj.tkms.modules.apig0010.web.rest;

import com.daitoj.tkms.modules.apig0010.service.G0010Service;
import com.daitoj.tkms.modules.apig0010.service.dto.G0010ApiResult;
import com.daitoj.tkms.modules.apig0010.service.dto.G0010ReturnData;
import com.daitoj.tkms.modules.apig0010.service.dto.G0010S01Dto;
import com.daitoj.tkms.modules.apig0010.service.dto.G0010S02Dto;
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

/** 実行予算一覧コントローラ. */
@RestController
@Tag(name = "G0010", description = "実行予算一覧のAPI")
@RequestMapping("/api/v1/execBgtHdrs")
public class G0010Resource {

  private static final Logger LOG = LoggerFactory.getLogger(G0010Resource.class);

  /** CSV拡張子. */
  private static final String CSV_EXT = ".csv";

  /** PDF拡張子. */
  private static final String PDF_EXT = ".pdf";

  /** CSV日付フォーマット. */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /** サービス. */
  private final G0010Service g0010Service;

  /** コンストラクタ. */
  public G0010Resource(G0010Service g0010Service) {
    this.g0010Service = g0010Service;
  }

  /**
   * 初期表示.
   *
   * @param inDto パラメータ
   * @return 実行予算情報
   */
  @Operation(
      summary = "実行予算情報の初期表示",
      description = "実行予算一覧の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = G0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody G0010S01Dto inDto) {

    // 初期表示
    ApiResult<G0010ReturnData> result = g0010Service.getInitInfo(inDto);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 実行予算情報
   */
  @Operation(
      summary = "実行予算情報の検索処理",
      description = "実行予算一覧の検索情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = G0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getJikkouyosanInfo(@Valid @RequestBody G0010S01Dto inDto) {

    // 検索処理
    ApiResult<G0010ReturnData> result = g0010Service.getJikkouyosanInfo(inDto);

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
      summary = "実行予算情報のCSV出力処理",
      description = "実行予算一覧のCSV情報を出力する",
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
      @Valid @RequestBody G0010S02Dto inDto, HttpServletResponse response) {
    // ファイル名
    String fileName =
        G0010Service.APP_NAME
            + inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT))
            + CSV_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    // CSV出力
    ApiResult<?> result = g0010Service.downLoadCsv(inDto, encodedFileName, response);

    // 最大件数を超えた場合
    if (result.getError() != null) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }

    // 出力結果
    return ResponseEntity.ok().body(result);
  }

  /**
   * 印刷処理.
   *
   * @param inDto パラメータ
   */
  @Operation(
      summary = "実行予算情報の印刷処理",
      description = "実行予算一覧のPDF情報を出力する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/pdf",
                    schema = @Schema(implementation = ResponseEntity.class))),
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
  @PostMapping("/print")
  public ResponseEntity<?> generateReport(@Valid @RequestBody G0010S02Dto inDto) {
    // ファイル名
    String fileName =
        inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT)) + PDF_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    // pdfファイル
    ApiResult<?> result = g0010Service.exportReportToPdf(inDto);

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
