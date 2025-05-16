package com.daitoj.tkms.modules.common.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Cacheコントローラ */
@RestController
@Tag(name = "共通API", description = "共通のAPI")
@RequestMapping("/api/v1/cache")
public class CacheResource {

  /**
   * 項目分類コードより、マスタデータCacheを削除する
   *
   * @param itemClassCd 項目分類コード
   */
  @Operation(
      summary = "マスタデータCacheを削除する",
      description = "項目分類コードより、マスタデータCacheを削除する",
      responses = {@ApiResponse(responseCode = "200", description = "成功")})
  @GetMapping("/removeItemByClass")
  @CacheEvict(
      value =
          com.daitoj.tkms.modules.common.repository.MItemListSettingRepository
              .ITEMLIST_BY_CLASS_CACHE,
      key = "#itemClassCd")
  public ResponseEntity<?> removeItemByClass(
      @RequestParam(name = "itemClassCd", required = true)
          @Parameter(
              name = "itemClassCd",
              description = "項目分類コード",
              required = true,
              in = ParameterIn.QUERY)
          String itemClassCd) {
    return ResponseEntity.ok().build();
  }

  /**
   * 項目分類コード、項目コードより、マスタデータCacheを削除する
   *
   * @param itemClassCd 項目分類コード
   * @param itemCd 項目コード
   */
  @Operation(
      summary = "マスタデータCacheを削除する",
      description = "項目分類コード、項目コードより、マスタデータCacheを削除する",
      responses = {@ApiResponse(responseCode = "200", description = "成功")})
  @GetMapping("/removeItemByClassAndCd")
  @CacheEvict(
      value =
          com.daitoj.tkms.modules.common.repository.MItemListSettingRepository
              .ITEMLIST_BY_CLASS_CD_CACHE,
      key = "#itemClassCd + '-' + #itemCd")
  public ResponseEntity<?> removeItemByClassAndCd(
      @RequestParam(name = "itemClassCd", required = true)
          @Parameter(
              name = "itemClassCd",
              description = "項目分類コード",
              required = true,
              in = ParameterIn.QUERY)
          String itemClassCd,
      @RequestParam(name = "itemCd", required = true)
          @Parameter(
              name = "itemCd",
              description = "項目コード",
              required = true,
              in = ParameterIn.QUERY)
          String itemCd) {
    return ResponseEntity.ok().build();
  }

  /**
   * システムコードより、システム設定Cacheを削除する
   *
   * @param sysCd システムコード
   */
  @Operation(
      summary = "システム設定Cacheを削除する",
      description = "システムコードより、マスタデータCacheを削除する",
      responses = {@ApiResponse(responseCode = "200", description = "成功")})
  @GetMapping("/removeItemBySysCd")
  @CacheEvict(
      value =
          com.daitoj.tkms.modules.common.repository.MSystemConfigRepository
              .SYSTEMCONFIG_BY_SYSCD_CACHE,
      key = "#sysCd")
  public ResponseEntity<?> removeItemBySysCd(
      @RequestParam(name = "sysCd", required = true)
          @Parameter(
              name = "sysCd",
              description = "システムコード",
              required = true,
              in = ParameterIn.QUERY)
          String sysCd) {
    return ResponseEntity.ok().build();
  }

  /**
   * システムコード、システム設定キーより、システム設定Cacheを削除する
   *
   * @param sysCd システムコード
   * @param configKey システム設定キー
   */
  @Operation(
      summary = "システム設定Cacheを削除する",
      description = "システムコード、システム設定キーより、マスタデータCacheを削除する",
      responses = {@ApiResponse(responseCode = "200", description = "成功")})
  @GetMapping("/removeItemBySysCdAndConfigKey")
  @CacheEvict(
      value =
          com.daitoj.tkms.modules.common.repository.MSystemConfigRepository
              .SYSTEMCONFIG_BY_SYSCD_KEY_CACHE,
      key = "#sysCd + '-' + #configKey")
  public ResponseEntity<?> removeItemBySysCdAndConfigKey(
      @RequestParam(name = "sysCd", required = true)
          @Parameter(
              name = "sysCd",
              description = "システムコード",
              required = true,
              in = ParameterIn.QUERY)
          String sysCd,
      @RequestParam(name = "configKey", required = true)
          @Parameter(
              name = "configKey",
              description = "システム設定キー",
              required = true,
              in = ParameterIn.QUERY)
          String configKey) {
    return ResponseEntity.ok().build();
  }
}
