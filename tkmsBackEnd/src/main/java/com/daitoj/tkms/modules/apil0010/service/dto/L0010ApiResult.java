package com.daitoj.tkms.modules.apil0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 請求一覧API結果情報. */
@Schema(name = "L0010ApiResult", description = "請求一覧API結果情報")
public class L0010ApiResult extends ApiResult<L0010ReturnData> {}
