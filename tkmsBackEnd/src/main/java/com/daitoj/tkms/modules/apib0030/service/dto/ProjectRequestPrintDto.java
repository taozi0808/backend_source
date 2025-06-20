package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** 案件要望印刷明細情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ProjectRequestPrintDto", description = "案件要望印刷明細情報")
public class ProjectRequestPrintDto extends BaseDto {

  /** 要望発生年月日 */
  @NotNull
  @Size(max = 8)
  @Schema(description = "要望発生年月日", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 8)
  private String requestYmd;

  /** 要望内容/要望回答内容 */
  @NotNull
  @Schema(description = "要望内容/要望回答内容", requiredMode = Schema.RequiredMode.REQUIRED)
  private String content;

  /** グループID */
  @Schema(description = "グループID")
  private Integer groupId;

  /** コンストラクタ */
  public ProjectRequestPrintDto() {}

  /**
   * コンストラクタ
   *
   * @param requestYmd 要望発生年月日
   * @param content 要望内容/要望回答内容
   * @param groupId グループID
   */
  public ProjectRequestPrintDto(String requestYmd, String content, Integer groupId) {
    this.requestYmd = requestYmd;
    this.content = content;
    this.groupId = groupId;
  }
}
