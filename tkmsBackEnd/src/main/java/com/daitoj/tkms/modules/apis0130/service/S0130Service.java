package com.daitoj.tkms.modules.apis0130.service;

import com.daitoj.tkms.modules.apis0130.repository.S0130Repository;
import com.daitoj.tkms.modules.apis0130.service.dto.EmpApprInfoDto;
import com.daitoj.tkms.modules.apis0130.service.dto.S0130ReturnData;
import com.daitoj.tkms.modules.apis0130.service.dto.S0130S01Dto;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.TextUtils;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/** 承認一覧（会社管理）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0130Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0130Service.class);

  /** 承認一覧（会社管理）のリポジトリ. */
  private final S0130Repository s0130Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0130Service(S0130Repository s0130Repository, MessageSource messageSource) {
    this.s0130Repository = s0130Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（会社管理）取得パラメータ
   * @return 承認一覧（会社管理）取得結果
   */
  public ApiResult<S0130ReturnData> getInitInfo(S0130S01Dto inDto) {

    try {
      // 承認一覧（会社管理）社員管理取得
      List<EmpApprInfoDto> empApprList =
          s0130Repository.findInitEmpInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_C00002,
              MasterData.APPR_ST_1,
              MasterData.BUSINESS_TBL_ID_EMP);
      // 承認一覧（会社管理）組織管理取得
      List<EmpApprInfoDto> orgRevApprList =
          s0130Repository.findInitOrgRevInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_C00003,
              MasterData.APPR_ST_1,
              MasterData.BUSINESS_TBL_ID_ORG_REV);
      // 戻り値
      S0130ReturnData returnData = new S0130ReturnData();

      // 会社管理情報リスト
      List<EmpApprInfoDto> sortedList =
          Stream.concat(
                  Stream.ofNullable(empApprList).flatMap(Collection::stream),
                  Stream.ofNullable(orgRevApprList).flatMap(Collection::stream))
              .sorted(
                  Comparator.comparing(EmpApprInfoDto::getMstName)
                      .thenComparing(EmpApprInfoDto::getEmpCd))
              .collect(Collectors.toList());

      returnData.setListEmpApprInfo(sortedList);

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
   * @param inDto 承認一覧（会社管理）取得パラメータ
   * @return 承認一覧（会社管理）取得結果
   */
  public ApiResult<S0130ReturnData> getEmpInfo(S0130S01Dto inDto) {
    try {
      // 承認一覧（会社管理）を取得
      List<EmpApprInfoDto> requestEmpInfos = new ArrayList<>();
      List<EmpApprInfoDto> requestOrgRevInfos = new ArrayList<>();
      List<EmpApprInfoDto> requestEmpEndInfos = new ArrayList<>();
      List<EmpApprInfoDto> requestOrgRevEndInfos = new ArrayList<>();

      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        requestEmpInfos =
            s0130Repository.findRequestEmpInfo(
                inDto.getEmpCd(),
                inDto.getCodeOrEffectiveStartDate(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00002,
                MasterData.APPR_ST_1,
                MasterData.BUSINESS_TBL_ID_EMP);
        requestOrgRevInfos =
            s0130Repository.findRequestOrgRevInfo(
                inDto.getEmpCd(),
                inDto.getCodeOrEffectiveStartDate(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00003,
                MasterData.APPR_ST_1,
                MasterData.BUSINESS_TBL_ID_ORG_REV);

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        requestEmpEndInfos =
            s0130Repository.findRequestEmpEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getCodeOrEffectiveStartDate(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00002,
                MasterData.BUSINESS_TBL_ID_EMP);
        requestOrgRevEndInfos =
            s0130Repository.findRequestOrgRevEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getCodeOrEffectiveStartDate(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00003,
                MasterData.BUSINESS_TBL_ID_ORG_REV);
      }

      // 会社管理情報リスト
      List<EmpApprInfoDto> sortedList =
          Stream.of(
                  Stream.ofNullable(requestEmpInfos).flatMap(Collection::stream),
                  Stream.ofNullable(requestOrgRevInfos).flatMap(Collection::stream),
                  Stream.ofNullable(requestEmpEndInfos).flatMap(Collection::stream),
                  Stream.ofNullable(requestOrgRevEndInfos).flatMap(Collection::stream))
              .flatMap(Function.identity())
              .sorted(
                  Comparator.comparing(EmpApprInfoDto::getMstName)
                      .thenComparing(EmpApprInfoDto::getEmpCd))
              .collect(Collectors.toList());

      ApiResult<S0130ReturnData> msgId = getS0130ReturnDataApiResult(sortedList);
      if (msgId != null) {
        return msgId;
      }

      // 名称検索
      List<EmpApprInfoDto> searchedList = compareKanaItem(sortedList, inDto.getEmpNm());

      // 戻り値
      S0130ReturnData returnData = new S0130ReturnData();

      returnData.setListEmpApprInfo(searchedList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  @Nullable
  private ApiResult<S0130ReturnData> getS0130ReturnDataApiResult(List<EmpApprInfoDto> empApprList) {
    // 取得件数が０件だった場合
    if (CollectionUtils.isEmpty(empApprList)) {
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
   * 名称検索.
   *
   * @param list 承認一覧（会社管理）リスト
   * @param empNm 名称
   * @return 検索した承認一覧（会社管理）リスト
   */
  private List<EmpApprInfoDto> compareKanaItem(List<EmpApprInfoDto> list, String empNm) {
    if (list == null) {
      return null;
    }

    return list.stream()
        .filter(
            constrSite -> {
              if (StringUtils.isNotBlank(empNm)) {
                return StringUtils.isNotBlank(constrSite.getEmpNm())
                    && (TextUtils.matchesIgnoringKanaWidth(constrSite.getEmpNm(), empNm)
                        || (StringUtils.isNotBlank(constrSite.getEmpKnNm())
                            && TextUtils.matchesIgnoringKanaWidth(constrSite.getEmpKnNm(), empNm)));
              } else {
                return true;
              }
            })
        .collect(Collectors.toList());
  }
}
