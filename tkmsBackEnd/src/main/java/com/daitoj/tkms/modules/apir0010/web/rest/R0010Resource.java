package com.daitoj.tkms.modules.apir0010.web.rest;

import com.daitoj.tkms.modules.apir0010.service.R0010Service;
import com.daitoj.tkms.modules.apir0010.service.dto.*;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import com.daitoj.tkms.modules.common.utils.PageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** 権限情報コントローラ */
@RestController
@Tag(name = "R0010", description = "権限情報のAPI")
@RequestMapping("/api/v1/morg")
public class R0010Resource {

  private static final Logger LOG = LoggerFactory.getLogger(R0010Resource.class);

  /** CSV拡張子 */
  private static final String CSV_EXT = ".csv";

  /** PDF拡張子 */
  private static final String PDF_EXT = ".pdf";

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyyMMddHHmmss";

  /** サービス */
  private final R0010Service r0010Service;

  /** コンストラクタ */
  public R0010Resource(R0010Service r0010Service) {
    this.r0010Service = r0010Service;
  }

  /**
   * 初期表示
   *
   * @param effectiveStartDt 適用開始日
   * @return 権限情報
   */
  @Operation(
      summary = "選択項目の選択肢を取得",
      description = "選択項目の選択肢を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0010ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/init")
  public ResponseEntity<?> getKengenSentakuKoumoku(
      @RequestParam(name = "effective_start_dt", required = false)
          @Parameter(
              name = "effective_start_dt",
              description = "適用開始日",
              required = false,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string"))
          String effectiveStartDt) {
    // 初期表示
    ApiResult<R0010ReturnData> result = r0010Service.getKengenSentakuKoumoku(effectiveStartDt);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

  /**
   * 初期表示
   *
   * @param effectiveStartDt 適用開始日
   * @return 権限情報
   */
  @Operation(
      summary = "権限情報を取得",
      description = "権限情報を取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0020ApiResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/get")
  public ResponseEntity<?> getkengenInfo(
      @RequestParam(name = "effective_start_dt", required = true)
          @Parameter(
              name = "effective_start_dt",
              description = "適用開始日",
              required = true,
              in = ParameterIn.QUERY,
              schema = @Schema(type = "string"))
          String effectiveStartDt) {
    // 初期表示
    ApiResult<R0020ReturnData> result = r0010Service.getkengenInfo(effectiveStartDt);

    // 成功の場合
    return ResponseEntity.ok().body(result);
  }

    /**
     * 権限情報保存
     *
     * @param inDto パラメータ
     * @return 権限情報
     */
    @Operation(
        summary = "権限情報保存",
        description = "権限情報保存する",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "保存成功",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = R0020ApiResult.class))),
            @ApiResponse(
                responseCode = "500",
                description = "システムエラー",
                content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
        })
    @PostMapping("/save")
    public ResponseEntity<?> savekengenInfo(@Valid @RequestBody R0010S03Dto inDto) {
        // 初期表示
        ApiResult<R0020ReturnData> result = r0010Service.savekengenInfo(inDto);

        // 成功の場合
        return ResponseEntity.ok().body(result);
    }
}
