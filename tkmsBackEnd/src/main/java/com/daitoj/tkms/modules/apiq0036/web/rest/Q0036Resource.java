package com.daitoj.tkms.modules.apiq0036.web.rest;

import com.daitoj.tkms.modules.apiq0036.service.Q0036Service;
import com.daitoj.tkms.modules.apiq0036.service.dto.*;
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
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 作業員名簿業者一覧コントローラ */
@RestController
@Tag(name = "Q0036", description = "作業員名簿業者一覧のAPI")
@RequestMapping("/api/v1/project-sites")
public class Q0036Resource {

  private static final Logger LOG = LoggerFactory.getLogger(Q0036Resource.class);

  /** CSV拡張子 */
  private static final String CSV_EXT = ".csv";

  /** PDF拡張子 */
  private static final String PDF_EXT = ".pdf";

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /** サービス */
  private final Q0036Service q0036Service;

  /** コンストラクタ */
  public Q0036Resource(Q0036Service q0036Service) {
    this.q0036Service = q0036Service;
  }

  /**
   * 初期表示
   *
   * @param inDto パラメータ
   * @return 作業員名簿業者情報
   */
  @Operation(
      summary = "作業員名簿業者情報の初期表示",
      description = "作業員名簿業者一覧の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Q0036ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/init")
  public ResponseEntity<?> getInitialData(@Valid @RequestBody Q0036S01Dto inDto) {
    // ページパラメータ
    Pageable pageable = PageUtils.createPageable(inDto.getPage(), inDto.getSize());

    // 初期表示
    ApiResult<Q0036ReturnData> result = q0036Service.getInitInfo(inDto, pageable);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理
   *
   * @param inDto パラメータ
   * @return 作業員名簿業者情報
   */
  @Operation(
      summary = "作業員名簿業者情報の検索処理",
      description = "作業員名簿業者一覧の検索情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Q0036ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getSagyouinInfo(@Valid @RequestBody Q0036S01Dto inDto) {
    // ページパラメータ
    Pageable pageable = PageUtils.createPageable(inDto.getPage(), inDto.getSize());

    // 検索処理
    ApiResult<Q0036ReturnData> result = q0036Service.getSagyouinInfo(inDto, pageable);

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
      summary = "作業員名簿業者情報のCSV出力処理",
      description = "作業員名簿業者一覧のCSV情報を出力する",
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
      @Valid @RequestBody Q0036S02Dto inDto, HttpServletResponse response) {
    // ファイル名
    String fileName =
        Q0036Service.APP_NAME
            + inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT))
            + CSV_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    // CSV出力
    ApiResult<?> result = q0036Service.downLoadCsv(inDto, encodedFileName, response);

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
      summary = "作業員名簿業者情報の印刷処理",
      description = "作業員名簿業者一覧のPDF情報を出力する",
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
  public ResponseEntity<?> generateReport(@Valid @RequestBody Q0036S02Dto inDto) {
    // ファイル名
    String fileName =
        inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT)) + PDF_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    // pdfファイル
    ApiResult<?> result = q0036Service.exportReportToPdf(inDto);

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
