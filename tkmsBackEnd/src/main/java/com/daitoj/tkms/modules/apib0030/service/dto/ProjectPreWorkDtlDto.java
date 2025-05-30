package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/** 先行作業明細情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ProjectPreWorkDtlDto", description = "先行作業明細情報")
public class ProjectPreWorkDtlDto extends BaseDto {
  /** 案件ID */
  @Schema(description = "案件ID")
  private Long projectId;

  /** 連番 */
  @NotNull
  @Schema(description = "連番", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer seqNo;

  /** 大工事コード */
  @Size(max = 3)
  @Schema(description = "大工事コード", maxLength = 3)
  private String majorWorkCd;

  /** 大工事名 */
  @Schema(description = "大工事名")
  private String majorWorkNm;

  /** 小工事コード */
  @Size(max = 4)
  @Schema(description = "小工事コード", maxLength = 4)
  private String minorWorkCd;

  /** 小工事名 */
  @Schema(description = "小工事名")
  private String minorWorkNm;

  /** 精積算明細ID */
  @NotNull
  @Schema(description = "精積算明細ID", requiredMode = Schema.RequiredMode.REQUIRED)
  private Long detailedEstDid;

  /** 見積日 */
  @Size(max = 8)
  @Schema(description = "見積日", maxLength = 8)
  private String estYmd;

  /** 計画概要 */
  @NotNull
  @Schema(description = "計画概要", requiredMode = Schema.RequiredMode.REQUIRED)
  private String outline;

  /** 先行工事内容 */
  @Schema(description = "先行工事内容")
  private String preConstrContent;

  /** 工事原価 */
  @Schema(description = "工事原価")
  private String constrCost;

  /** 支払条件 */
  @Schema(description = "支払条件")
  private String paymentTerms;

  /** その他 */
  @Schema(description = "その他")
  private String sonota;

  /** ファイルインデックス */
  @Schema(description = "ファイルインデックス")
  private String fileIndex;

  /** 添付ファイルID */
  @Schema(description = "添付ファイルID")
  private UUID fileId;

  /** 添付ファイル名 */
  @Schema(description = "添付ファイル名")
  private String fileName;

  /** 先行作業処理区分 */
  @NotNull
  @Schema(description = "先行作業処理区分", requiredMode = Schema.RequiredMode.REQUIRED)
  private String preWorkProcessK = "1";

  /** 注文書ヘッダID */
  @Schema(description = "注文書ヘッダID")
  private Long poHid;

  /** 工事連番 */
  @NotNull
  @Size(max = 3)
  @Schema(description = "工事連番", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 3)
  private String workSeqNo;

  /**
   * @param projectId 案件ID
   * @param seqNo 連番
   * @param majorWorkCd 大工事コード
   * @param majorWorkNm 大工事名
   * @param minorWorkCd 小工事コード
   * @param minorWorkNm 小工事名
   * @param detailedEstDid 精積算明細ID
   * @param estYmd 見積日
   * @param outline 計画概要
   * @param preConstrContent 先行工事内容
   * @param constrCost 工事原価
   * @param paymentTerms 支払条件
   * @param sonota その他
   * @param fileId 添付ファイルID
   * @param fileName 添付ファイル名
   * @param preWorkProcessK 先行作業処理区分
   * @param poHid 注文書ヘッダID
   * @param workSeqNo 工事連番
   */
  public ProjectPreWorkDtlDto(
      Long projectId,
      Integer seqNo,
      String majorWorkCd,
      String majorWorkNm,
      String minorWorkCd,
      String minorWorkNm,
      Long detailedEstDid,
      String estYmd,
      String outline,
      String preConstrContent,
      String constrCost,
      String paymentTerms,
      String sonota,
      UUID fileId,
      String fileName,
      String preWorkProcessK,
      Long poHid,
      String workSeqNo) {
    this.projectId = projectId;
    this.seqNo = seqNo;
    this.majorWorkCd = majorWorkCd;
    this.majorWorkNm = majorWorkNm;
    this.minorWorkCd = minorWorkCd;
    this.minorWorkNm = minorWorkNm;
    this.detailedEstDid = detailedEstDid;
    this.estYmd = estYmd;
    this.outline = outline;
    this.preConstrContent = preConstrContent;
    this.constrCost = constrCost;
    this.paymentTerms = paymentTerms;
    this.fileId = fileId;
    this.fileName = fileName;
    this.sonota = sonota;
    this.preWorkProcessK = preWorkProcessK;
    this.poHid = poHid;
    this.workSeqNo = workSeqNo;
  }
}
