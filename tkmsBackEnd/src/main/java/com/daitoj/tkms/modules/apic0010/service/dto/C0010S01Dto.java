package com.daitoj.tkms.modules.apic0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/** 概算一覧パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0010S01Dto", description = "概算一覧パラメータ")
public class C0010S01Dto extends BasePageRequest {

  /** 案件コード */
  @Schema(name = "projectCd", description = "案件コード")
  private String projectCd;

  /** 案件名（ｶﾅ含む） */
  @Schema(name = "projectNm", description = "案件名（ｶﾅ含む）")
  private String projectNm;

  /** 概算コード */
  @Schema(name = "roughEstCd", description = "概算コード")
  private String roughEstCd;

  /** 顧客コード */
  @Schema(name = "customerCd", description = "顧客コード")
  private String customerCd;

  /** 顧客名（ｶﾅ含む） */
  @Schema(name = "customerName", description = "顧客名（ｶﾅ含む）")
  private String customerName;

  /** 見積提出期限（開始） */
  @Schema(name = "estSubmitDueDtStart", description = "見積提出期限（開始）")
  private String estSubmitDueDtStart;

  /** 見積提出期限（終了） */
  @Schema(name = "estSubmitDueDtEnd", description = "見積提出期限（終了）")
  private String estSubmitDueDtEnd;

  /** 概算部門 */
  @Schema(name = "orgNm", description = "概算部門")
  private String orgNm;

  /** 概算担当者 */
  @Schema(name = "empNm", description = "概算担当者")
  private String empNm;

  /** 概算作成済みの案件を表示する */
  @Schema(name = "gaisanSakusei", description = "概算作成済みの案件を表示する")
  private String gaisanSakusei;
}
