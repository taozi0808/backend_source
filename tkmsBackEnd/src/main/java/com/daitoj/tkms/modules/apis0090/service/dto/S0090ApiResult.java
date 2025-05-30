package com.daitoj.tkms.modules.apis0090.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（作業員情報管理）API結果情報.
 */
@Schema(name = "S0090ApiResult", description = "承認一覧（作業員情報管理）API結果情報")
public class S0090ApiResult extends ApiResult<S0090ReturnData> {
}
