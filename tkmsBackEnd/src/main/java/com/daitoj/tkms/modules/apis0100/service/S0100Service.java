package com.daitoj.tkms.modules.apis0100.service;

import com.daitoj.tkms.modules.apis0100.repository.S0100Repository;
import com.daitoj.tkms.modules.apis0100.service.dto.S0100ReturnData;
import com.daitoj.tkms.modules.apis0100.service.dto.S0100S01Dto;
import com.daitoj.tkms.modules.apis0100.service.dto.SubConLedgerApprInfoDto;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.TextUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/** 承認一覧（下請契約台帳）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0100Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0100Service.class);

  /** 承認一覧（下請契約台帳）のリポジトリ. */
  private final S0100Repository s0100Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0100Service(S0100Repository s0100Repository, MessageSource messageSource) {
    this.s0100Repository = s0100Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（下請契約台帳）取得パラメータ
   * @return 承認一覧（下請契約台帳）取得結果
   */
  public ApiResult<S0100ReturnData> getInitInfo(S0100S01Dto inDto) {

    try {
      // 承認一覧（下請契約台帳）を取得
      List<SubConLedgerApprInfoDto> subConLedgerApprList =
          s0100Repository.findInitInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_B00008,
              MasterData.APPR_ST_1,
              MasterData.BUSINESS_TBL_ID_SUB_CON_LEDGER);
      // 戻り値
      S0100ReturnData returnData = new S0100ReturnData();

      // 下請契約台帳情報リスト
      returnData.setListSubConLedgerApprInfo(subConLedgerApprList);

      return ApiResult.success(returnData);
    } catch (Exception ex) {

      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 検索処理.
   *
   * @param inDto 承認一覧（下請契約台帳）取得パラメータ
   * @return 承認一覧（下請契約台帳）取得結果
   */
  public ApiResult<S0100ReturnData> getSubConLedgerInfo(S0100S01Dto inDto) {
    try {
      // 承認一覧（下請契約台帳）を取得
      List<SubConLedgerApprInfoDto> subConLedgerApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        subConLedgerApprList.addAll(
            s0100Repository.findRequestSubConLedgerInfo(
                inDto.getEmpCd(),
                inDto.getProjectSiteCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00008,
                MasterData.APPR_ST_1,
                MasterData.BUSINESS_TBL_ID_SUB_CON_LEDGER));

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        subConLedgerApprList.addAll(
            s0100Repository.findRequestSubConLedgerEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getProjectSiteCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00008,
                MasterData.BUSINESS_TBL_ID_SUB_CON_LEDGER));
      }

      ApiResult<S0100ReturnData> msgId = getS0100ReturnDataApiResult(subConLedgerApprList);
      if (msgId != null) {
        return msgId;
      }

      subConLedgerApprList =
          subConLedgerApprList.stream()
              .sorted(Comparator.comparing(SubConLedgerApprInfoDto::getProjectSiteCd))
              .collect(Collectors.toList());

      // 案件名検索
      List<SubConLedgerApprInfoDto> searchedList =
          compareKanaItem(subConLedgerApprList, inDto.getProjectSiteNm());

      // 戻り値
      S0100ReturnData returnData = new S0100ReturnData();

      returnData.setListSubConLedgerApprInfo(searchedList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  @Nullable
  private ApiResult<S0100ReturnData> getS0100ReturnDataApiResult(
      List<SubConLedgerApprInfoDto> subConLedgerApprList) {
    // 取得件数が０件だった場合
    if (CollectionUtils.isEmpty(subConLedgerApprList)) {
      // メッセージ
      String msg =
          messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

      LOG.info(msg);

      // 結果情報
      return ApiResult.error(Message.MSGID_K00001, msg);
    }
    return null;
  }

  /**
   * 物件名検索.
   *
   * @param list 承認一覧（下請契約台帳）リスト
   * @param projectSiteNm 物件名
   * @return 検索した承認一覧（下請契約台帳）リスト
   */
  private List<SubConLedgerApprInfoDto> compareKanaItem(
      List<SubConLedgerApprInfoDto> list, String projectSiteNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            constrSite ->
                (TextUtils.matchesIgnoringKanaWidth(constrSite.getProjectSiteNm(), projectSiteNm)
                    || TextUtils.matchesIgnoringKanaWidth(
                        constrSite.getProjectSiteKnNm(), projectSiteNm)))
        .collect(Collectors.toList());
  }
}
