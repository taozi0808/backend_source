package com.daitoj.tkms.modules.apil0010.web.rest;

import com.daitoj.tkms.modules.apil0010.service.L0010Service;
import com.daitoj.tkms.modules.apil0010.service.dto.L0010ApiResult;
import com.daitoj.tkms.modules.apil0010.service.dto.L0010ReturnData;
import com.daitoj.tkms.modules.apil0010.service.dto.L0010S01Dto;
import com.daitoj.tkms.modules.apil0010.service.dto.L0010S02Dto;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 請求一覧のビジネスロジック. */
@RestController
@RequestMapping("/api/v1/customerInvoices")
public class L0010Resource {

  private static final Logger LOG = LoggerFactory.getLogger(L0010Resource.class);

  /* CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /* CSV拡張子 */
  private static final String CSV_EXT = ".csv";

  /* PDF拡張子 */
  private static final String PDF_EXT = ".pdf";

  /* サービス */
  private final L0010Service l0010Service;

  /**
   * コンストラクタ.
   *
   * @param l0010Service 請求一覧のサービス
   */
  public L0010Resource(L0010Service l0010Service) {
    this.l0010Service = l0010Service;
  }

  /**
   * 初期表示.
   *
   * @param belongOfficeCd 所属事務所コード
   * @return API結果
   */
  @Operation(
      summary = "請求一覧の初期表示",
      description = "請求一覧の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = L0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/init")
  public ResponseEntity<?> getInitialData(
      @RequestParam(value = "belongOfficeCd") String belongOfficeCd) {

    // 初期表示
    ApiResult<L0010ReturnData> result = l0010Service.getInitInfo(belongOfficeCd);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto 検索パラメータ
   * @return 請求書一覧
   */
  @Operation(
      summary = "請求書一覧の検索処理",
      description = "請求書一覧の検索情報を取得処理",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = L0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getCustomerInvoiceInfo(@Valid @RequestBody L0010S01Dto inDto) {

    // 検索処理
    ApiResult<L0010ReturnData> result = l0010Service.getCustomerInvoiceInfo(inDto);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * CSV出力処理.
   *
   * @param inDto パラメータ
   * @param response レスポンス
   * @return 無し
   */
  @Operation(
      summary = "請求一覧のCSV出力処理",
      description = "請求一覧のCSV情報を出力",
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
      @Valid @RequestBody L0010S02Dto inDto, HttpServletResponse response) {

    // ファイル名生成
    String fileName =
        L0010Service.APP_NAME
            + inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT))
            + CSV_EXT;

    // 日本語ファイル名対応のUTF-8エンコード
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    // CSV出力処理
    ApiResult<?> result = l0010Service.downloadCsv(inDto, encodedFileName, response);

    // 最大件数を超えた場合
    if (result.getError() != null) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }

    // 正常応答
    return ResponseEntity.ok().body(result);

  }

  /**
   * 印刷処理.
   *
   * @param inDto パラメータ
   * @return 無し
   */
  @Operation(
      summary = "請求情報の印刷処理",
      description = "請求一覧のPDF情報を出力する",
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
  public ResponseEntity<?> generateReport(@Valid @RequestBody L0010S02Dto inDto) {

    // ファイル名
    String fileName =
        inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT)) + PDF_EXT;

    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    // pdfファイル作成
    ApiResult<?> result = l0010Service.exportReportToPdf(inDto);

    // 最大件数を超えた場合
    if (result.getError() != null) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }

    // HTTPレスポンスヘッダー設定
    org.springframework.http.HttpHeaders headers = new HttpHeaders();
    // コンテンツタイプをPDFに設定
    headers.setContentType(MediaType.APPLICATION_PDF);
    // コンテンツの表示方法をファイル名に設定
    headers.setContentDisposition(ContentDisposition.inline().filename(encodedFileName).build());

    // レスポンス生成
    return new ResponseEntity<>(result.getData(), headers, HttpStatus.OK);

  }

}
