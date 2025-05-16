package com.daitoj.tkms.modules.apia0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** ログインAPI結果情報 */
@Schema(name = "A0010S01ApiResult", description = "ログインAPI結果情報")
public class A0010S01ApiResult extends ApiResult<A0010S01ReturnData> {}
