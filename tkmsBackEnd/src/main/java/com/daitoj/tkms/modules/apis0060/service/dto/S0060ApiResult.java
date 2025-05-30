package com.daitoj.tkms.modules.apis0060.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（現場経費管理）API結果情報.
 */
@Schema(name = "S0060ApiResult", description = "承認一覧（現場経費管理）API結果情報")
public class S0060ApiResult extends ApiResult<S0060ReturnData> {
}
