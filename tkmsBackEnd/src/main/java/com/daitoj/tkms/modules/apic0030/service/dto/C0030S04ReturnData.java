package com.daitoj.tkms.modules.apic0030.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/** 概算見積依頼情報結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0030S04ReturnData", description = "概算見積依頼情報取得")
public class C0030S04ReturnData {

    /** 概算明細リスト */
    @Schema(
        name = "listRoughEstDtlInfoDto",
        description = "概算明細リスト",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("listRoughEstDtlInfoDto")
    private List<RoughEstDtlInfoDto> listRoughEstDtlInfoDto = new ArrayList<>();

}
