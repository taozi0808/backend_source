package com.daitoj.tkms.modules.apis0090.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 承認一覧（作業員情報管理）情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "WorkerApprInfoDto", description = "検索結果")
public class WorkerApprInfoDto {

  /** 作業員コード. */
  @Schema(name = "workerCd", description = "作業員コード")
  private String workerCd;

  /** 作業員氏名. */
  @Schema(name = "workerNm", description = "作業員氏名")
  private String workerNm;

  /** 作業員カナ氏名. */
  @Schema(name = "workerKnNm", description = "作業員カナ氏名")
  private String workerKnNm;

  /** 雇用区分. */
  @Schema(name = "employK", description = "雇用区分")
  private String employK;

  /** 健康診断. */
  @Schema(name = "healthCheckup", description = "健康診断")
  private String healthCheckup;

  /** 厚生年金. */
  @Schema(name = "wpiType", description = "厚生年金")
  private String wpiType;

  /** 健康保険. */
  @Schema(name = "ehiType", description = "健康保険")
  private String ehiType;

  /** 雇用保険. */
  @Schema(name = "eisType", description = "雇用保険")
  private String eisType;

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
  public WorkerApprInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param workerCd           作業員コード
   * @param workerNm           作業員氏名
   * @param workerKnNm 　　　　　作業員カナ氏名
   * @param employK 　　   　　　雇用区分
   * @param healthCheckup      健康診断
   * @param wpiType 　　　　　　　厚生年金
   * @param ehiType 　　　　　　　健康保険
   * @param eisType 　　　　　　　雇用保険
   * @param requestEmpNm 　　　　申請者
   * @param requestTs 　        申請日
   * @param finalApprDt         最終承認日
   * @param itemValue           結果
   * @param comment             コメント
   */
  public WorkerApprInfoDto(
      String workerCd,
      String workerNm,
      String workerKnNm,
      String employK,
      String healthCheckup,
      String wpiType,
      String ehiType,
      String eisType,
      String requestEmpNm,
      String requestTs,
      String finalApprDt,
      String itemValue,
      String comment,
      String itemCd) {
    this.workerCd = workerCd;
    this.workerNm = workerNm;
    this.workerKnNm = workerKnNm;
    this.employK = employK;
    this.healthCheckup = healthCheckup;
    this.wpiType = wpiType;
    this.ehiType = ehiType;
    this.eisType = eisType;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.finalApprDt = finalApprDt;
    this.itemValue = itemValue;
    this.comment = comment;
    this.itemCd = itemCd;
  }
}
