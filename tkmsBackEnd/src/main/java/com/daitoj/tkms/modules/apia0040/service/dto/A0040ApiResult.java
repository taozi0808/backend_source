package com.daitoj.tkms.modules.apia0040.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** ダッシュボードAPI結果情報. */
@Schema(name = "A0040ApiResult", description = "ダッシュボードAPI結果情報")
public class A0040ApiResult extends ApiResult<A0040ReturnData> {}
