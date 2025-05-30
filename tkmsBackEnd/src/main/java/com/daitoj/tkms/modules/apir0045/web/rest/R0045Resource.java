package com.daitoj.tkms.modules.apir0045.web.rest;

import com.daitoj.tkms.modules.apir0045.service.R0045Service;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045Dto;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S01ApiResult;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S01ReturnData;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S02ApiResult;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S02ReturnData;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S03ApiResult;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S03ReturnData;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S04ApiResult;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S04ReturnData;
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
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** 社員登録コントローラ */
@RestController
@Tag(name = "R0045", description = "社員登録のAPI")
@RequestMapping("/api/v1/emp")
public class R0045Resource {
  private static final Logger LOG = LoggerFactory.getLogger(R0045Resource.class);

  /** 社員登録サービス */
  private final R0045Service r0045Service;

  /** 日付フォーマット */
  private static final String DATE_FORMAT = "yyyyMMddHHmmss";

  /** 日付フォーマット */
  private static final String PARAM_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  /** PDF拡張子 */
  private static final String PDF_EXT = ".pdf";

  /** コンストラクタ */
  public R0045Resource(R0045Service r0045Service) {
    this.r0045Service = r0045Service;
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
                    schema = @Schema(implementation = R0045S01ApiResult.class))),
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
    ApiResult<R0045S01ReturnData> result = r0045Service.getSentakuKoumoku();

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 社員登録情報取得
   *
   * @return 社員登録情報
   */
  @Operation(
      summary = "社員登録情報を取得",
      description = "社員登録情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0045S02ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/get")
  public ResponseEntity<?> getsyainkanritourokuInfo(
      @RequestParam(name = "emp_cd", required = true)
          @Size(max = 6)
          @Parameter(
              name = "emp_cd",
              description = "社員コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 6))
          String empCd) {
    // 社員登録情報を取得
    ApiResult<R0045S02ReturnData> result = r0045Service.getsyainkanritourokuInfo(empCd);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 社員管理登録情報保存
   *
   * @param inDto パラメータ
   * @return 社員コード
   */
  @Operation(
      summary = "社員管理登録情報保存",
      description = "社員管理登録情報を保存する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "変更成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResult.class))),
        @ApiResponse(
            responseCode = "201",
            description = "新規成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0045S03ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> savesyainkanritourokuInfo(
      @RequestPart("inDto") @Valid R0045Dto inDto,
      @RequestPart(value = "files", required = false) MultipartFile[] files)
      throws MessagingException {
    // 社員登録情報を保存
    ApiResult<R0045S03ReturnData> result = r0045Service.savesyainkanritourokuInfo(inDto, files);

    // 処理区分が新規
    if (CommonConstants.SHORIKUBUN_SINNKI.equals(inDto.getShorikubun())) {
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * パスワード通知
   *
   * @param loginId ログインID
   * @param mailAddress メールアドレス
   * @return エラー情報
   */
  @Operation(
      summary = "パスワードを通知",
      description = "パスワードを通知する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
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
  @PutMapping("/sendMail")
  public ResponseEntity<?> syainkanritourokupasswordalert(
      @RequestParam(name = "login_id", required = true)
          @Size(max = 255)
          @Parameter(
              name = "login_id",
              description = "ログインID",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string"))
          String loginId,
      @RequestParam(name = "emp_nm", required = true)
          @Size(max = 255)
          @Parameter(
              name = "emp_nm",
              description = "氏名",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string"))
          String shimei,
      @RequestParam(name = "mail_address", required = true)
          @Email
          @Parameter(
              name = "mail_address",
              description = "メールアドレス",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string"))
          String mailAddress) throws MessagingException {

    // パスワード通知
    ApiResult<?> result = r0045Service.syainkanritourokupasswordalert(loginId, shimei, mailAddress);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 社員情報を削除
   *
   * @param empCd 社員コード
   */
  @Operation(
      summary = "社員管理登録情報削除",
      description = "社員管理登録情報を削除する",
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
  @DeleteMapping("/delete")
  public ResponseEntity<?> deletesyainkanritourokuInfo(
      @RequestParam(name = "emp_cd", required = true)
          @Size(max = 6)
          @Parameter(
              name = "emp_cd",
              description = "社員コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 6))
          String empCd) {

    // 社員登録情報を削除
    ApiResult<?> result = r0045Service.deletesyainkanritourokuInfo(empCd);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 社員異動情報取得
   *
   * @return 社員異動情報
   */
  @Operation(
      summary = "社員異動情報を取得",
      description = "社員異動情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0045S04ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/transfer/get")
  public ResponseEntity<?> getEmpTransferHdr(
      @RequestParam(name = "emp_id", required = true)
          @Digits(integer = Integer.MAX_VALUE, fraction = 0)
          @Parameter(
              name = "emp_id",
              description = "社員ID",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "long"))
          Long empId,
      @RequestParam(name = "start_dt", required = true)
          @Size(max = 8)
          @Parameter(
              name = "start_dt",
              description = "適用開始日",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 6))
          String effectiveStartDt) {
    // 社員登録情報を取得
    ApiResult<R0045S04ReturnData> result = r0045Service.getEmpTransferHdr(empId, effectiveStartDt);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  @Operation(
      summary = "社員登録の印刷処理",
      description = "社員登録のPDF情報を出力する",
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
      @RequestParam(name = "emp_cd", required = true)
          @Size(max = 6)
          @Parameter(
              name = "emp_cd",
              description = "社員コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 6))
          String empCd,
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
    ApiResult<?> result = r0045Service.exportReportToPdf(empCd, dateTime);

    // ヘッダ編集
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition.inline().filename(encodedFileName).build());

    // 出力結果
    return new ResponseEntity<>(result.getData(), headers, HttpStatus.OK);
  }
}
