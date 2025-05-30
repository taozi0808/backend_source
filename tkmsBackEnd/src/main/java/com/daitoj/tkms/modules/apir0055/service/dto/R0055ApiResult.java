package com.daitoj.tkms.modules.apir0055.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 顧客一覧API結果情報. */
@Schema(name = "R0055ApiResult", description = "顧客一覧API結果情報")
public class R0055ApiResult extends ApiResult<R0055ReturnData> {}
