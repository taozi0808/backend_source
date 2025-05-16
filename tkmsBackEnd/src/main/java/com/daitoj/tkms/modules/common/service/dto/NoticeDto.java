package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** お知らせ情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.NoticeDto", description = "お知らせ情報")
public class NoticeDto {
  /** 適用開始日 */
  @Schema(description = "適用開始日")
  private String effectiveStartDt;

  /** お知らせ内容（HTML） */
  @Schema(description = "お知らせ内容（HTML）")
  private String noticeContentHtml;

  /** 説明 */
  @Schema(description = "説明")
  private String comment;
}
