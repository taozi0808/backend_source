package com.daitoj.tkms.modules.apib0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 案件一覧API結果情報 */
@Schema(name = "B0010ApiResult", description = "案件一覧API結果情報")
public class B0010ApiResult extends ApiResult<B0010ReturnData> {}
