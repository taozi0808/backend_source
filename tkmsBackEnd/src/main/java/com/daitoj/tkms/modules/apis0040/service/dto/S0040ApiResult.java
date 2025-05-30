package com.daitoj.tkms.modules.apis0040.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（実行予算管理）API結果情報.
 */
@Schema(name = "S0040ApiResult", description = "承認一覧（実行予算管理）API結果情報")
public class S0040ApiResult extends ApiResult<S0040ReturnData> {
}
