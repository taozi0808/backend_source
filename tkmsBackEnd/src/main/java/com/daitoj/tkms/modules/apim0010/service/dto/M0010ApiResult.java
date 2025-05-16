package com.daitoj.tkms.modules.apim0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import io.swagger.v3.oas.annotations.media.Schema;

/** 現場経費一覧API結果情報 */
@Schema(name = "M0010ApiResult", description = "現場経費一覧API結果情報")
public class M0010ApiResult extends ApiResult<M0010ReturnData> {}
