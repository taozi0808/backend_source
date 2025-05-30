package com.daitoj.tkms.modules.apid0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 精積算一覧API結果情報. */
@Schema(name = "D0010ApiResult", description = "精積算一覧API結果情報")
public class D0010ApiResult extends ApiResult<D0010ReturnData> {}
