package com.daitoj.tkms.modules.apis0020.service;

import com.daitoj.tkms.modules.apis0020.repository.S0020Repository;
import com.daitoj.tkms.modules.apis0020.service.dto.DetailedEstApprInfoDto;
import com.daitoj.tkms.modules.apis0020.service.dto.S0020ReturnData;
import com.daitoj.tkms.modules.apis0020.service.dto.S0020S01Dto;
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

/** 承認一覧（精積算管理）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0020Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0020Service.class);

  /** 承認一覧（精積算管理）のリポジトリ. */
  private final S0020Repository s0020Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0020Service(S0020Repository s0020Repository, MessageSource messageSource) {
    this.s0020Repository = s0020Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（精積算管理）取得パラメータ
   * @return 承認一覧（精積算管理）取得結果
   */
  public ApiResult<S0020ReturnData> getInitInfo(S0020S01Dto inDto) {

    try {
      // 承認一覧（精積算管理）を取得
      List<DetailedEstApprInfoDto> detailedEstApprList =
          s0020Repository.findInitInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_B00002,
              MasterData.APPR_ST_1);
      // 戻り値
      S0020ReturnData returnData = new S0020ReturnData();

      // 精積算管理情報リスト
      returnData.setListDetailedEstApprInfo(detailedEstCdformat(detailedEstApprList));

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
   * @param inDto 承認一覧（精積算管理）取得パラメータ
   * @return 承認一覧（精積算管理）取得結果
   */
  public ApiResult<S0020ReturnData> getDetailedEstApprInfo(S0020S01Dto inDto) {
    try {
      // 承認一覧（精積算管理）を取得
      List<DetailedEstApprInfoDto> detailedEstApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        detailedEstApprList.addAll(
            s0020Repository.findRequestDetailedEstInfo(
                inDto.getEmpCd(),
                inDto.getProjectCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00002,
                MasterData.APPR_ST_1));

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        detailedEstApprList.addAll(
            s0020Repository.findRequestDetailedEstEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getProjectCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_B00002));
      }

      ApiResult<S0020ReturnData> msgId = getS0020ReturnDataApiResult(detailedEstApprList);
      if (msgId != null) {
        return msgId;
      }

      detailedEstApprList =
          detailedEstCdformat(
              detailedEstApprList.stream()
                  .sorted(Comparator.comparing(DetailedEstApprInfoDto::getDetailedEstCd))
                  .collect(Collectors.toList()));

      // 案件名検索
      List<DetailedEstApprInfoDto> searchedList =
          compareKanaItem(detailedEstApprList, inDto.getProjectNm());

      // 戻り値
      S0020ReturnData returnData = new S0020ReturnData();

      returnData.setListDetailedEstApprInfo(searchedList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  @Nullable
  private ApiResult<S0020ReturnData> getS0020ReturnDataApiResult(
      List<DetailedEstApprInfoDto> detailedEstApprList) {
    // 取得件数が０件だった場合
    if (CollectionUtils.isEmpty(detailedEstApprList)) {
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
   * 案件名検索.
   *
   * @param list 承認一覧（概算管理）リスト
   * @param projectNm 案件名
   * @return 検索した承認一覧（概算管理）リスト
   */
  private List<DetailedEstApprInfoDto> compareKanaItem(
      List<DetailedEstApprInfoDto> list, String projectNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            constrSite ->
                (TextUtils.matchesIgnoringKanaWidth(constrSite.getProjectNm(), projectNm)
                    || TextUtils.matchesIgnoringKanaWidth(constrSite.getProjectKnNm(), projectNm)))
        .collect(Collectors.toList());
  }

  /**
   * 精積算コードフォーマット変換.
   *
   * @param list 承認一覧（精積算管理）リスト
   * @return 承認一覧（精積算管理）リスト
   */
  private List<DetailedEstApprInfoDto> detailedEstCdformat(List<DetailedEstApprInfoDto> list) {
    for (DetailedEstApprInfoDto execBgtAppr : list) {
      String constrSiteCd = execBgtAppr.getDetailedEstCd();
      if (constrSiteCd == null || constrSiteCd.length() != 12) {
        continue;
      }
      String formatted = constrSiteCd.substring(0, 10) + "-" + constrSiteCd.substring(10, 12);
      execBgtAppr.setDetailedEstCd(formatted);
    }
    return list;
  }
}
