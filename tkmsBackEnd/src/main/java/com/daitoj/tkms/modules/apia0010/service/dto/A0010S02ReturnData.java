package com.daitoj.tkms.modules.apia0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** お知らせ情報結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "A0010S02ReturnData", description = "お知らせ情報結果")
public class A0010S02ReturnData {
  /** お知らせ内容（HTML） */
  @Schema(description = "お知らせ内容（HTML）")
  private String noticeContentHtml;
}
