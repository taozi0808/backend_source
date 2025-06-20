package com.daitoj.tkms.modules.apif0010.web.rest;

import com.daitoj.tkms.modules.apif0010.service.F0010Service;
import com.daitoj.tkms.modules.apif0010.service.dto.F0010ApiResult;
import com.daitoj.tkms.modules.apif0010.service.dto.F0010ReturnData;
import com.daitoj.tkms.modules.apif0010.service.dto.F0010S01Dto;
import com.daitoj.tkms.modules.apif0010.service.dto.F0010S02Dto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 物件一覧コントローラ. */
@RestController
@Tag(name = "tkmsapi", description = "物件一覧のAPI")
@RequestMapping("/api/v1/projectSites")
public class F0010Resource {
  private static final Logger LOG = LoggerFactory.getLogger(F0010Resource.class);

  /* CSV拡張子 */
  private static final String CSV_EXT = ".csv";

  /* Excel拡張子 */
  private static final String EXCEL_EXT = ".xlsx";

  /* CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /* サービス */
  private final F0010Service f0010Service;

  /**
   * コンストラクタ.
   *
   * @param f0010Service 物件一覧サービス
   */
  public F0010Resource(F0010Service f0010Service) {
    this.f0010Service = f0010Service;
  }

  /**
   * 初期表示.
   *
   * @return 物件情報
   */
  @Operation(
      summary = "物件情報の初期表示",
      description = "物件一覧の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = F0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/init")
  public ResponseEntity<?> getInitialData(@RequestParam String belongOfficeCd) {

    // 初期表示
    ApiResult<F0010ReturnData> result = f0010Service.getInitInfo(belongOfficeCd);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 物件情報
   */
  @Operation(
      summary = "物件情報の検索処理",
      description = "物件一覧の検索情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = F0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/search")
  public ResponseEntity<?> getProjectSiteInfo(@Valid @RequestBody F0010S01Dto inDto) {

    // 検索処理
    ApiResult<F0010ReturnData> result = f0010Service.getProjectSiteInfo(inDto);

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
      summary = "物件一覧のCSV出力処理",
      description = "物件一覧のCSV情報を出力",
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
      @RequestBody F0010S02Dto inDto, HttpServletResponse response) {

    // ファイル名生成
    String fileName =
        F0010Service.APP_NAME
            + inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT))
            + CSV_EXT;

    // 日本語ファイル名対応のUTF-8エンコード
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    // CSV出力処理
    ApiResult<?> result = f0010Service.downLoadCsv(inDto, encodedFileName, response);

    // 最大件数を超えた場合
    if (result.getError() != null) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }

    // 正常応答
    return ResponseEntity.ok().body(result);
  }

//  @Operation(
//      summary = "物件一覧の印刷処理",
//      description = "物件一覧のPDF情報を出力する",
//      responses = {
//        @ApiResponse(
//            responseCode = "200",
//            description = "成功",
//            content =
//                @Content(
//                    mediaType = "application/pdf",
//                    schema = @Schema(implementation = ResponseEntity.class))),
//        @ApiResponse(
//            responseCode = "422",
//            description = "最大件数を超えた",
//            content =
//                @Content(
//                    mediaType = "application/json",
//                    schema = @Schema(implementation = ErrorInfo.class))),
//        @ApiResponse(
//            responseCode = "500",
//            description = "システムエラー",
//            content =
//                @Content(
//                    mediaType = "application/json",
//                    schema = @Schema(implementation = ErrorInfo.class)))
//      })
//  @PostMapping("/print")
//  public ResponseEntity<?> generateReport(@RequestBody F0010S02Dto inDto) {
//
//    // ファイル名
//    String fileName =
//        inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT)) + EXCEL_EXT;
//
//    // 出力ファイル名
//    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
//
//    // excelファイル作成
//    ApiResult<?> result = f0010Service.exportExcel(inDto);
//
//    // 最大件数を超えた場合
//    if (result.getError() != null) {
//      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
//    }
//
//    // HTTPレスポンスヘッダー設定
//    org.springframework.http.HttpHeaders headers = new HttpHeaders();
//    // コンテンツタイプを「application/octet-stream」に設定
//    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//    // コンテンツの表示方法をファイル名に設定
//    headers.setContentDisposition(ContentDisposition.inline().filename(encodedFileName).build());
//
//    // レスポンス生成
//    return new ResponseEntity<>(result.getData(), headers, HttpStatus.OK);
//  }

}
