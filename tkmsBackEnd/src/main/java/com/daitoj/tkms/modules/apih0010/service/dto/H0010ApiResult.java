package com.daitoj.tkms.modules.apih0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 査定一覧API結果情報. */
@Schema(name = "H0010ApiResult", description = "査定一覧API結果情報")
public class H0010ApiResult extends ApiResult<H0010ReturnData> {}
