package com.daitoj.tkms.modules.apis0060.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（現場経費管理）情報.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ConstrSiteExpApprInfoDto", description = "検索結果")
public class ConstrSiteExpApprInfoDto {

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
   * 現場着手日.
   * */
  @Schema(name = "constrSiteStartYmd", description = "現場着手日")
  private String constrSiteStartYmd;

  /**
   * 現場引渡日.
   * */
  @Schema(name = "constrSiteDeliveryYmd", description = "現場引渡日")
  private String constrSiteDeliveryYmd;

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
   * コンストラクタ.
   */
  public ConstrSiteExpApprInfoDto() {
  }

  /**
   * コンストラクタ.
   *
   * @param constrSiteCd          現場コード
   * @param constrSiteNm          現場名
   * @param constrSiteStartYmd    現場着手日
   * @param constrSiteDeliveryYmd 現場引渡日
   * @param requestTs             申請日
   * @param itemValue             結果
   * @param comment               コメント
   */
  public ConstrSiteExpApprInfoDto(
      String constrSiteCd,
      String constrSiteNm,
      String constrSiteStartYmd,
      String constrSiteDeliveryYmd,
      String requestEmpNm,
      String requestTs,
      String itemValue,
      String comment) {
    this.constrSiteCd = constrSiteCd;
    this.constrSiteNm = constrSiteNm;
    this.constrSiteStartYmd = constrSiteStartYmd;
    this.constrSiteDeliveryYmd = constrSiteDeliveryYmd;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.itemValue = itemValue;
    this.comment = comment;
  }

}
