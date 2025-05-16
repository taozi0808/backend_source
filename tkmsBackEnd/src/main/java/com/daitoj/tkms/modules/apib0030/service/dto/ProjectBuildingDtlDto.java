package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

/** 現場棟明細情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ProjectBuildingDtlDto", description = "現場棟明細情報")
public class ProjectBuildingDtlDto extends BaseDto {

  /** 案件ID */
  @Schema(description = "案件ID")
  private Long projectId;

  /** 連番 */
  @NotNull
  @Schema(description = "連番", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer seqNo;

  /** 棟コード */
  @NotNull
  @Size(max = 2)
  @Schema(description = "棟コード", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 2)
  private String buildingCd;

  /** 棟番号工事名 */
  @NotNull
  @Schema(description = "棟番号工事名", requiredMode = Schema.RequiredMode.REQUIRED)
  private String buildingWorkNm;

  /** 概算コード */
  @Schema(description = "概算コード")
  private String roughEstCd;

  /** コンストラクタ */
  public ProjectBuildingDtlDto() {}

  /**
   * コンストラクタ
   *
   * @param projectId 案件ID
   * @param seqNo 連番
   * @param buildingCd 棟コード
   * @param buildingWorkNm 棟番号工事名
   * @param roughEstCd 概算コード
   * @param delFlg 削除フラグ
   * @param regTs 登録日時
   * @param regUserId 登録者ID
   * @param regPgId 登録PG
   * @param updTs 更新日時
   * @param updUserId 更新者ID
   * @param updPgId 更新PG
   */
  public ProjectBuildingDtlDto(
      Long projectId,
      Integer seqNo,
      String buildingCd,
      String buildingWorkNm,
      String roughEstCd,
      String delFlg,
      Instant regTs,
      String regUserId,
      String regPgId,
      Instant updTs,
      String updUserId,
      String updPgId) {
    this.projectId = projectId;
    this.seqNo = seqNo;
    this.buildingCd = buildingCd;
    this.buildingWorkNm = buildingWorkNm;
    this.roughEstCd = roughEstCd;
    this.delFlg = delFlg;
    this.regTs = regTs;
    this.regUserId = regUserId;
    this.regPgId = regPgId;
    this.updTs = updTs;
    this.updUserId = updUserId;
    this.updPgId = updPgId;
  }
}
