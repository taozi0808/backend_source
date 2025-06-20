package com.daitoj.tkms.modules.apif0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 物件一覧API結果情報. */
@Schema(name = "F0010ApiResult", description = "物件一覧API結果情報")
public class F0010ApiResult extends ApiResult<F0010ReturnData> {}
