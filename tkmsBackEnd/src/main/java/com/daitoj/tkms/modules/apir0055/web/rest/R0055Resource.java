package com.daitoj.tkms.modules.apir0055.web.rest;

import com.daitoj.tkms.modules.apir0055.service.R0055Service;
import com.daitoj.tkms.modules.apir0055.service.dto.R0055ApiResult;
import com.daitoj.tkms.modules.apir0055.service.dto.R0055ReturnData;
import com.daitoj.tkms.modules.apir0055.service.dto.R0055S01Dto;
import com.daitoj.tkms.modules.apir0055.service.dto.R0055S02Dto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 顧客一覧のビジネスロジック. */
@RestController
@Tag(name = "R0055", description = "顧客一覧API")
@RequestMapping("/api/v1/customers")
public class R0055Resource {
  private static final Logger LOG = LoggerFactory.getLogger(R0055Resource.class);

  /* サービス */
  private final R0055Service r0055Service;

  /* CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /* CSV拡張子 */
  private static final String CSV_EXT = ".csv";

  /* pdf拡張子 */
  private static final String PDF_EXT = ".pdf";

  /**
   * コンストラクタ.
   *
   * @param r0055Service サービス
   */
  public R0055Resource(R0055Service r0055Service) {
    this.r0055Service = r0055Service;
  }

  /**
   * 初期処理.
   *
   * @return 顧客情報
   */
  @Operation(
      summary = "顧客情報の初期表示",
      description = "顧客一覧の初期表示を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0055ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/init")
  public ResponseEntity<?> getInitialData() {
    // 初期表示
    ApiResult<R0055ReturnData> result = r0055Service.getInitInfo();
    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 顧客情報一覧
   */
  @Operation(
      summary = "顧客情報一覧の検索処理",
      description = "顧客情報一覧の検索情報を取得処理",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0055ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getCustomerData(@RequestBody R0055S01Dto inDto) {

    // 検索処理
    ApiResult<R0055ReturnData> result = r0055Service.getCustomerInfo(inDto);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * csv出力.
   *
   * @param inDto パラメータ
   * @param response response
   * @return なし
   */
  @Operation(
      summary = "顧客情報のCSV出力処理",
      description = "顧客情報一覧のCSV情報を出力する",
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
      @RequestBody R0055S02Dto inDto, HttpServletResponse response) {

    // ファイル名
    String fileName =
        R0055Service.APP_NAME
            + inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT))
            + CSV_EXT;

    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    // CSV出力
    ApiResult<?> result = r0055Service.downLoadCsv(inDto, encodedFileName, response);
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
   * @return なし
   */
  @Operation(
      summary = "顧客情報一覧の印刷処理",
      description = "顧客情報一覧のPDF情報を出力する",
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
  public ResponseEntity<?> generateReport(@Valid @RequestBody R0055S02Dto inDto) {
    // ファイル名
    String fileName =
        inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT)) + PDF_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    // pdfファイル
    ApiResult<?> result = r0055Service.exportReportToPdf(inDto);

    // 最大件数を超えた場合
    if (result.getError() != null) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }

    org.springframework.http.HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition.inline().filename(encodedFileName).build());

    // 出力結果
    return new ResponseEntity<>(result.getData(), headers, HttpStatus.OK);
  }
}
