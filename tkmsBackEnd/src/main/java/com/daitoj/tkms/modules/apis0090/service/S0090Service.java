package com.daitoj.tkms.modules.apis0090.service;

import com.daitoj.tkms.modules.apis0020.service.dto.DetailedEstApprInfoDto;
import com.daitoj.tkms.modules.apis0090.repository.S0090Repository;
import com.daitoj.tkms.modules.apis0090.service.dto.S0090ReturnData;
import com.daitoj.tkms.modules.apis0090.service.dto.S0090S01Dto;
import com.daitoj.tkms.modules.apis0090.service.dto.WorkerApprInfoDto;
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

/** 承認一覧（作業員情報管理）ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0090Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0090Service.class);

  /** 承認一覧（作業員情報管理）のリポジトリ. */
  private final S0090Repository s0090Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public S0090Service(S0090Repository s0090Repository, MessageSource messageSource) {
    this.s0090Repository = s0090Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（作業員情報管理）取得パラメータ
   * @return 承認一覧（作業員情報管理）取得結果
   */
  public ApiResult<S0090ReturnData> getInitInfo(S0090S01Dto inDto) {

    try {
      // 承認一覧（作業員情報管理）を取得
      List<WorkerApprInfoDto> workerApprList =
          s0090Repository.findInitInfo(
              inDto.getEmpCd(),
              MasterData.ITEM_CLASS_CD_C0001,
              MasterData.BUSINESS_TYPE_CD_C00005,
              MasterData.APPR_ST_1);
      // 戻り値
      S0090ReturnData returnData = new S0090ReturnData();

      // 作業員情報管理情報リスト
      returnData.setListWorkerApprInfo(workerApprList);

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
   * @param inDto 承認一覧（作業員情報管理）取得パラメータ
   * @return 承認一覧（作業員情報管理）取得結果
   */
  public ApiResult<S0090ReturnData> getWorkerInfo(S0090S01Dto inDto) {
    try {
      // 承認一覧（作業員情報管理）を取得
      List<WorkerApprInfoDto> workerApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_1)) {
        workerApprList.addAll(
            s0090Repository.findWorkerInfo(
                inDto.getEmpCd(),
                inDto.getWorkerCd(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00005,
                MasterData.APPR_ST_1));

        inDto.getListApprStatus().remove(MasterData.APPR_ST_1);
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains(MasterData.APPR_ST_2)
          || inDto.getListApprStatus().contains(MasterData.APPR_ST_9)) {
        workerApprList.addAll(
            s0090Repository.findWorkerEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getWorkerCd(),
                DateUtils.stringToOffsetDateTime(inDto.getRequestDateFrom()),
                DateUtils.stringToEndOfDayOffsetDateTime(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm(),
                MasterData.ITEM_CLASS_CD_C0001,
                MasterData.BUSINESS_TYPE_CD_C00005));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(workerApprList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      // 戻り値
      S0090ReturnData returnData = new S0090ReturnData();

      returnData.setListWorkerApprInfo(
          compareKanaItem(workerApprList, inDto.getWorkerNm()).stream()
              .sorted(Comparator.comparing(WorkerApprInfoDto::getWorkerCd))
              .collect(Collectors.toList()));

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 作業員氏名.
   *
   * @param list 承認一覧（作業員情報管理）リスト
   * @param workerNm 作業員氏名
   * @return 検索した承認一覧（作業員情報管理）リスト
   */
  private List<WorkerApprInfoDto> compareKanaItem(List<WorkerApprInfoDto> list, String workerNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            constrSite ->
                (TextUtils.matchesIgnoringKanaWidth(constrSite.getWorkerNm(), workerNm)
                    || TextUtils.matchesIgnoringKanaWidth(constrSite.getWorkerKnNm(), workerNm)))
        .collect(Collectors.toList());
  }
}
