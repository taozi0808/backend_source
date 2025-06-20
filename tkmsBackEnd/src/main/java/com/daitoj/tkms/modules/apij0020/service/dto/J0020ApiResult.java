package com.daitoj.tkms.modules.apij0020.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 概算一覧API結果情報 */
@Schema(name = "J0020ApiResult", description = "概算一覧API結果情報")
public class J0020ApiResult extends ApiResult<J0020ReturnData> {}
