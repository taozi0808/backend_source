package com.daitoj.tkms.modules.apis0060.service;

import com.daitoj.tkms.modules.apis0040.service.dto.ExecBgtApprInfoDto;
import com.daitoj.tkms.modules.apis0060.repository.S0060Repository;
import com.daitoj.tkms.modules.apis0060.service.dto.ConstrSiteExpApprInfoDto;
import com.daitoj.tkms.modules.apis0060.service.dto.S0060ReturnData;
import com.daitoj.tkms.modules.apis0060.service.dto.S0060S01Dto;
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

/** 承認一覧（現場経費管理）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0060Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0060Service.class);

  /** 承認一覧（現場経費管理）のリポジトリ. */
  private final S0060Repository s0060Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0060Service(S0060Repository s0060Repository, MessageSource messageSource) {
    this.s0060Repository = s0060Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（現場経費管理）取得パラメータ
   * @return 承認一覧（現場経費管理）取得結果
   */
  public ApiResult<S0060ReturnData> getInitInfo(S0060S01Dto inDto) {

    try {
      // 承認一覧（現場経費管理）を取得
      List<ConstrSiteExpApprInfoDto> constrSiteExpApprList =
          s0060Repository.findInitInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_B00006,
              MasterData.APPR_ST_1);
      // 戻り値
      S0060ReturnData returnData = new S0060ReturnData();

      // 現場経費管理情報リスト
      returnData.setListConstrSiteExpApprInfo(constrSiteCdformat(constrSiteExpApprList));

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
   * @param inDto 承認一覧（現場経費管理）取得パラメータ
   * @return 承認一覧（現場経費管理）取得結果
   */
  public ApiResult<S0060ReturnData> getConstrSiteExpInfo(S0060S01Dto inDto) {
    try {
      // 承認一覧（現場経費管理）を取得
      List<ConstrSiteExpApprInfoDto> constrSiteExpApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        constrSiteExpApprList.addAll(
            s0060Repository.findConstrSiteExpInfo(
                inDto.getEmpCd(),
                inDto.getConstrSiteOrProjectCd(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00006,
                MasterData.APPR_ST_1));

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        constrSiteExpApprList.addAll(
            s0060Repository.findConstrSiteExpEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getConstrSiteOrProjectCd(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00006));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(constrSiteExpApprList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      constrSiteExpApprList =
          constrSiteCdformat(
              constrSiteExpApprList.stream()
                  .sorted(Comparator.comparing(ConstrSiteExpApprInfoDto::getConstrSiteOrProjectCd))
                  .collect(Collectors.toList()));
      // 案件名検索
      List<ConstrSiteExpApprInfoDto> searchedList =
          compareKanaItem(constrSiteExpApprList, inDto.getConstrSiteOrProjectNm());

      // 戻り値
      S0060ReturnData returnData = new S0060ReturnData();

      returnData.setListConstrSiteExpApprInfo(searchedList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 現場/案件検索.
   *
   * @param list 承認一覧（実行予算管理）リスト
   * @param constrSiteNm 現場/案件（
   * @return 検索した承認一覧（実行予算管理）リスト
   */
  private List<ConstrSiteExpApprInfoDto> compareKanaItem(
      List<ConstrSiteExpApprInfoDto> list, String constrSiteNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            constrSite ->
                (TextUtils.matchesIgnoringKanaWidth(
                        constrSite.getConstrSiteOrProjectNm(), constrSiteNm)
                    || TextUtils.matchesIgnoringKanaWidth(
                        constrSite.getConstrSiteOrProjectKnNm(), constrSiteNm)))
        .collect(Collectors.toList());
  }

  /**
   * 現場/案件コードフォーマット変換.
   *
   * @param list 承認一覧（現場経費管理）リスト
   * @return 承認一覧（現場経費管理）リスト
   */
  private List<ConstrSiteExpApprInfoDto> constrSiteCdformat(List<ConstrSiteExpApprInfoDto> list) {
    for (ConstrSiteExpApprInfoDto execBgtAppr : list) {
      String constrSiteCd = execBgtAppr.getConstrSiteOrProjectCd();
      if (constrSiteCd == null || constrSiteCd.length() != 12) {
        continue;
      }
      String formatted =
          constrSiteCd.substring(0, 7)
              + "-"
              + constrSiteCd.substring(7, 10)
              + "-"
              + constrSiteCd.substring(10, 12);
      execBgtAppr.setConstrSiteOrProjectCd(formatted);
    }
    return list;
  }
}
