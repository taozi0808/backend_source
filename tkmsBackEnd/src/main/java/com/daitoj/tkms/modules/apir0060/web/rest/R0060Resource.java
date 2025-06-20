package com.daitoj.tkms.modules.apir0060.web.rest;

import com.daitoj.tkms.modules.apir0045.service.dto.R0045Dto;
import com.daitoj.tkms.modules.apir0060.service.R0060Service;
import com.daitoj.tkms.modules.apir0060.service.dto.CustomerBankBankBanchInfoDto;
import com.daitoj.tkms.modules.apir0060.service.dto.R0060S01ApiResult;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.service.NumberService;
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
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** 顧客情報登録コントローラ */
@RestController
@Tag(name = "R0060", description = "顧客情報登録のAPI")
@RequestMapping("/api/v1/customer")
public class R0060Resource {

  private static final Logger LOG = LoggerFactory.getLogger(R0060Resource.class);

  /** PDF拡張子 */
  private static final String PDF_EXT = ".pdf";

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /** サービス */
  private final R0060Service r0060Service;

  /** 採番サービス */
  private final NumberService numberRuleService;

  /** コンストラクタ */
  public R0060Resource(R0060Service r0060Service, NumberService numberRuleService) {
    this.r0060Service = r0060Service;
    this.numberRuleService = numberRuleService;
  }

  /**
   * 初期表示
   *
   * @return 選択項目の選択肢を取得
   */
  @Operation(
      summary = "選択項目の選択肢を取得の初期表示",
      description = "選択項目の選択肢を取得の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0060S01ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/getbank")
  public ResponseEntity<?> getbank() {
    // 初期表示
    ApiResult<?> result = r0060Service.getInitInfo();

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 初期表示
   *
   * @return 顧客情報を取得
   */
  @Operation(
      summary = "顧客情報取得の初期表示",
      description = "顧客情報取得の初期表示情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0060S01ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/get")
  public ResponseEntity<?> getKokyaku(
      @RequestParam(name = "customer_cd", required = true)
          @Size(max = 6, min = 6)
          @Parameter(
              name = "customer_cd",
              description = "顧客コード",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string", maxLength = 12, minLength = 12))
          String customerCd) {
    // 初期表示
    ApiResult<?> result = r0060Service.getKokyaku(customerCd);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 顧客管理登録情報保存
   *
   * @param inDto パラメータ
   * @return 顧客コード
   */
  @Operation(
      summary = "顧客管理登録情報保存",
      description = "顧客管理登録情報を保存する",
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
                    schema = @Schema(implementation = R0060S01ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping(value = "/save")
  public ResponseEntity<?> saveKokyaku(@Valid @RequestBody CustomerBankBankBanchInfoDto inDto) {
    // 社員登録情報を保存
    ApiResult<?> result = r0060Service.saveKokyaku(inDto);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }
}
