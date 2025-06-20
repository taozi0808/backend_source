package com.daitoj.tkms.modules.apic0030.web.rest;

import com.daitoj.tkms.modules.apic0030.service.C0030Service;
import com.daitoj.tkms.modules.apic0030.service.dto.*;
import com.daitoj.tkms.modules.common.service.NumberService;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/** 概算一覧コントローラ */
@RestController
@Tag(name = "C0030", description = "概算作成のAPI")
@RequestMapping("/api/v1/roughInner")
public class C0030Resource {

  private static final Logger LOG = LoggerFactory.getLogger(C0030Resource.class);

  /** PDF拡張子 */
  private static final String PDF_EXT = ".pdf";

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /** サービス */
  private final C0030Service c0030Service;

  /** 採番サービス */
  private final NumberService numberRuleService;

  /** コンストラクタ */
  public C0030Resource(C0030Service c0030Service, NumberService numberRuleService) {
    this.c0030Service = c0030Service;
    this.numberRuleService = numberRuleService;
  }

  /**
   * 初期表示
   *
   * @param roughEstCd 概算コード
   * @return 概算作成の概算情報
   */
  @Operation(
      summary = "概算作成の初期表示",
      description = "概算作成の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = C0030S01ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/get")
  public ResponseEntity<?> getInitialData(
      @RequestParam(name = "rough_est_cd", required = true)
          @Size(max = 12, min = 12)
          @Parameter(
              name = "rough_est_cd",
              description = "概算コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 12, minLength = 12))
          String roughEstCd) {
    // 初期表示
    ApiResult<C0030S01ReturnData> result = c0030Service.getInitInfo(roughEstCd);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 保存処理
   *
   * @param inDto パラメータ
   * @return 概算情報
   */
  @Operation(
      summary = "概算作成の保存処理",
      description = "概算作成の情報を保存する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "変更成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = C0030S01ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PutMapping("/rough/save")
  public ResponseEntity<?> saveRoughInfo(@Valid @RequestBody C0030S01Dto inDto) {
    // 概算情報を保存
    ApiResult<C0030S01ReturnData> result = c0030Service.saveRoughInfo(inDto);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 概算情報を削除
   *
   * @param roughEstCd 概算コード
   * @param roughEstCd 歴番
   */
  @Operation(
      summary = "概算作成の削除処理",
      description = "概算作成の情報を削除する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "削除成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @DeleteMapping("/rough/delete")
  public ResponseEntity<?> deleteRoughInfo(
      @RequestParam(name = "rough_est_cd", required = true)
          @Size(max = 12)
          @Parameter(
              name = "rough_est_cd",
              description = "概算コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 12))
          String roughEstCd,
      @RequestParam(name = "his_no", required = true)
          @Size(max = 2)
          @Parameter(
              name = "his_no",
              description = "歴番",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 2))
          String hisNo) {

    // 概算情報を削除
    ApiResult<?> result = c0030Service.deleteRoughInfo(roughEstCd, hisNo);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 単価情報取得
   *
   * @param regionCd 地域区分
   * @param constrSiteK 現場区分
   */
  @Operation(
      summary = "概算作成の削除処理",
      description = "概算作成の情報を削除する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "削除成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/rough/getmprice")
  public ResponseEntity<?> getMPriceInfo(
      @RequestParam(name = "region_cd", required = true)
          @Size(max = 3)
          @Parameter(
              name = "region_cd",
              description = "地域区分",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 3))
          String regionCd,
      @RequestParam(name = "constr_site_k", required = true)
          @Size(max = 1)
          @Parameter(
              name = "constr_site_k",
              description = "現場区分",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 1))
          String constrSiteK) {

    // 単価情報取得
    ApiResult<?> result = c0030Service.getMPriceInfo(regionCd, constrSiteK);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 概算出来高S作成API
   *
   * <p>inDto パラメータ
   */
  @Operation(
      summary = "概算作成の出来高シミュレーション起動処理",
      description = "出来高シミュレーションの情報を保存する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "保存成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/rough/savetevsim")
  public ResponseEntity<?> createGaisanDekidakaSData(@Valid @RequestBody C0030S04Dto inDto) {

    // 単価情報取得
    ApiResult<?> result = c0030Service.createGaisanDekidakaSData(inDto);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 概算見積依頼情報取得API
   *
   * <p>inDto パラメータ
   */
  @Operation(
      summary = "概算作成の見積依頼情報取得A処理",
      description = "概算見積依頼情の情報を保存する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/rough/getGaisanMitsumoriIraiData")
  public ResponseEntity<?> getGaisanMitsumoriIraiData(
      @RequestParam(name = "rough_est_cd", required = true)
          @Size(max = 12, min = 12)
          @Parameter(
              name = "rough_est_cd",
              description = "概算コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 12, minLength = 12))
          String roughEstCd) {

    // 単価情報取得
    ApiResult<?> result = c0030Service.getGaisanMitsumoriIraiData(roughEstCd);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 印刷処理
   *
   * @param roughEstCd 概算コード
   */
  @Operation(
      summary = "概算作成の印刷処理",
      description = "概算作成のPDF情報を出力する",
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
  @GetMapping("/rough/print")
  public ResponseEntity<?> generateReport(
      @RequestParam(name = "rough_est_cd", required = true)
          @Size(max = 12, min = 12)
          @Parameter(
              name = "rough_est_cd",
              description = "概算コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 12, minLength = 12))
          String roughEstCd)
      throws Exception {
    // ファイル名
    String fileName =
        DateUtils.formatDateTime(numberRuleService.getSystemDate(), CSV_DATE_FORMAT) + PDF_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    // pdfファイル
    ApiResult<?> result =
        c0030Service.exportReportToPdf(
            roughEstCd,
            DateUtils.formatDateTime(numberRuleService.getSystemDate(), CSV_DATE_FORMAT));
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

  /**
   * 初期表示
   *
   * @return 大工事/小工事情報
   */
  @Operation(
      summary = "大工事/小工事情報の初期表示",
      description = "概算作成の初期表示大工事/小工事情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = C0030S01ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/get-major-minor")
  public ResponseEntity<?> getMajorMinorReport() {
    // 初期表示
    ApiResult<C0030S03ReturnData> result = c0030Service.getMajorMinorReport();

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
