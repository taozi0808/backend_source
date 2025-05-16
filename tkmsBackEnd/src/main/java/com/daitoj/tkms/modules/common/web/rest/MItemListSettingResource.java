package com.daitoj.tkms.modules.common.web.rest;

import com.daitoj.tkms.modules.common.service.ItemListSettingService;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import com.daitoj.tkms.modules.common.service.dto.MItemListSettingDto;
import com.daitoj.tkms.modules.common.service.dto.MItemListSettingResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** マスタデータコントローラ */
@RestController
@Tag(name = "共通API", description = "共通のAPI")
@RequestMapping("/api/v1/itemListSetting")
public class MItemListSettingResource {

  /** マスタデータサービス */
  private final ItemListSettingService itemListSettingService;

  /** コンストラクタ */
  public MItemListSettingResource(ItemListSettingService itemListSettingService) {
    this.itemListSettingService = itemListSettingService;
  }

  /**
   * マスタデータを取得する
   *
   * @param itemClassCd 項目分類コード
   * @return マスタデータ
   */
  @Operation(
      summary = "マスタデータを取得する",
      description = "マスタデータを取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MItemListSettingResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/get")
  public ResponseEntity<?> getData(
      @RequestParam(name = "item_class_cd", required = true)
          @Parameter(
              name = "item_class_cd",
              description = "項目分類コード",
              required = true,
              in = ParameterIn.QUERY)
          String itemClassCd) {

    // マスタデータリスト
    ApiResult<MItemListSettingDto> result = itemListSettingService.getData(itemClassCd);

    return ResponseEntity.ok().body(result);
  }

  /**
   * マスタデータリストを取得する
   *
   * @param itemClassCds 項目分類コードリスト
   * @return マスタデータリスト
   */
  @Operation(
      summary = "マスタデータリストを取得する",
      description = "マスタデータリストを取得する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "成功",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MItemListSettingResult.class))),
        @ApiResponse(
            responseCode = "500",
            description = "システムエラー",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorInfo.class)))
      })
  @GetMapping("/list/get")
  public ResponseEntity<?> getDataList(
      @RequestParam(name = "item_class_cds", required = true)
          @Parameter(
              name = "item_class_cds",
              description = "項目分類コードリスト",
              required = true,
              in = ParameterIn.QUERY)
          String[] itemClassCds) {

    // マスタデータリスト
    ApiResult<List<MItemListSettingDto>> result = itemListSettingService.getDataList(itemClassCds);

    return ResponseEntity.ok().body(result);
  }
}
