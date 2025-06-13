package com.daitoj.tkms.modules.apir0040.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 社員一覧API結果情報. */
@Schema(name = "R0040ApiResult", description = "社員一覧API結果情報")
public class R0040ApiResult extends ApiResult<R0040ReturnData> {}
