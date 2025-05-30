package com.daitoj.tkms.modules.apib0030.web.rest;

import com.daitoj.tkms.modules.apib0030.service.B0030Service;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030Dto;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030PreWorkPrintDto;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S01ApiResult;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S01ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S02ApiResult;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S02ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S03ApiResult;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S03ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S04ApiResult;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S04ReturnData;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
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
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** 案件登録コントローラ */
@RestController
@Tag(name = "B0030", description = "案件登録のAPI")
@RequestMapping("/api/v1/project")
public class B0030Resource {
  private static final Logger LOG = LoggerFactory.getLogger(B0030Resource.class);

  /** PDF拡張子 */
  private static final String PDF_EXT = ".pdf";

  /** CSV日付フォーマット */
  private static final String DATE_FORMAT = "yyyyMMddHHmmss";

  /** 日付フォーマット */
  private static final String PARAM_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  /** 案件登録サービス */
  private final B0030Service b0030Service;

  /** コンストラクタ */
  public B0030Resource(B0030Service b0030Service) {
    this.b0030Service = b0030Service;
  }

  /**
   * 選択項目取得
   *
   * @return 選択項目情報
   */
  @Operation(
      summary = "選択項目を取得",
      description = "選択項目を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = B0030S01ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/list")
  public ResponseEntity<?> getsyainkanritourokuInfo() {
    // 選択項目情報を取得
    ApiResult<B0030S01ReturnData> result = b0030Service.getSentakuKoumoku();

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 小工事選択項目取得
   *
   * @return 小工事選択項目情報
   */
  @Operation(
      summary = "小工事選択項目を取得",
      description = "小工事選択項目を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = B0030S04ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/minorWork")
  public ResponseEntity<?> getminorWork(
      @RequestParam(name = "anken_code", required = true)
          @Size(max = 9)
          @Parameter(
              name = "anken_code",
              description = "案件コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 9))
          String ankenCode,
      @RequestParam(name = "major_work_cd", required = true)
          @Size(max = 9)
          @Parameter(
              name = "major_work_cd",
              description = "大工事コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 9))
          String majorWorkCd) {
    // 小工事選択項目情報を取得
    ApiResult<B0030S04ReturnData> result = b0030Service.getMinorWork(ankenCode, majorWorkCd);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 案件登録情報取得
   *
   * @return 案件登録情報
   */
  @Operation(
      summary = "案件登録情報を取得",
      description = "案件登録情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = B0030S02ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/get")
  public ResponseEntity<?> getAnkenInfo(
      @RequestParam(name = "anken_code", required = true)
          @Size(max = 9)
          @Parameter(
              name = "anken_code",
              description = "案件コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 9))
          String ankenCode) {
    // 案件登録情報を取得
    ApiResult<B0030S02ReturnData> result = b0030Service.getAnkenInfo(ankenCode);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 案件登録情報保存
   *
   * @param inDto パラメータ
   * @return 案件ID、歴番
   */
  @Operation(
      summary = "案件登録情報保存",
      description = "案件登録情報を保存する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "変更成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = B0030S03ApiResult.class))),
        @ApiResponse(
            responseCode = "201",
            description = "新規成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = B0030S03ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping("/save")
  public ResponseEntity<?> saveAnkenInfo(
      @RequestPart("inDto") @Valid B0030Dto inDto,
      @RequestPart(value = "files", required = false) MultipartFile[] files) {

    // 案件登録情報を保存
    ApiResult<B0030S03ReturnData> result = b0030Service.saveAnkenInfo(inDto, files);

    // 処理区分が新規
    if (CommonConstants.SHORIKUBUN_SINNKI.equals(inDto.getShorikubun())) {
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 案件登録先行作業明細印刷処理
   *
   * @param inDto パラメータ
   */
  @Operation(
      summary = "案件登録先行作業明細の印刷処理",
      description = "案件登録先行作業明細のPDF情報を出力する",
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
  @PostMapping("/printPreWork")
  public ResponseEntity<?> generatePreWorkReport(@Valid @RequestBody B0030PreWorkPrintDto inDto) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PARAM_DATE_FORMAT);

    LocalDateTime dateTime = LocalDateTime.parse(inDto.getSysDate(), formatter);
    // ファイル名
    String fileName = dateTime + PDF_EXT;
    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    // pdfファイル
    ApiResult<?> result = b0030Service.exportReportPreWorkToPdf(inDto);

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
   * 案件登録印刷処理
   *
   * @param inDto パラメータ
   */
  @Operation(
      summary = "案件登録の印刷処理",
      description = "案件登録のPDF情報を出力する",
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
  @GetMapping("/print")
  public ResponseEntity<?> generateReport(
      @RequestParam(name = "anken_code", required = true)
          @Size(max = 9)
          @Parameter(
              name = "anken_code",
              description = "案件コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 9))
          String ankenCode,
      @RequestParam(name = "sys_date", required = true)
          @Parameter(
              name = "sys_date",
              description = "利用PCのシステム日付(YYYY-MM-DD HH:mm:ss)",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", example = "2025-05-09 15:23:00"))
          String sysDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PARAM_DATE_FORMAT);

    LocalDateTime dateTime = LocalDateTime.parse(sysDate, formatter);

    // ファイル名
    String fileName = dateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT)) + PDF_EXT;

    // 出力ファイル名
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    // pdfファイル
    ApiResult<?> result = b0030Service.exportReportToPdf(ankenCode, dateTime);

    // ヘッダ編集
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition.inline().filename(encodedFileName).build());

    // 出力結果
    return new ResponseEntity<>(result.getData(), headers, HttpStatus.OK);
  }
}
