package com.daitoj.tkms.modules.apis0070.service;

import com.daitoj.tkms.modules.apis0070.repository.S0070Repository;
import com.daitoj.tkms.modules.apis0070.service.dto.ConstrWbsApprInfoDto;
import com.daitoj.tkms.modules.apis0070.service.dto.S0070ReturnData;
import com.daitoj.tkms.modules.apis0070.service.dto.S0070S01Dto;
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

/** 承認一覧（工事予実管理）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0070Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0070Service.class);

  /** 承認一覧（工事予実管理）のリポジトリ. */
  private final S0070Repository s0070Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0070Service(S0070Repository s0070Repository, MessageSource messageSource) {
    this.s0070Repository = s0070Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（工事予実管理）取得パラメータ
   * @return 承認一覧（工事予実管理）取得結果
   */
  public ApiResult<S0070ReturnData> getInitInfo(S0070S01Dto inDto) {

    try {
      // 承認一覧（工事予実管理）を取得
      List<ConstrWbsApprInfoDto> constrWbsApprList =
          s0070Repository.findInitInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_B00007,
              MasterData.APPR_ST_1);
      // 戻り値
      S0070ReturnData returnData = new S0070ReturnData();

      // 工事予実管理情報リスト
      returnData.setListConstrWbsApprInfo(constrSiteCdformat(constrWbsApprList));

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
   * @param inDto 承認一覧（工事予実管理）取得パラメータ
   * @return 承認一覧（工事予実管理）取得結果
   */
  public ApiResult<S0070ReturnData> getConstrWbsInfo(S0070S01Dto inDto) {
    try {
      // 承認一覧（工事予実管理）を取得
      List<ConstrWbsApprInfoDto> constrWbsApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        constrWbsApprList.addAll(
            s0070Repository.findConstrWbsInfo(
                inDto.getEmpCd(),
                inDto.getConstrSiteCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00007,
                MasterData.APPR_ST_1));

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        constrWbsApprList.addAll(
            s0070Repository.findConstrWbsEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getConstrSiteCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00007));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(constrWbsApprList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      constrWbsApprList =
          constrSiteCdformat(
              constrWbsApprList.stream()
                  .sorted(Comparator.comparing(ConstrWbsApprInfoDto::getConstrSiteCd))
                  .collect(Collectors.toList()));
      // 現場名検索
      List<ConstrWbsApprInfoDto> searchedList =
          compareKanaItem(constrWbsApprList, inDto.getConstrSiteNm());
      // 戻り値
      S0070ReturnData returnData = new S0070ReturnData();

      returnData.setListConstrWbsApprInfo(searchedList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 現場名検索.
   *
   * @param list 承認一覧（工事予実管理）リスト
   * @param constrSiteNm 現場名（
   * @return 検索した承認一覧（工事予実管理）リスト
   */
  private List<ConstrWbsApprInfoDto> compareKanaItem(
      List<ConstrWbsApprInfoDto> list, String constrSiteNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            constrSite ->
                (TextUtils.matchesIgnoringKanaWidth(constrSite.getConstrSiteNm(), constrSiteNm)
                    || TextUtils.matchesIgnoringKanaWidth(
                        constrSite.getConstrSiteKnNm(), constrSiteNm)))
        .collect(Collectors.toList());
  }

  /**
   * 現場コードフォーマット変換.
   *
   * @param list 承認一覧（実行予算管理）リスト
   * @return 承認一覧（実行予算管理）リスト
   */
  private List<ConstrWbsApprInfoDto> constrSiteCdformat(List<ConstrWbsApprInfoDto> list) {
    for (ConstrWbsApprInfoDto execBgtAppr : list) {
      String constrSiteCd = execBgtAppr.getConstrSiteCd();
      if (constrSiteCd == null || constrSiteCd.length() != 12) {
        continue;
      }
      String formatted =
          constrSiteCd.substring(0, 7)
              + "-"
              + constrSiteCd.substring(7, 10)
              + "-"
              + constrSiteCd.substring(10, 12);
      execBgtAppr.setConstrSiteCd(formatted);
    }
    return list;
  }
}
