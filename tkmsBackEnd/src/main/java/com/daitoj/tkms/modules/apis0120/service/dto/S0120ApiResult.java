package com.daitoj.tkms.modules.apis0120.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（顧客管理）API結果情報.
 */
@Schema(name = "S0120ApiResult", description = "承認一覧（顧客管理）API結果情報")
public class S0120ApiResult extends ApiResult<S0120ReturnData> {
}
