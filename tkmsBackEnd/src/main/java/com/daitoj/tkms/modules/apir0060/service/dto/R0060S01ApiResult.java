package com.daitoj.tkms.modules.apir0060.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PI結果情報
 */
@Schema(name = "R0060S01ApiResult", description = "API結果情報")
public class R0060S01ApiResult extends ApiResult<R0060S01ReturnData> {}
