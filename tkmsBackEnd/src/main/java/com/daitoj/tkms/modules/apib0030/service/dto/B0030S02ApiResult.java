package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 案件情報取得結果 */
@Schema(name = "B0030S02ApiResult", description = "案件情報取得結果")
public class B0030S02ApiResult extends ApiResult<B0030S02ReturnData> {}
