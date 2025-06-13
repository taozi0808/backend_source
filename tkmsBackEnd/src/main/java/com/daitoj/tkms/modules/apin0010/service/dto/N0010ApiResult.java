package com.daitoj.tkms.modules.apin0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 工事予実一覧API結果情報. */
@Schema(name = "N0010ApiResult", description = "工事予実一覧API結果情報")
public class N0010ApiResult extends ApiResult<N0010ReturnData> {}
