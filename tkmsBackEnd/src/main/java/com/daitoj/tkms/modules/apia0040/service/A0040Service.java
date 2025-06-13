package com.daitoj.tkms.modules.apia0040.service;

import com.daitoj.tkms.modules.apia0040.repository.A0040Repository;
import com.daitoj.tkms.modules.apia0040.repository.mapper.A0040Mapper;
import com.daitoj.tkms.modules.apia0040.service.dto.A0040ReturnData;
import com.daitoj.tkms.modules.apia0040.service.dto.NoticeInfoDto;
import com.daitoj.tkms.modules.apia0040.service.dto.NoticeResultInfoDto;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;


/** ダッシュボードサービス. */
@Service
public class A0040Service {

  private static final Logger LOG = LoggerFactory.getLogger(A0040Service.class);

  /* ダッシュボードのリポジトリ */
  private final A0040Repository a0040Repository;

  /* メッセージ */
  private final MessageSource messageSource;

  /* ダッシュボード情報マッピング */
  private final A0040Mapper a0040Mapper;

  /**
   * コンストラクタ.
   *
   * @param a0040Repository ダッシュボードのリポジトリ
   * @param messageSource メッセージ
   * @param a0040Mapper ダッシュボード情報マッピング
   */
  public A0040Service(
      A0040Repository a0040Repository, MessageSource messageSource, A0040Mapper a0040Mapper) {
    this.a0040Repository = a0040Repository;
    this.messageSource = messageSource;
    this.a0040Mapper = a0040Mapper;
  }

  /**
   * ダッシュボードの初期処理.
   *
   * @param accountK アカウント区分
   * @param loginId 申請者コード
   * @return ダッシュボード情報
   */
  public ApiResult<A0040ReturnData> getInitInfo(String accountK, String loginId) {

    try {
      // 協力会社からのお知らせを取得
      List<NoticeInfoDto> listCooperatingNoticeInfo = a0040Repository.findCooperatingNoticeInfo();
      // ダッシュボード情報に変換する
      List<NoticeResultInfoDto> listCooperatingNoticeResultInfo =
          a0040Mapper.toNoticeResultInfo(listCooperatingNoticeInfo);

      // 「通知・通達・社報」「申請・承認待ち」「協力会社からのお知らせ」
      List<NoticeResultInfoDto> listNoticeResultInfo = new ArrayList<>();
      // 申請・承認待ち
      List<NoticeResultInfoDto> listApplyResultInfo = new ArrayList<>();
      // 社員(業者)の場合
      if ("1".equals(accountK)) {
        // 「通知・通達・社報」「申請・承認待ち」「協力会社からのお知らせ」を取得
        List<NoticeInfoDto> listNoticeInfo = a0040Repository.findNoticeInfo(loginId);
        listNoticeResultInfo = limitAndSort(a0040Mapper.toNoticeResultInfo(listNoticeInfo));
        // 「申請・承認待ち」を取得
        List<NoticeInfoDto> listApplyInfo = a0040Repository.findApplyInfo(loginId);
        listApplyResultInfo = limitAndSort(a0040Mapper.toNoticeResultInfo(listApplyInfo));
      }

      // 戻り値
      A0040ReturnData returnData = new A0040ReturnData();
      // 協力会社からのお知らせを設定
      returnData.setListCooperatingNoticeInfo(listCooperatingNoticeResultInfo);
      // 通知・通達・社報を設定
      returnData.setListNoticeInfo(listNoticeResultInfo);
      // 申請・承認待ち
      returnData.setListApplyInfo(listApplyResultInfo);

      // API応答を返却する
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      // エラーログ出力とシステム例外のスロー
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  private List<NoticeResultInfoDto> limitAndSort(List<NoticeResultInfoDto> noticeList) {
    return noticeList.stream()
        .sorted(Comparator.comparing(NoticeInfoDto::getRequestDate).reversed())
        .limit(10)
        .collect(Collectors.toList());
  }

}
