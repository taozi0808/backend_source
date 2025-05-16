package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/** 物件情報 */
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@Schema(name = "ProjectSiteDto", description = "物件情報")
public class ProjectSiteDto extends BaseDto {
  /** 現場コード */
  @Schema(description = "現場コード")
  private String constrSiteCd;

  /** 現場名 */
  @Schema(description = "現場名")
  private String constrSiteNm;

  /** 物件コード */
  @Schema(description = "物件コード")
  private String projectSiteCd;

  /** 物件名 */
  @Schema(description = "物件名")
  private String projectSiteNm;

  /** 物件カナ名 */
  @Schema(description = "物件カナ名")
  private String projectSiteKnNm;

  /** 見積日付 */
  @Schema(description = "見積日付")
  private String estYmd;

  /**
   * コンストラクタ
   *
   * @param constrSiteCd 現場コード
   * @param constrSiteNm 現場名
   * @param projectSiteCd 物件コード
   * @param projectSiteNm 物件名
   * @param projectSiteKnNm 物件カナ名
   * @param estYmd 見積日付
   * @param delFlg 削除フラグ
   * @param regTs 登録日時
   * @param regUserId 登録者ID
   * @param regPgId 登録PG
   * @param updTs 更新日時
   * @param updUserId 更新者ID
   * @param updPgId 更新PG
   */
  public ProjectSiteDto(
      String constrSiteCd,
      String constrSiteNm,
      String projectSiteCd,
      String projectSiteNm,
      String projectSiteKnNm,
      String estYmd,
      String delFlg,
      Instant regTs,
      String regUserId,
      String regPgId,
      Instant updTs,
      String updUserId,
      String updPgId) {
    this.constrSiteCd = constrSiteCd;
    this.constrSiteNm = constrSiteNm;
    this.projectSiteCd = projectSiteCd;
    this.projectSiteNm = projectSiteNm;
    this.projectSiteKnNm = projectSiteKnNm;
    this.estYmd = estYmd;
    this.delFlg = delFlg;
    this.regTs = regTs;
    this.regUserId = regUserId;
    this.regPgId = regPgId;
    this.updTs = updTs;
    this.updUserId = updUserId;
    this.updPgId = updPgId;
  }
}
