package com.daitoj.tkms.modules.apiq0035.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 作業員名簿物件情報一覧API結果情報. */
@Schema(name = "Q0035ApiResult", description = "作業員名簿物件情報一覧API結果情報")
public class Q0035ApiResult extends ApiResult<Q0035ReturnData> {}
