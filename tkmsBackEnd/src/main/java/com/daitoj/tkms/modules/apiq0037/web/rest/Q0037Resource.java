package com.daitoj.tkms.modules.apiq0037.web.rest;

import com.daitoj.tkms.modules.apin0030.service.dto.N0030S02ApiResult;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S02ReturnData;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S03Dto;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S04ApiResult;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S04Dto;
import com.daitoj.tkms.modules.apiq0037.service.Q0037Service;
import com.daitoj.tkms.modules.apiq0037.service.dto.Q0037S01ApiResult;
import com.daitoj.tkms.modules.apiq0037.service.dto.Q0037S01ReturnData;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
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

/** 作業員名簿登録. */
@RestController
@Tag(name = "Q0037", description = "作業員名簿登録情報取得API")
@RequestMapping("/api/v1/work-list")
public class Q0037Resource {
  private static final Logger LOG = LoggerFactory.getLogger(Q0037Resource.class);

  /** PDF拡張子. */
  private static final String PDF_EXT = ".pdf";

  /** CSV日付フォーマット. */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /** 作業員名簿登録サービス. */
  private final Q0037Service q0037Service;

  /** コンストラクタ. */
  public Q0037Resource(Q0037Service q0037Service) {
    this.q0037Service = q0037Service;
  }

  /**
   * 選択項目取得.
   *
   * @return 選択項目情報
   */
  @Operation(
      summary = "作業員名簿登録選択項目を取得",
      description = "作業員名簿登録選択項目を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Q0037S01ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/list")
  public ResponseEntity<?> getSagyouinMeiboSK(
      @RequestParam(name = "parent_partner_vendor_cd", required = true)
          @Size(max = 9)
          @Parameter(
              name = "parent_partner_vendor_cd",
              description = "上位業者コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 9))
          String parentPartnerVendorCd) {
    // 選択項目情報を取得
    ApiResult<Q0037S01ReturnData> result = q0037Service.getSagyouinMeiboSK(parentPartnerVendorCd);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
  // 以下未実装-----------------------------------------------------------------
  /**
   * 工事予実入力情報取得.
   *
   * @param constrSiteCd 現場コード
   * @return 工事予実入力情報
   */
  @Operation(
      summary = "工事予実入力情報取得",
      description = "工事予実入力情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = N0030S02ApiResult.class))),
        @ApiResponse(
            responseCode = "201",
            description = "工事予実情報が存在しませんでした",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = N0030S02ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/get")
  public ResponseEntity<?> getKoujiYojitsuInfo(
      @RequestParam(name = "constr_site_cd", required = true)
          @Size(max = 12)
          @Parameter(
              name = "constr_site_cd",
              description = "現場コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 12))
          String constrSiteCd) {
    // 工事予実入力情報を取得
    ApiResult<N0030S02ReturnData> result = q0037Service.getKoujiYojitsuInfo(constrSiteCd);

    // 成功の場合
    return result == null
        ? ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResult.success(new N0030S02ReturnData()))
        : ResponseEntity.ok().body(result);
  }

  /**
   * 印刷処理.
   *
   * @param inDto パラメータ
   */
  @Operation(
      summary = "工事予実の印刷処理",
      description = "工事予実のPDF情報を出力する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/pdf",
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
  public ResponseEntity<?> generateReport(@Valid @RequestBody N0030S03Dto inDto) {
    // ファイル名
    String fileName =
        inDto.getSysDate().format(DateTimeFormatter.ofPattern(CSV_DATE_FORMAT)) + PDF_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    // pdfファイル
    ApiResult<?> result = q0037Service.exportReportToPdf(inDto);

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
   * 工事予実入力情報保存.
   *
   * @param inDto パラメータ
   * @return 工事予実情報
   */
  @Operation(
      summary = "工事予実入力情報保存",
      description = "工事予実入力情報を保存する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "保存成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = N0030S04ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/save")
  public ResponseEntity<?> saveKoujiYojitsuInfo(@RequestBody @Valid N0030S04Dto inDto) {

    // 工事予実情報を保存
    return ResponseEntity.ok().body(q0037Service.saveKoujiYojitsuInfo(inDto));
  }
}
