package com.daitoj.tkms.modules.apic0010.web.rest;

import com.daitoj.tkms.modules.apic0010.service.C0010Service;
import com.daitoj.tkms.modules.apic0010.service.dto.C0010ApiResult;
import com.daitoj.tkms.modules.apic0010.service.dto.C0010ReturnData;
import com.daitoj.tkms.modules.apic0010.service.dto.C0010S01Dto;
import com.daitoj.tkms.modules.apic0010.service.dto.C0010S02Dto;
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

/** 概算一覧コントローラ */
@RestController
@Tag(name = "C0010", description = "概算一覧のAPI")
@RequestMapping("/api/v1/rough")
public class C0010Resource {

  private static final Logger LOG = LoggerFactory.getLogger(C0010Resource.class);

  /** CSV拡張子 */
  private static final String CSV_EXT = ".csv";

  /** PDF拡張子 */
  private static final String PDF_EXT = ".pdf";

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /** サービス */
  private final C0010Service c0010Service;

  /** コンストラクタ */
  public C0010Resource(C0010Service c0010Service) {
    this.c0010Service = c0010Service;
  }

  /**
   * 初期表示
   *
   * @param inDto パラメータ
   * @return 概算情報
   */
  @Operation(
      summary = "概算情報の初期表示",
      description = "概算一覧の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = C0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/projects/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody C0010S01Dto inDto) {
    // ページパラメータ
    Pageable pageable = PageUtils.createPageable(inDto.getPage(), inDto.getSize());
    // 初期表示
    ApiResult<C0010ReturnData> result = c0010Service.getInitInfo(inDto, pageable);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理
   *
   * @param inDto パラメータ
   * @return 概算情報
   */
  @Operation(
      summary = "概算情報の検索処理",
      description = "概算一覧の検索情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = C0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/projects/search")
  public ResponseEntity<?> getAnkenInfo(@Valid @RequestBody C0010S01Dto inDto) {
    // ページパラメータ
    Pageable pageable = PageUtils.createPageable(inDto.getPage(), inDto.getSize());
    // 検索処理
    ApiResult<C0010ReturnData> result = c0010Service.getAnkenInfo(inDto, pageable);

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
      summary = "概算情報のCSV出力処理",
      description = "概算一覧のCSV情報を出力する",
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
  @PostMapping("/projects/download")
  public ResponseEntity<?> downloadCsv(
      @Valid @RequestBody C0010S02Dto inDto, HttpServletResponse response) {
    // ファイル名
    String fileName =
        C0010Service.APP_NAME
            + inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT))
            + CSV_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
    // CSV出力
    ApiResult<?> result = c0010Service.downLoadCsv(inDto, encodedFileName, response);
    // 最大件数を超えた場合
    if (result.getError() != null) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }

    // 出力結果
    return ResponseEntity.ok().body(result);
  }

  /**
   * 印刷処理
   *
   * @param inDto パラメータ
   */
  @Operation(
      summary = "概算情報の印刷処理",
      description = "概算一覧のPDF情報を出力する",
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
  @PostMapping("/projects/print")
  public ResponseEntity<?> generateReport(@Valid @RequestBody C0010S02Dto inDto) throws Exception {
    // ファイル名
    String fileName =
        inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT)) + PDF_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    // pdfファイル
    ApiResult<?> result = c0010Service.exportReportToPdf(inDto);
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
