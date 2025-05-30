package com.daitoj.tkms.modules.apii0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/** 出来高シュミレーション一覧パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "I0010S01Dto", description = "出来高シュミレーション一覧パラメータ")
public class I0010S01Dto extends BasePageRequest {

  /** 現場コード/概算コード */
  @Schema(name = "genbaCode", description = "現場コード/概算コード")
  private String genbaCode;

  /** 現場名/案件名（ｶﾅ含む） */
  @Schema(name = "genbaName", description = "現場名/案件名（ｶﾅ含む）")
  private String genbaName;

  /** 現場着工日（開始） */
  @Schema(name = "genbaChakkouYmdStart", description = "現場着工日（開始）(yyyyMMdd)", example = "20250102")
  private String genbaChakkouYmdStart;

  /** 現場着工日（終了） */
  @Schema(name = "genbaChakkouYmdEnd", description = "現場着工日（終了）(yyyyMMdd)", example = "20250102")
  private String genbaChakkouYmdEnd;

  /** 現場完工日（開始） */
  @Schema(name = "genbaKankouYmdStart", description = "現場完工日（開始）(yyyyMMdd)", example = "20250102")
  private String genbaKankouYmdStart;

  /** 現場完工日（終了） */
  @Schema(name = " genbaKankouYmdEnd ", description = "現場完工日（終了）(yyyyMMdd)", example = "20250102")
  private String genbaKankouYmdEnd;
}
