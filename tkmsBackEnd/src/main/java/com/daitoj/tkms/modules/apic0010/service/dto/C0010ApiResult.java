package com.daitoj.tkms.modules.apic0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 概算一覧API結果情報 */
@Schema(name = "C0010ApiResult", description = "概算一覧API結果情報")
public class C0010ApiResult extends ApiResult<C0010ReturnData> {}
