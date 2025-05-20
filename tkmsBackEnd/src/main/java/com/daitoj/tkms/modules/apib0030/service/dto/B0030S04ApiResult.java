package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 小工事リスト項目取得結果 */
@Schema(name = "B0030S04ApiResult", description = "小工事リスト項目取得結果")
public class B0030S04ApiResult extends ApiResult<B0030S04ReturnData> {}
