package com.daitoj.tkms.modules.apis0030.service;

import com.daitoj.tkms.modules.apis0030.repository.S0030Repository;
import com.daitoj.tkms.modules.apis0030.service.dto.ProjectSiteApprInfoDto;
import com.daitoj.tkms.modules.apis0030.service.dto.S0030ReturnData;
import com.daitoj.tkms.modules.apis0030.service.dto.S0030S01Dto;
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

/** 承認一覧（物件管理）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0030Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0030Service.class);

  /** 承認一覧（物件管理）のリポジトリ. */
  private final S0030Repository s0030Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0030Service(S0030Repository s0030Repository, MessageSource messageSource) {
    this.s0030Repository = s0030Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（物件管理）取得パラメータ
   * @return 承認一覧（物件管理）取得結果
   */
  public ApiResult<S0030ReturnData> getInitInfo(S0030S01Dto inDto) {

    try {
      // 承認一覧（物件管理）を取得
      List<ProjectSiteApprInfoDto> projectSiteApprList =
          s0030Repository.findInitInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_B00003,
              MasterData.APPR_ST_1);
      // 戻り値
      S0030ReturnData returnData = new S0030ReturnData();

      // 物件管理情報リスト
      returnData.setListProjectSiteApprInfo(projectSiteApprList);

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
   * @param inDto 承認一覧（物件管理）取得パラメータ
   * @return 承認一覧（物件管理）取得結果
   */
  public ApiResult<S0030ReturnData> getProjectSiteInfo(S0030S01Dto inDto) {
    try {
      // 承認一覧（物件管理）を取得
      List<ProjectSiteApprInfoDto> projectSiteApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        projectSiteApprList.addAll(
            s0030Repository.findProjectSiteInfo(
                inDto.getEmpCd(),
                inDto.getProjectSiteCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00003,
                MasterData.APPR_ST_1,
                MasterData.BUSINESS_TBL_ID_PROJECT_SITE));

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        projectSiteApprList.addAll(
            s0030Repository.findProjectSiteEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getProjectSiteCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00003));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(projectSiteApprList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      projectSiteApprList =
          projectSiteApprList.stream()
              .sorted(Comparator.comparing(ProjectSiteApprInfoDto::getProjectSiteCd))
              .collect(Collectors.toList());
      // 物件名検索
      List<ProjectSiteApprInfoDto> searchedList =
          compareKanaItem(projectSiteApprList, inDto.getProjectSiteNm());
      // 戻り値
      S0030ReturnData returnData = new S0030ReturnData();

      returnData.setListProjectSiteApprInfo(searchedList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 物件名検索.
   *
   * @param list 承認一覧（物件管理）スト
   * @param projectSiteNm 物件名
   * @return 検索した承認一覧（物件管理）リスト
   */
  private List<ProjectSiteApprInfoDto> compareKanaItem(
      List<ProjectSiteApprInfoDto> list, String projectSiteNm) {
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
