package com.daitoj.tkms.modules.apis0070.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 承認一覧（工事予実管理）情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "ConstrWbsApprInfoDto", description = "検索結果")
public class ConstrWbsApprInfoDto {
  /** 現場コード. */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /** 現場名. */
  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  /** 現場カナ名. */
  @Schema(name = "constrSiteKnNm", description = "現場カナ名")
  private String constrSiteKnNm;

  /** 着工日. */
  @Schema(name = "constrStartYmd", description = "着工日")
  private String constrStartYmd;

  /** 完工日. */
  @Schema(name = "constrCompYmd", description = "完工日")
  private String constrCompYmd;

  /** 申請者. */
  @Schema(name = "requestEmpNm", description = "申請者")
  private String requestEmpNm;

  /** 申請日. */
  @Schema(name = "requestTs", description = "申請日")
  private String requestTs;

  /** 結果. */
  @Schema(name = "itemValue", description = "結果")
  private String itemValue;

  /** コメント. */
  @Schema(name = "comment", description = "コメント")
  private String comment;

  /** 項目コード. */
  @Schema(name = "itemCd", description = "項目コード")
  private String itemCd;

  /** 最終承認日. */
  @Schema(name = "itemCd", description = "最終承認日")
  private String finalApprDt;

  /** コンストラクタ. */
  public ConstrWbsApprInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param constrSiteCd 現場コード
   * @param constrSiteNm 現場名
   * @param constrSiteKnNm 現場カナ名
   * @param constrStartYmd 着工日
   * @param constrCompYmd 完工日
   * @param requestEmpNm 申請者
   * @param requestTs 申請日
   * @param itemValue 結果
   * @param comment コメント
   * @param itemCd 項目コード
   * @param finalApprDt 最終承認日
   */
  public ConstrWbsApprInfoDto(
      String constrSiteCd,
      String constrSiteNm,
      String constrSiteKnNm,
      String constrStartYmd,
      String constrCompYmd,
      String requestEmpNm,
      String requestTs,
      String itemValue,
      String comment,
      String itemCd,
      String finalApprDt) {
    this.constrSiteCd = constrSiteCd;
    this.constrSiteNm = constrSiteNm;
    this.constrSiteKnNm = constrSiteKnNm;
    this.constrStartYmd = constrStartYmd;
    this.constrCompYmd = constrCompYmd;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.itemValue = itemValue;
    this.comment = comment;
    this.itemCd = itemCd;
    this.finalApprDt = finalApprDt;
  }
}
