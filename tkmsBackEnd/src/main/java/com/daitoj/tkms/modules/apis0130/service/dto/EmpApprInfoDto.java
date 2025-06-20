package com.daitoj.tkms.modules.apis0130.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 承認一覧（会社管理）情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "EmpApprInfoDto", description = "検索結果")
public class EmpApprInfoDto {

  /** マスタ名. */
  @Schema(name = "mstName", description = "マスタ名")
  private String mstName;

  /** コード/適用開始日. */
  @Schema(name = "empCd", description = "コード/適用開始日")
  private String empCd;

  /** 名称. */
  @Schema(name = "empNm", description = "名称")
  private String empNm;

  /** 名称カナ名. */
  @Schema(name = "empKnNm", description = "名称カナ名")
  private String empKnNm;

  /** 申請者. */
  @Schema(name = "requestEmpNm", description = "申請者")
  private String requestEmpNm;

  /** 申請日. */
  @Schema(name = "requestTs", description = "申請日")
  private String requestTs;

  /** 最終承認日. */
  @Schema(name = "finalApprDt", description = "最終承認日")
  private String finalApprDt;

  /** 結果. */
  @Schema(name = "itemValue", description = "結果")
  private String itemValue;

  /** コメント. */
  @Schema(name = "comment", description = "コメント")
  private String comment;

  /** 項目コード. */
  @Schema(name = "itemCd", description = "項目コード")
  private String itemCd;

  /** コンストラクタ. */
  public EmpApprInfoDto() {}

  /**
   * @param mstName マスタ名
   * @param empCd コード/適用開始日
   * @param empNm 名称
   * @param empKnNm 名称カナ名
   * @param requestEmpNm 申請者
   * @param requestTs 申請日
   * @param finalApprDt 最終承認日
   * @param itemValue 結果
   * @param comment コメント
   * @param itemCd 項目コード
   */
  public EmpApprInfoDto(
      String mstName,
      String empCd,
      String empNm,
      String empKnNm,
      String requestEmpNm,
      String requestTs,
      String finalApprDt,
      String itemValue,
      String comment,
      String itemCd) {
    this.mstName = mstName;
    this.empCd = empCd;
    this.empNm = empNm;
    this.empKnNm = empKnNm;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.finalApprDt = finalApprDt;
    this.itemValue = itemValue;
    this.comment = comment;
    this.itemCd = itemCd;
  }
}
