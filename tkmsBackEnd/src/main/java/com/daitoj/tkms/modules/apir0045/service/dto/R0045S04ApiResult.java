package com.daitoj.tkms.modules.apir0045.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 従業員の異動情報結果 */
@Schema(name = "R0045S04ApiResult", description = "従業員の異動情報結果")
public class R0045S04ApiResult extends ApiResult<R0045S04ReturnData> {}
