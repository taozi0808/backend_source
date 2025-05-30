package com.daitoj.tkms.modules.apis0020.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（精積算管理）API結果情報.
 */
@Schema(name = "S0020ApiResult", description = "承認一覧（精積算管理）API結果情報")
public class S0020ApiResult extends ApiResult<S0020ReturnData> {
}
