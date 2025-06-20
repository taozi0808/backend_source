package com.daitoj.tkms.modules.apig0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 実行予算情報結果. */
@Schema(name = "G0030S01ApiResult", description = "実行予算選択項目取得結果")
public class G0030S01ApiResult extends ApiResult<G0030S01ReturnData> {}
