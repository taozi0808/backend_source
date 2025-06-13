package com.daitoj.tkms.modules.apir0040.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 社員情報. */
@lombok.Setter
@lombok.Getter
@Schema(name = "EmpInfoDto", description = "検索結果")
public class EmpInfoDto {

  /* 社員id */
  @Schema(name = "id", description = "社員id")
  private long id;

  /* 社員コード */
  @Schema(name = "empCd", description = "社員コード")
  private String empCd;

  /* 氏名 */
  @Schema(name = "empNm", description = "氏名")
  private String empNm;

  /* 性別 */
  @Schema(name = "sex", description = "性別")
  private String sex;

  /* 職種区分 */
  @Schema(name = "jobTypeK", description = "職種区分")
  private String jobTypeK;

  /* 職種名 */
  @Schema(name = "jobTypeNm", description = "職種名")
  private String jobTypeNm;

  /* 所属部コード */
  @Schema(name = "belongOrgCd", description = "所属部コード")
  private String belongOrgCd;

  /* 所属部 */
  @Schema(name = "belongOrgNm", description = "所属部")
  private String belongOrgNm;

  /* 所属部連番 */
  @Schema(name = "belongOrgSeq", description = "所属部連番")
  private Integer belongOrgSeq;

  /* 所属課コード */
  @Schema(name = "belongSectionCd", description = "所属課コード")
  private String belongSectionCd;

  /* 所属課 */
  @Schema(name = "belongSectionNm", description = "所属課")
  private String belongSectionNm;

  /* 所属課連番 */
  @Schema(name = "belongSectionSeq", description = "所属課連番")
  private Integer belongSectionSeq;

  /* 役職コード */
  @Schema(name = "positionCd", description = "役職コード")
  private String positionCd;

  /* 役職 */
  @Schema(name = "position", description = "役職")
  private String position;

  /** コンストラクタ. */
  public EmpInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param id id
   * @param empCd 社員コード
   * @param empNm 氏名
   * @param sex 性別
   * @param jobTypeK 職種区分
   * @param jobTypeNm 職種名
   * @param belongOrgCd 所属部コード
   * @param belongOrgNm 所属部
   * @param belongSectionCd 所属課コード
   * @param belongSectionNm 所属課
   * @param positionCd 役職コード
   * @param position 役職
   */
  public EmpInfoDto(
      long id,
      String empCd,
      String empNm,
      String sex,
      String jobTypeK,
      String jobTypeNm,
      String belongOrgCd,
      String belongOrgNm,
      Integer belongOrgSeq,
      String belongSectionCd,
      String belongSectionNm,
      Integer belongSectionSeq,
      String positionCd,
      String position) {
    this.id = id;
    this.empCd = empCd;
    this.empNm = empNm;
    this.sex = sex;
    this.jobTypeK = jobTypeK;
    this.jobTypeNm = jobTypeNm;
    this.belongOrgCd = belongOrgCd;
    this.belongOrgNm = belongOrgNm;
    this.belongOrgSeq = belongOrgSeq;
    this.belongSectionCd = belongSectionCd;
    this.belongSectionNm = belongSectionNm;
    this.belongSectionSeq = belongSectionSeq;
    this.positionCd = positionCd;
    this.position = position;
  }
}
