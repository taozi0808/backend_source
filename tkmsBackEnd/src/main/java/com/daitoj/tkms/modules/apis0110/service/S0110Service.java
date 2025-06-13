package com.daitoj.tkms.modules.apis0110.service;

import com.daitoj.tkms.modules.apis0110.repository.S0110Repository;
import com.daitoj.tkms.modules.apis0110.service.dto.S0110ReturnData;
import com.daitoj.tkms.modules.apis0110.service.dto.S0110S01Dto;
import com.daitoj.tkms.modules.apis0110.service.dto.SubconNotifApprInfoDto;
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

/** 承認一覧（再下請負通知書）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0110Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0110Service.class);

  /** 承認一覧（再下請負通知書）のリポジトリ. */
  private final S0110Repository s0110Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0110Service(S0110Repository s0110Repository, MessageSource messageSource) {
    this.s0110Repository = s0110Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（再下請負通知書）取得パラメータ
   * @return 承認一覧（再下請負通知書）取得結果
   */
  public ApiResult<S0110ReturnData> getInitInfo(S0110S01Dto inDto) {

    try {
      // 承認一覧（再下請負通知書）を取得
      List<SubconNotifApprInfoDto> subconNotifApprList =
          s0110Repository.findInitInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_B00009,
              MasterData.APPR_ST_1);
      // 戻り値
      S0110ReturnData returnData = new S0110ReturnData();

      // 下請契約台帳情報リスト
      returnData.setListSubconNotifApprInfo(subconNotifApprList);

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
   * @param inDto 承認一覧（再下請負通知書）取得パラメータ
   * @return 承認一覧（再下請負通知書）取得結果
   */
  public ApiResult<S0110ReturnData> getSubconNotifInfo(S0110S01Dto inDto) {
    try {
      // 承認一覧（再下請負通知書）を取得
      List<SubconNotifApprInfoDto> subconNotifApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        subconNotifApprList.addAll(
            s0110Repository.findRequestSubconNotifInfo(
                inDto.getEmpCd(),
                inDto.getParentPartnerVendorCd(),
                inDto.getChildPartnerVendorCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00009,
                MasterData.APPR_ST_1));

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        subconNotifApprList.addAll(
            s0110Repository.findRequestSubconNotifEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getParentPartnerVendorCd(),
                inDto.getChildPartnerVendorCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,

                MasterData.BUSINESS_TYPE_CD_B00009));
      }

      ApiResult<S0110ReturnData> msgId = getS0110ReturnDataApiResult(subconNotifApprList);
      if (msgId != null) {
        return msgId;
      }

      subconNotifApprList =
          subconNotifApprList.stream()
              .sorted(
                  Comparator.comparing(SubconNotifApprInfoDto::getParentPartnerVendorCd)
                      .thenComparing(SubconNotifApprInfoDto::getChildPartnerVendorCd))
              .collect(Collectors.toList());

      // 元請業者検索
      List<SubconNotifApprInfoDto> searchedList =
          parentPartnerVendorKanaItem(subconNotifApprList, inDto.getParentPartnerVendorNm());

      // 下請業者検索
      searchedList =
          childPartnerVendorKanaItem(searchedList, inDto.getChildPartnerVendorNm());

      // 戻り値
      S0110ReturnData returnData = new S0110ReturnData();

      returnData.setListSubconNotifApprInfo(searchedList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  @Nullable
  private ApiResult<S0110ReturnData> getS0110ReturnDataApiResult(
      List<SubconNotifApprInfoDto> subconNotifApprList) {
    // 取得件数が０件だった場合
    if (CollectionUtils.isEmpty(subconNotifApprList)) {
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
   * 元請業者（ｶﾅ含む）検索.
   *
   * @param list 承認一覧（再下請負通知書）リスト
   * @param parentPartnerVendorNm 元請業者
   * @return 検索した承認一覧（再下請負通知書）リスト
   */
  private List<SubconNotifApprInfoDto> parentPartnerVendorKanaItem(
      List<SubconNotifApprInfoDto> list, String parentPartnerVendorNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            subconNotif ->
                (TextUtils.matchesIgnoringKanaWidth(
                        subconNotif.getParentPartnerVendorNm(), parentPartnerVendorNm)
                    || TextUtils.matchesIgnoringKanaWidth(
                        subconNotif.getParentPartnerVendorKnNm(), parentPartnerVendorNm)))
        .collect(Collectors.toList());
  }

  /**
   * 下請業者（ｶﾅ含む）検索.
   *
   * @param list 承認一覧（再下請負通知書）リスト
   * @param childPartnerVendorNm 下請業者
   * @return 検索した承認一覧（再下請負通知書）リスト
   */
  private List<SubconNotifApprInfoDto> childPartnerVendorKanaItem(
      List<SubconNotifApprInfoDto> list, String childPartnerVendorNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            subconNotif ->
                (TextUtils.matchesIgnoringKanaWidth(
                        subconNotif.getChildPartnerVendorNm(), childPartnerVendorNm)
                    || TextUtils.matchesIgnoringKanaWidth(
                        subconNotif.getChildPartnerVendorKnNm(), childPartnerVendorNm)))
        .collect(Collectors.toList());
  }
}
