package com.daitoj.tkms.modules.apis0070.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（工事予実管理）API結果情報.
 */
@Schema(name = "S0070ApiResult", description = "承認一覧（工事予実管理）API結果情報")
public class S0070ApiResult extends ApiResult<S0070ReturnData> {
}
