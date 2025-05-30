package com.daitoj.tkms.modules.apis0050.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（査定管理）API結果情報.
 */
@Schema(name = "S0050ApiResult", description = "承認一覧（査定管理）API結果情報")
public class S0050ApiResult extends ApiResult<S0050ReturnData> {
}
