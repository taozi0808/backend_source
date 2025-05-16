package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

/** 案件要望明細情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ProjectRequestDtlDto", description = "案件要望明細情報")
public class ProjectRequestDtlDto extends BaseDto {

  /** 案件ID */
  @Schema(description = "案件ID")
  private Long projectId;

  /** 連番 */
  @NotNull
  @Schema(description = "連番", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer seqNo;

  /** 要望発生年月日 */
  @NotNull
  @Size(max = 8)
  @Schema(description = "要望発生年月日", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 8)
  private String requestYmd;

  /** 要望内容 */
  @NotNull
  @Schema(description = "要望内容", requiredMode = Schema.RequiredMode.REQUIRED)
  private String requestContent;

  /** 要望回答内容 */
  @Schema(description = "要望回答内容")
  private String requestAnswer;

  /** コンストラクタ */
  public ProjectRequestDtlDto() {}

  /**
   * コンストラクタ
   *
   * @param projectId 案件ID
   * @param seqNo 連番
   * @param requestYmd 要望発生年月日
   * @param requestContent 要望内容
   * @param requestAnswer 要望回答内容
   * @param delFlg 削除フラグ
   * @param regTs 登録日時
   * @param regUserId 登録者ID
   * @param regPgId 登録PG
   * @param updTs 更新日時
   * @param updUserId 更新者ID
   * @param updPgId 更新PG
   */
  public ProjectRequestDtlDto(
      Long projectId,
      Integer seqNo,
      String requestYmd,
      String requestContent,
      String requestAnswer,
      String delFlg,
      Instant regTs,
      String regUserId,
      String regPgId,
      Instant updTs,
      String updUserId,
      String updPgId) {
    this.projectId = projectId;
    this.seqNo = seqNo;
    this.requestYmd = requestYmd;
    this.requestContent = requestContent;
    this.requestAnswer = requestAnswer;
    this.delFlg = delFlg;
    this.regTs = regTs;
    this.regUserId = regUserId;
    this.regPgId = regPgId;
    this.updTs = updTs;
    this.updUserId = updUserId;
    this.updPgId = updPgId;
  }
}
