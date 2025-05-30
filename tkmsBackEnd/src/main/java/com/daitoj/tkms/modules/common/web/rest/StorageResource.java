package com.daitoj.tkms.modules.common.web.rest;

import com.daitoj.tkms.modules.common.constants.KeyConstants;
import com.daitoj.tkms.modules.common.service.CloudStorageService;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import com.daitoj.tkms.modules.common.service.dto.StorageUploadResult;
import com.daitoj.tkms.modules.common.service.dto.StorageUrlResult;
import com.daitoj.tkms.modules.common.utils.ContextUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** ストレージコントローラ */
@RestController
@Tag(name = "共通API", description = "共通のAPI")
@RequestMapping("/api/v1/storage")
public class StorageResource {

  /** 機能ID */
  public static final String PG_ID = "Web";

  /** ストレージサービス */
  private final CloudStorageService cloudStorageService;

  /** コンストラクタ */
  public StorageResource(CloudStorageService cloudStorageService) {
    this.cloudStorageService = cloudStorageService;
  }

  /**
   * ファイルリストをアップロード
   *
   * @param files ファイルリスト
   * @return ファイルIDリスト
   */
  @Operation(
      summary = "ファイルをアップロードする",
      description = "ファイルをアップロードする",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StorageUploadResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<StorageUploadResult> upload(
      @RequestParam(name = "file", required = true)
          @Parameter(description = "アップロードファイルリスト", required = true, in = ParameterIn.QUERY)
          @RequestPart("files")
          MultipartFile[] files,
      @RequestParam(name = "login_id", required = true)
          @Parameter(description = "ログインID", required = true, in = ParameterIn.QUERY)
          String loginId,
      @RequestParam(name = "pg_id", required = true)
          @Parameter(description = "機能ID", required = true, in = ParameterIn.QUERY)
          String pgId) {
    StorageUploadResult ret = new StorageUploadResult();

    // ログインID
    ContextUtils.setParameter(KeyConstants.HTTP_HEADER_LOGIN_ID, loginId);
    // 機能ID
    ContextUtils.setParameter(KeyConstants.HTTP_HEADER_PG_ID, String.join("", PG_ID, pgId));

    // ファイルIDリスト
    List<UUID> uuidList = cloudStorageService.upload(files);
    // 結果にファイルを設定
    ret.setUuid(uuidList);

    return ResponseEntity.ok().body(ret);
  }

  /**
   * ファイルをダウンロードする
   *
   * @param objectName オブジェクト名
   * @return ファイルStream
   */
  @Operation(
      summary = "ファイルをダウンロードする",
      description = "ファイルをダウンロードする",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/octet-stream",
                    schema = @Schema(implementation = ResponseEntity.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/download")
  public ResponseEntity<InputStreamResource> download(
      @RequestParam(name = "object_name", required = true)
          @Parameter(
              name = "object_name",
              description = "オブジェクト名",
              required = true,
              in = ParameterIn.QUERY)
          String objectName) {

    // パス
    Path filePath = Paths.get(objectName);
    // // ファイル名
    String fileName = filePath.getFileName().toString();
    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

    // ファイルStream
    InputStream inputStream = cloudStorageService.download(objectName);
    InputStreamResource resource = new InputStreamResource(inputStream);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName)
        .body(resource);
  }

  /**
   * ファイルUrlを取得する
   *
   * @param fileId ファイルID
   * @return ファイルUrl
   */
  @Operation(
      summary = "ファイルUrlを取得する",
      description = "ファイルUrlを取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/url/get")
  public ResponseEntity<?> getUrl(
      @RequestParam(name = "object_name", required = true)
          @Parameter(
              name = "object_name",
              description = "ファイルID",
              required = true,
              in = ParameterIn.QUERY)
          String fileId) {

    // ファイルUrl
    String url = cloudStorageService.createUrl(fileId);

    // 結果
    Map<String, String> ret = new HashMap<>();
    ret.put("url", url);

    return ResponseEntity.ok().body(ret);
  }

  /**
   * ファイルUrlリストを取得する
   *
   * @param fileIds ファイルIDリスト
   * @return ファイルUrlリスト
   */
  @Operation(
      summary = "ファイルUrlリストを取得する",
      description = "ファイルUrlリストを取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StorageUrlResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/urls")
  public ResponseEntity<List<StorageUrlResult>> getUrlList(
      @RequestParam(name = "object_names", required = true)
          @Parameter(
              name = "object_names",
              description = "ファイルIDリスト",
              required = true,
              in = ParameterIn.QUERY)
          String[] fileIds) {

    List<StorageUrlResult> ret = new ArrayList<>();

    for (String fileId : fileIds) {
      // ファイルUrl
      String url = cloudStorageService.createUrl(fileId);
      // 結果にファイルを設定
      ret.add(new StorageUrlResult(fileId, url));
    }

    return ResponseEntity.ok().body(ret);
  }
}
