package com.daitoj.tkms.modules.apiq0035.web.rest;

import com.daitoj.tkms.modules.apiq0035.service.Q0035Service;
import com.daitoj.tkms.modules.apiq0035.service.dto.Q0035ApiResult;
import com.daitoj.tkms.modules.apiq0035.service.dto.Q0035ReturnData;
import com.daitoj.tkms.modules.apiq0035.service.dto.Q0035S01Dto;
import com.daitoj.tkms.modules.apiq0035.service.dto.Q0035S02Dto;
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

/** 作業員名簿物件一覧コントローラ. */
@RestController
@Tag(name = "tkmsapi", description = "作業員名簿物件一覧のAPI")
@RequestMapping("/api/v1/tConstrSites")
public class Q0035Resource {

  private static final Logger LOG = LoggerFactory.getLogger(Q0035Resource.class);

  /** CSV拡張子. */
  private static final String CSV_EXT = ".csv";

  /** PDF拡張子. */
  private static final String PDF_EXT = ".pdf";

  /** CSV日付フォーマット. */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /** サービス. */
  private final Q0035Service q0035Service;

  /** コンストラクタ. */
  public Q0035Resource(Q0035Service q0035Service) {
    this.q0035Service = q0035Service;
  }

  /**
   * 初期表示.
   *
   * @param inDto パラメータ
   * @return 作業員名簿物件情報
   */
  @Operation(
      summary = "作業員名簿物件情報の初期表示",
      description = "作業員名簿物件情報一覧の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Q0035ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody Q0035S01Dto inDto) {
    // 初期表示
    ApiResult<Q0035ReturnData> result = q0035Service.getInitInfo(inDto);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 作業員名簿物件情報
   */
  @Operation(
      summary = "作業員名簿物件情報の検索処理",
      description = "作業員名簿物件情報一覧の検索情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Q0035ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getSateiInfo(@Valid @RequestBody Q0035S01Dto inDto) {
    // 初期表示
    ApiResult<Q0035ReturnData> result = q0035Service.getSateiInfo(inDto);

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
      summary = "作業員名簿物件情報のCSV出力処理",
      description = "作業員名簿物件情報一覧のCSV情報を出力する",
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
      @Valid @RequestBody Q0035S02Dto inDto, HttpServletResponse response) {
    // ファイル名
    String fileName =
        Q0035Service.APP_NAME
            + inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT))
            + CSV_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);

    // CSV出力
    ApiResult<?> result = q0035Service.downLoadCsv(inDto, encodedFileName, response);

    // 成功の場合
    return ResponseEntity.ok().build();
  }

  /**
   * 印刷処理.
   *
   * @param inDto パラメータ
   */
  @Operation(
      summary = "作業員名簿物件情報の印刷処理",
      description = "作業員名簿物件情報一覧のPDF情報を出力する",
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
  public ResponseEntity<?> generateReport(@Valid @RequestBody Q0035S02Dto inDto) {
    // ファイル名
    String fileName =
        inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT)) + PDF_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    // pdfファイル
    //    byte[] reportBytes = Q0035Service.exportReportToPdf(inDto);
    ApiResult<?> result = q0035Service.exportReportToPdf(inDto);

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
