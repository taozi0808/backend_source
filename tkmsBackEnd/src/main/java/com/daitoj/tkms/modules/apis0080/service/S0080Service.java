package com.daitoj.tkms.modules.apis0080.service;

import com.daitoj.tkms.modules.apis0080.repository.S0080Repository;
import com.daitoj.tkms.modules.apis0080.service.dto.PartnerVendorApprInfoDto;
import com.daitoj.tkms.modules.apis0080.service.dto.S0080ReturnData;
import com.daitoj.tkms.modules.apis0080.service.dto.S0080S01Dto;
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

/** 承認一覧（協力業者管理）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0080Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0080Service.class);

  /** 承認一覧（協力業者管理）のリポジトリ. */
  private final S0080Repository s0080Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0080Service(S0080Repository s0080Repository, MessageSource messageSource) {
    this.s0080Repository = s0080Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（協力業者管理）取得パラメータ
   * @return 承認一覧（協力業者管理）取得結果
   */
  public ApiResult<S0080ReturnData> getInitInfo(S0080S01Dto inDto) {

    try {
      // 承認一覧（協力業者管理）を取得
      List<PartnerVendorApprInfoDto> partnerVendorApprList =
          s0080Repository.findInitInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_C00004,
              MasterData.APPR_ST_1);
      // 戻り値
      S0080ReturnData returnData = new S0080ReturnData();

      // 協力業者管理情報リスト
      returnData.setListPartnerVendorApprInfo(partnerVendorApprList);

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
   * @param inDto 承認一覧（協力業者管理）取得パラメータ
   * @return 承認一覧（協力業者管理）取得結果
   */
  public ApiResult<S0080ReturnData> getPartnerVendorInfo(S0080S01Dto inDto) {
    try {
      // 承認一覧（協力業者管理）を取得
      List<PartnerVendorApprInfoDto> partnerVendorApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        partnerVendorApprList.addAll(
            s0080Repository.findPartnerVendorInfo(
                inDto.getEmpCd(),
                inDto.getPartnerVendorCd(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00004,
                MasterData.APPR_ST_1));

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        partnerVendorApprList.addAll(
            s0080Repository.findPartnerVendorEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getPartnerVendorCd(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00004));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(partnerVendorApprList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }
      // 協力業者名（ｶﾅ含む）
      List<PartnerVendorApprInfoDto> searchedList =
          compareKanaItem(partnerVendorApprList, inDto.getPartnerVendorNm()).stream()
              .sorted(Comparator.comparing(PartnerVendorApprInfoDto::getPartnerVendorCd))
              .collect(Collectors.toList());

      // 戻り値
      S0080ReturnData returnData = new S0080ReturnData();

      returnData.setListPartnerVendorApprInfo(searchedList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 協力業者名検索.
   *
   * @param list 承認一覧（協力業者管理）リスト
   * @param partnerVendorNm 協力業者名（
   * @return 検索した承認一覧（協力業者管理）リスト
   */
  private List<PartnerVendorApprInfoDto> compareKanaItem(
      List<PartnerVendorApprInfoDto> list, String partnerVendorNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            constrSite ->
                (TextUtils.matchesIgnoringKanaWidth(constrSite.getCompNm(), partnerVendorNm)
                        || TextUtils.matchesIgnoringKanaWidth(
                            constrSite.getCompKnNm(), partnerVendorNm))
                    && (TextUtils.matchesIgnoringKanaWidth(
                            constrSite.getBranchNm(), partnerVendorNm)
                        || TextUtils.matchesIgnoringKanaWidth(
                            constrSite.getBranchKnNm(), partnerVendorNm)))
        .collect(Collectors.toList());
  }
}
