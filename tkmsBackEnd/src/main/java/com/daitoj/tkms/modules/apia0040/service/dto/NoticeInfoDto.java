package com.daitoj.tkms.modules.apia0040.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** お知らせ情報. */
@lombok.Setter
@lombok.Getter
@Schema(name = "NoticeInfoDto", description = "お知らせ情報検索結果")
public class NoticeInfoDto {

  /* id */
  @Schema(name = "id", description = "id")
  protected Long id;

  /* お知らせ内容(HTML) */
  @Schema(name = "noticeContentHtml", description = "お知らせ内容(HTML)")
  protected String noticeContentHtml;

  /* 「通知・通達・社報」、「申請・承認待ち」、「協力会社からのお知らせ」区分 */
  @Schema(name = "kbn", description = "「通知・通達・社報」、「申請・承認待ち」、「協力会社からのお知らせ」区分")
  protected String kbn;

  /* 通知通達件名 */
  @Schema(name = "noticeTitle", description = "通知通達件名")
  protected String noticeTitle;

  /* 業務データステータス */
  @Schema(name = "businessDataSt", description = "業務データステータス")
  protected String businessDataSt;

  /* 申請日時 */
  @Schema(name = "requestDate", description = "申請日時")
  protected String requestDate;

  /** コンストラクタ. */
  public NoticeInfoDto() {}

  /**
   * コンストラクタ.
   *
   * @param kbn 区分: 固定値80 協力会社からのお知らせ
   * @param noticeContentHtml お知らせ内容(HTML)
   */
  public NoticeInfoDto(String kbn, String noticeContentHtml) {
    this.kbn = kbn;
    this.noticeContentHtml = noticeContentHtml;
  }

  /**
   * コンストラクタ.
   *
   * @param id id
   * @param kbn 区分
   * @param noticeTitle 通知通達件名
   * @param requestDate 申請日時
   */
  public NoticeInfoDto(Long id, String kbn, String noticeTitle, String requestDate) {
    this.id = id;
    this.kbn = kbn;
    this.noticeTitle = noticeTitle;
    this.requestDate = requestDate;
  }

  /**
   * コンストラクタ.
   *
   * @param id id
   * @param kbn 区分 固定値4: 申請
   * @param noticeTitle 件名
   * @param businessDataSt 業務データステータス
   * @param requestDate 申請日時
   */
  public NoticeInfoDto(
      Long id, String kbn, String noticeTitle, String businessDataSt, String requestDate) {
    this.id = id;
    this.kbn = kbn;
    this.noticeTitle = noticeTitle;
    this.businessDataSt = businessDataSt;
    this.requestDate = requestDate;
  }
}
