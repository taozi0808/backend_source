package com.daitoj.tkms.modules.apis0130.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（会社管理）API結果情報.
 */
@Schema(name = "S0130ApiResult", description = "承認一覧（会社管理）API結果情報")
public class S0130ApiResult extends ApiResult<S0130ReturnData> {
}
