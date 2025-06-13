package com.daitoj.tkms.modules.apis0100.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（下請契約台帳）API結果情報.
 */
@Schema(name = "S0100ApiResult", description = "承認一覧（下請契約台帳）API結果情報")
public class S0100ApiResult extends ApiResult<S0100ReturnData> {
}
