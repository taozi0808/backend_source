package com.daitoj.tkms.modules.apig0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 実行予算一覧API結果情報. */
@Schema(name = "G0010ApiResult", description = "実行予算一覧API結果情報")
public class G0010ApiResult extends ApiResult<G0010ReturnData> {}
