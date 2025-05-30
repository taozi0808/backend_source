package com.daitoj.tkms.modules.apis0080.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（協力業者管理）API結果情報.
 */
@Schema(name = "S0080ApiResult", description = "承認一覧（協力業者管理）API結果情報")
public class S0080ApiResult extends ApiResult<S0080ReturnData> {
}
