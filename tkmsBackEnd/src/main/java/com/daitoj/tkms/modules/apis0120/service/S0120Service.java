package com.daitoj.tkms.modules.apis0120.service;

import com.daitoj.tkms.modules.apis0120.repository.S0120Repository;
import com.daitoj.tkms.modules.apis0120.service.dto.CustomerApprInfoDto;
import com.daitoj.tkms.modules.apis0120.service.dto.S0120ReturnData;
import com.daitoj.tkms.modules.apis0120.service.dto.S0120S01Dto;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.daitoj.tkms.modules.common.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/** 承認一覧（顧客管理）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0120Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0120Service.class);

  /** 承認一覧（顧客管理）のリポジトリ. */
  private final S0120Repository s0120Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0120Service(S0120Repository s0120Repository, MessageSource messageSource) {
    this.s0120Repository = s0120Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（顧客管理）取得パラメータ
   * @return 承認一覧（顧客管理）取得結果
   */
  public ApiResult<S0120ReturnData> getInitInfo(S0120S01Dto inDto) {

    try {
      // 承認一覧（顧客管理）を取得
      List<CustomerApprInfoDto> customerApprList =
          s0120Repository.findInitInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_C00001,
              MasterData.APPR_ST_1,
              MasterData.BUSINESS_TBL_ID_CUSTOMER);
      // 戻り値
      S0120ReturnData returnData = new S0120ReturnData();

      // 顧客管理情報リスト
      returnData.setListCustomerApprInfo(customerApprList);

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
   * @param inDto 承認一覧（顧客管理）取得パラメータ
   * @return 承認一覧（顧客管理）取得結果
   */
  public ApiResult<S0120ReturnData> getCustomerInfo(S0120S01Dto inDto) {
    try {
      // 承認一覧（顧客管理）を取得
      List<CustomerApprInfoDto> customerApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        customerApprList.addAll(
            s0120Repository.findCustomerInfo(
                inDto.getEmpCd(),
                inDto.getCustomerCd(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00001,
                MasterData.APPR_ST_1,
                MasterData.BUSINESS_TBL_ID_CUSTOMER));

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        customerApprList.addAll(
            s0120Repository.findCustomerEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getCustomerCd(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00001,
                MasterData.BUSINESS_TBL_ID_CUSTOMER));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(customerApprList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      customerApprList =
          customerApprList.stream()
              .sorted(Comparator.comparing(CustomerApprInfoDto::getCustomerCd))
              .collect(Collectors.toList());

      // 案件名検索
      List<CustomerApprInfoDto> searchedList =
          compareKanaItem(customerApprList, inDto.getCustomerNm());
      // 戻り値
      S0120ReturnData returnData = new S0120ReturnData();

      returnData.setListCustomerApprInfo(searchedList);

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
   * @param list 承認一覧（顧客管理）リスト
   * @param customerNm 顧客名
   * @return 検索した承認一覧（顧客管理）リスト
   */
  private List<CustomerApprInfoDto> compareKanaItem(
      List<CustomerApprInfoDto> list, String customerNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            constrSite -> {
              String[] customerNms = constrSite.getCustomerNm().split(":");
              String customerNm1 = customerNms[0];
              String customerNm2 = customerNms[1];
              return TextUtils.matchesIgnoringKanaWidth(customerNm1, customerNm)
                  || TextUtils.matchesIgnoringKanaWidth(customerNm2, customerNm)
                  || TextUtils.matchesIgnoringKanaWidth(constrSite.getCustomerKnNm(), customerNm);
            })
        .peek(constrSite -> constrSite.setCustomerNm(constrSite.getCustomerNm().replace(":", "")))
        .collect(Collectors.toList());
  }
}
