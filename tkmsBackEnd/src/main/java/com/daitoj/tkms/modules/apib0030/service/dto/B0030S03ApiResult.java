package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 案件情報登録保存結果 */
@Schema(name = "B0030S03ApiResult", description = "案件情報登録保存結果")
public class B0030S03ApiResult extends ApiResult<B0030S03ReturnData> {}
