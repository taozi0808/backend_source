package com.daitoj.tkms.modules.apis0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（概算管理）API結果情報.
 */
@Schema(name = "S0010ApiResult", description = "承認一覧（概算管理）API結果情報")
public class S0010ApiResult extends ApiResult<S0010ReturnData> {
}
