package com.daitoj.tkms.modules.apir0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 権限情報取得API結果情報 */
@Schema(name = "R0020ApiResult", description = "権限情報取得API結果情報")
public class R0020ApiResult extends ApiResult<R0020ReturnData> {}
