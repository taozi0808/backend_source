package com.daitoj.tkms.modules.apia0040.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** ダッシュボードの検索結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "A0040ReturnData", description = "ダッシュボードの検索結果")
public class A0040ReturnData {

  /* 協力会社からのお知らせ */
  @Schema(name = "listCooperatingNoticeInfo", description = "協力会社からのお知らせ")
  private List<NoticeResultInfoDto> listCooperatingNoticeInfo;

  /* 「通知・通達・社報」「申請・承認待ち」「協力会社からのお知らせ」 */
  @Schema(name = "listNoticeInfo", description = "「通知・通達・社報」「申請・承認待ち」「協力会社からのお知らせ」")
  private List<NoticeResultInfoDto> listNoticeInfo;

  /* 申請・承認待ち情報 */
  @Schema(name = "listApplyInfo", description = "申請・承認待ち情報")
  private List<NoticeResultInfoDto> listApplyInfo;

}
