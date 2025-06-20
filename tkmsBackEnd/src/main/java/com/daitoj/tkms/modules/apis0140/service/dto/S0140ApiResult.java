package com.daitoj.tkms.modules.apis0140.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（自社情報）API結果情報.
 */
@Schema(name = "S0140ApiResult", description = "承認一覧（自社情報）API結果情報")
public class S0140ApiResult extends ApiResult<S0140ReturnData> {}
