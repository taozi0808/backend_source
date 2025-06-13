package com.daitoj.tkms.modules.apir0040.web.rest;

import com.daitoj.tkms.modules.apir0040.service.R0040Service;
import com.daitoj.tkms.modules.apir0040.service.dto.R0040ApiResult;
import com.daitoj.tkms.modules.apir0040.service.dto.R0040ReturnData;
import com.daitoj.tkms.modules.apir0040.service.dto.R0040S01Dto;
import com.daitoj.tkms.modules.apir0040.service.dto.R0040S02Dto;
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

/** 社員一覧ビジネスロジック. */
@RestController
@Tag(name = "tkmsapi", description = "社員一覧のAPI")
@RequestMapping("/api/v1/emps")
public class R0040Resource {
  private static final Logger LOG = LoggerFactory.getLogger(R0040Resource.class);

  /* CSV拡張子 */
  private static final String CSV_EXT = ".csv";

  /* PDF拡張子 */
  private static final String PDF_EXT = ".pdf";

  /* CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /* サービス */
  private final R0040Service r0040Service;

  /**
   * コンストラクタ.
   *
   * @param r0040Service 社員一覧サービス
   */
  public R0040Resource(R0040Service r0040Service) {
    this.r0040Service = r0040Service;
  }

  /**
   * 初期表示.
   *
   * @return 社員情報
   */
  @Operation(
      summary = "社員情報の初期表示",
      description = "社員一覧の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0040ApiResult.class))),
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
    ApiResult<R0040ReturnData> result = r0040Service.getInitInfo();

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 社員情報
   */
  @Operation(
      summary = "社員情報の検索処理",
      description = "社員一覧の検索情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0040ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getDetailedEstInfo(@Valid @RequestBody R0040S01Dto inDto) {

    // 検索処理
    ApiResult<R0040ReturnData> result = r0040Service.getEmpInfo(inDto);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * CSV出力.
   *
   * @param inDto パラメータ
   * @param response レスポンス
   */
  @Operation(
      summary = "社員情報のCSV出力処理",
      description = "社員一覧のCSV情報を出力する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
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
  @PostMapping("/download")
  public ResponseEntity<?> downloadCsv(
      @Valid @RequestBody R0040S02Dto inDto, HttpServletResponse response) {

    // ファイル名
    String fileName =
        R0040Service.APP_NAME
            + inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT))
            + CSV_EXT;

    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    // CSV出力
    ApiResult<?> result = r0040Service.downLoadCsv(inDto, encodedFileName, response);
    if (result.getError() != null) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }

    // 出力結果
    return ResponseEntity.ok().build();
  }

  /**
   * 印刷処理.
   *
   * @param inDto パラメータ
   */
  @Operation(
      summary = "社員情報の印刷処理",
      description = "社員一覧のPDF情報を出力する",
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
  public ResponseEntity<?> generateReport(@Valid @RequestBody R0040S02Dto inDto) {

    // ファイル名
    String fileName =
        inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT)) + PDF_EXT;

    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    // pdfファイル
    ApiResult<?> result = r0040Service.exportReportToPdf(inDto);

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
