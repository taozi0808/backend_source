package com.daitoj.tkms.modules.apis0140.service;

import com.daitoj.tkms.modules.apis0140.service.dto.S0140ReturnData;
import com.daitoj.tkms.modules.apis0140.service.dto.S0140S01Dto;
import com.daitoj.tkms.modules.apis0140.repository.S0140Repository;
import com.daitoj.tkms.modules.apis0140.service.dto.VendorApprInfoDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/** 承認一覧（自社情報）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0140Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0140Service.class);

  /** 承認一覧（自社情報）のリポジトリ. */
  private final S0140Repository s0140Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0140Service(S0140Repository s0140Repository, MessageSource messageSource) {
    this.s0140Repository = s0140Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（自社情報）取得パラメータ
   * @return 承認一覧（自社情報）取得結果
   */
  public ApiResult<S0140ReturnData> getInitInfo(S0140S01Dto inDto) {

    try {
      // 承認一覧（自社情報）を取得
      List<VendorApprInfoDto> vendorApprList =
          s0140Repository.findInitInfo(
              inDto.getVendorCd(),
              inDto.getWorkerCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_C00004,
              MasterData.APPR_ST_1);
      // 戻り値
      S0140ReturnData returnData = new S0140ReturnData();

      // 自社情報承認情報リスト
      returnData.setListVendorApprInfo(vendorApprList);

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
   * @param inDto 承認一覧（自社情報）取得パラメータ
   * @return 承認一覧（自社情報）取得結果
   */
  public ApiResult<S0140ReturnData> getVendorInfo(S0140S01Dto inDto) {
    try {
      // 承認一覧（自社情報）を取得
      List<VendorApprInfoDto> vendorApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        vendorApprList.addAll(
            s0140Repository.findVendorInfo(
                inDto.getVendorCd(),
                inDto.getPartnerVendorCd(),
                inDto.getWorkerCd(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00004,
                MasterData.APPR_ST_1));

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        vendorApprList.addAll(
            s0140Repository.findVendorEndInfo(
                inDto.getVendorCd(),
                inDto.getPartnerVendorCd(),
                inDto.getWorkerCd(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00001,
                inDto.getListApprStatus()));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(vendorApprList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      vendorApprList =
          vendorApprList.stream()
              .sorted(Comparator.comparing(VendorApprInfoDto::getPartnerVendorCd))
              .collect(Collectors.toList());

      // 案件名検索
      //      List<VendorApprInfoDto> searchedList =
      //          compareKanaItem(customerApprList, inDto.getCustomerNm());
      // 戻り値
      S0140ReturnData returnData = new S0140ReturnData();

      returnData.setListVendorApprInfo(vendorApprList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 顧客名検索.
   *
   * @param list 承認一覧（自社情報）リスト
   * @param customerNm 顧客名
   * @return 検索した承認一覧（自社情報）リスト
   */
  //  private List<VendorApprInfoDto> compareKanaItem(List<VendorApprInfoDto> list, String
  // customerNm) {
  //    if (list == null) {
  //      return null;
  //    }
  //    return list.stream()
  //        .filter(
  //            constrSite -> {
  //              String[] customerNms = constrSite.getCustomerNm().split(":");
  //              String customerNm1 = customerNms[0];
  //              String customerNm2 = customerNms[1];
  //              return TextUtils.matchesIgnoringKanaWidth(customerNm1, customerNm)
  //                  || TextUtils.matchesIgnoringKanaWidth(customerNm2, customerNm)
  //                  || TextUtils.matchesIgnoringKanaWidth(constrSite.getCustomerKnNm(),
  // customerNm);
  //            })
  //        .peek(constrSite -> constrSite.setCustomerNm(constrSite.getCustomerNm().replace(":",
  // "")))
  //        .collect(Collectors.toList());
  //  }
}
