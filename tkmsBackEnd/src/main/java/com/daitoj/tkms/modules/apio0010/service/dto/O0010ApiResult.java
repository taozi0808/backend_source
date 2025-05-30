package com.daitoj.tkms.modules.apio0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 業者一覧API結果情報. */
@Schema(name = "O0010ApiResult", description = "業者一覧API結果情報")
public class O0010ApiResult extends ApiResult<O0010ReturnData> {}
