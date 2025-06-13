package com.daitoj.tkms.modules.apiq0035.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 作業員名簿物件一覧情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "GenbaInfoDto", description = "検索結果")
public class GenbaInfoDto {

  /** Id. */
  @Schema(name = "id", description = "Id")
  private long id;

  /** 物件コード. */
  @Schema(name = "projectSiteCd", description = "物件コード")
  private String projectSiteCd;

  /** 物件名. */
  @Schema(name = "projectSiteNm", description = "物件名")
  private String projectSiteNm;

  /** 専任技術者コード. */
  @Schema(name = "ftEngineerCd", description = "専任技術者コード")
  private String ftEngineerCd;

  /** 専任技術者名. */
  @Schema(name = "ftEngineerNm", description = "専任技術者名")
  private String ftEngineerNm;

  /** 現場着手日. */
  @Schema(name = "constrSiteStartYmd", description = "現場着手日")
  private String constrSiteStartYmd;

  /** 現場引渡日. */
  @Schema(name = "constrSiteDeliveryYmd", description = "現場引渡日")
  private String constrSiteDeliveryYmd;

  /** 書類提出状況. */
  @Schema(name = "docSubmissionStatus", description = "書類提出状況")
  private String docSubmissionStatus;

  /** 物件カナ名. */
  @Schema(name = "projectSiteKnNm", description = "物件カナ名")
  private String projectSiteKnNm;

  /** 専任技術者カナ名. */
  @Schema(name = "ftEngineerKnNm", description = "専任技術者カナ名")
  private String ftEngineerKnNm;

  /**
   * コンストラクタ.
   *
   * @param id                    Id
   * @param projectSiteCd         物件コード
   * @param projectSiteNm         物件名
   * @param ftEngineerCd          専任技術者コード
   * @param ftEngineerNm          専任技術者名
   * @param constrSiteStartYmd    現場着手日
   * @param constrSiteDeliveryYmd 現場引渡日
   * @param docSubmissionStatus   書類提出状況
   * @param projectSiteKnNm       物件カナ名
   * @param ftEngineerKnNm        専任技術者カナ名
   */
  public GenbaInfoDto(
      long id,
      String projectSiteCd,
      String projectSiteNm,
      String ftEngineerCd,
      String ftEngineerNm,
      String constrSiteStartYmd,
      String constrSiteDeliveryYmd,
      String docSubmissionStatus,
      String projectSiteKnNm,
      String ftEngineerKnNm) {
    this.id = id;
    this.projectSiteCd = projectSiteCd;
    this.projectSiteNm = projectSiteNm;
    this.ftEngineerCd = ftEngineerCd;
    this.ftEngineerNm = ftEngineerNm;
    this.constrSiteStartYmd = constrSiteStartYmd;
    this.constrSiteDeliveryYmd = constrSiteDeliveryYmd;
    this.projectSiteKnNm = projectSiteKnNm;
    this.ftEngineerKnNm = ftEngineerKnNm;
    this.docSubmissionStatus = docSubmissionStatus;
  }
}
