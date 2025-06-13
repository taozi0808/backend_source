package com.daitoj.tkms.modules.apiq0036.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/** 作業員名簿業者一覧パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "Q0036S01Dto", description = "作業員名簿業者一覧パラメータ")
public class Q0036S01Dto extends BasePageRequest {

  /** 物件コード */
  @Schema(name = "bukkenCode", description = "物件コード")
  private String bukkenCode;

  /** 業者コード */
  @Schema(name = "gyoushaCode", description = "業者コード")
  private String gyoushaCode;

  /** 業者名（ｶﾅ含む） */
  @Schema(name = "gyoushaName", description = "業者名（カナ含む）")
  private String gyoushaName;

  /** 現場コード */
  @Schema(name = "genbaCode", description = "現場コード")
  private String genbaCode;

  /** 現場名（ｶﾅ含む） */
  @Schema(name = "genbaName", description = "現場名（カナ含む）")
  private String genbaName;

  /** 注文書No */
  @Schema(name = "chuBunshoNo", description = "注文書No")
  private String chuBunshoNo;

  /** 大工事 */
  @Schema(name = "daikousyu", description = "大工事")
  private String daikousyu;

  /** 該当着手日（開始） */
  @Schema(
      name = "gaitouChakushuNichiStart",
      description = "該当着手日（開始）((yyyyMMdd))",
      example = "20250102")
  private String gaitouChakushuNichiStart;

  /** 該当着手日（終了） */
  @Schema(
      name = "gaitouChakushuNichiEnd",
      description = "該当着手日（終了）((yyyyMMdd))",
      example = "20250102")
  private String gaitouChakushuNichiEnd;

  /** 該当引渡日（開始） */
  @Schema(
      name = "gaitouBikiWataruNichiStart",
      description = "該当引渡日（開始）((yyyyMMdd))",
      example = "20250102")
  private String gaitouBikiWataruNichiStart;

  /** 該当引渡日（終了） */
  @Schema(
      name = "gaitouBikiWataruNichiEnd",
      description = "該当引渡日（終了）((yyyyMMdd))",
      example = "20250102")
  private String gaitouBikiWataruNichiEnd;
}
