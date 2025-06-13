package com.daitoj.tkms.modules.apiq0036.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 作業員名簿業者一覧API結果情報 */
@Schema(name = "Q0036ApiResult", description = "作業員名簿業者一覧API結果情報")
public class Q0036ApiResult extends ApiResult<Q0036ReturnData> {}
