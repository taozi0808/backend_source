package com.daitoj.tkms.modules.apic0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/** 概算一覧印刷パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0010S03Dto", description = "概算一覧印刷パラメータ")
public class C0010S03Dto {

  /** 利用PCのシステム日付 */
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String sysDate;

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
