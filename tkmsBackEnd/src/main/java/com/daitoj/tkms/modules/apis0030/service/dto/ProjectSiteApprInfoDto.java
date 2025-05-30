package com.daitoj.tkms.modules.apis0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（物件管理）情報.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ProjectSiteApprInfoDto", description = "検索結果")
public class ProjectSiteApprInfoDto {

  /**
   * 物件コード.
   */
  @Schema(name = "projectSiteCd", description = "物件コード")
  private String projectSiteCd;

  /**
   * 物件名.
   */
  @Schema(name = "projectSiteNm", description = "物件名")
  private String projectSiteNm;

  /**
   * 顧客名.
   */
  @Schema(name = "customerNm", description = "顧客名")
  private String customerNm;

  /**
   * 着工日.
   */
  @Schema(name = "constrStartYmd", description = "着工日")
  private String constrStartYmd;

  /**
   * 完工日.
   */
  @Schema(name = "constrCompYmd", description = "完工日")
  private String constrCompYmd;

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
  public ProjectSiteApprInfoDto() {
  }

  /**
   * コンストラクタ.
   *
   * @param projectSiteCd  物件コード
   * @param projectSiteNm  物件名
   * @param customerNm     顧客名
   * @param constrStartYmd 着工日
   * @param constrCompYmd  完工日
   * @param requestTs      申請日
   * @param itemValue      結果
   * @param comment        コメント
   */
  public ProjectSiteApprInfoDto(
      String projectSiteCd,
      String projectSiteNm,
      String customerNm,
      String constrStartYmd,
      String constrCompYmd,
      String requestEmpNm,
      String requestTs,
      String itemValue,
      String comment) {
    this.projectSiteCd = projectSiteCd;
    this.projectSiteNm = projectSiteNm;
    this.customerNm = customerNm;
    this.constrStartYmd = constrStartYmd;
    this.constrCompYmd = constrCompYmd;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.itemValue = itemValue;
    this.comment = comment;
  }

}
