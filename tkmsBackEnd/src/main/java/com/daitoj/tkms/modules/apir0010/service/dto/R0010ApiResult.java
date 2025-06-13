package com.daitoj.tkms.modules.apir0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 権限選択項目取得AP結果情報 */
@Schema(name = "R0010ApiResult", description = "権限選択項目取得AP結果情報")
public class R0010ApiResult extends ApiResult<R0010ReturnData> {}
