package com.daitoj.tkms.modules.apis0110.service.dto;

import com.daitoj.tkms.modules.apis0100.service.dto.S0100ReturnData;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（再下請負通知書）API結果情報.
 */
@Schema(name = "S0110ApiResult", description = "承認一覧（再下請負通知書）API結果情報")
public class S0110ApiResult extends ApiResult<S0110ReturnData> {
}
