package com.daitoj.tkms.modules.apis0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（物件管理）API結果情報.
 */
@Schema(name = "S0030ApiResult", description = "承認一覧（物件管理）API結果情報")
public class S0030ApiResult extends ApiResult<S0030ReturnData> {
}
