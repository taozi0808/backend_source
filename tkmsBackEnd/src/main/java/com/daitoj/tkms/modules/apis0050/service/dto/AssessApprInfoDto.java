package com.daitoj.tkms.modules.apis0050.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（査定管理）情報.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "AssessApprInfoDto", description = "検索結果")
public class AssessApprInfoDto {
  /**
   * 現場コード.
   */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /**
   * 現場名.
   */
  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  /**
   * 現場カナ名.
   */
  @Schema(name = "constrSiteKnNm", description = "現場名")
  private String constrSiteKnNm;

  /**
   * 現場所長.
   */
  @Schema(name = "constrSiteDirectorNm", description = "現場所長")
  private String constrSiteDirectorNm;

  /**
   * 担当部門.
   */
  @Schema(name = "orgNm", description = "担当部門")
  private String orgNm;

  /**
   * 工事工程.
   */
  @Schema(name = "constrProcessNm", description = "工事工程")
  private String constrProcessNm;

  /**
   * 最終承認日.
   */
  @Schema(name = "finalApprDt", description = "最終承認日")
  private String finalApprDt;

  /**
   * 申請者.
   */
  @Schema(name = "requestEmpNm", description = "申請者")
  private String requestEmpNm;

  /**
   * 申請日.
   */
  @Schema(name = "requestTs", description = "申請日")
  private String requestTs;

  /**
   * 結果.
   */
  @Schema(name = "itemValue", description = "結果")
  private String itemValue;

  /**
   * コメント.
   */
  @Schema(name = "comment", description = "コメント")
  private String comment;

  /**
   * 項目コード.
   */
  @Schema(name = "itemCd", description = "項目コード")
  private String itemCd;

  /**
   * コンストラクタ.
   */
  public AssessApprInfoDto() {
  }

  /**
   * コンストラクタ.
   *
   * @param constrSiteCd         現場コード
   * @param constrSiteNm         現場名
   * @param constrSiteKnNm       現場カナ名
   * @param constrSiteDirectorNm 現場所長
   * @param orgNm                担当部門
   * @param constrProcessNm      工事工程
   * @param finalApprDt          査定承認日
   * @param requestTs            申請日
   * @param itemValue            結果
   * @param comment              コメント
   * @param itemCd               アイテムコード
   */
  public AssessApprInfoDto(
      String constrSiteCd,
      String constrSiteNm,
      String constrSiteKnNm,
      String constrSiteDirectorNm,
      String orgNm,
      String constrProcessNm,
      String finalApprDt,
      String requestEmpNm,
      String requestTs,
      String itemValue,
      String comment,
      String itemCd) {
    this.constrSiteCd = constrSiteCd;
    this.constrSiteNm = constrSiteNm;
    this.constrSiteKnNm = constrSiteKnNm;
    this.constrSiteDirectorNm = constrSiteDirectorNm;
    this.orgNm = orgNm;
    this.constrProcessNm = constrProcessNm;
    this.finalApprDt = finalApprDt;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.itemValue = itemValue;
    this.comment = comment;
    this.itemCd = itemCd;
  }
}
