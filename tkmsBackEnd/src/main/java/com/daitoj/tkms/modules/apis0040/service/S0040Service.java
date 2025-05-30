package com.daitoj.tkms.modules.apis0040.service;

import com.daitoj.tkms.modules.apis0040.repository.S0040Repository;
import com.daitoj.tkms.modules.apis0040.service.dto.ExecBgtApprInfoDto;
import com.daitoj.tkms.modules.apis0040.service.dto.S0040ReturnData;
import com.daitoj.tkms.modules.apis0040.service.dto.S0040S01Dto;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
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

/**
 * 承認一覧（実行予算管理）ビジネスロジック.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0040Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0040Service.class);

  /**
   * 承認一覧（実行予算管理）のリポジトリ.
   */
  private final S0040Repository s0040Repository;

  /**
   * メッセージ.
   */
  private final MessageSource messageSource;

  /**
   * コンストラクタ.
   */
  public S0040Service(S0040Repository s0040Repository, MessageSource messageSource) {
    this.s0040Repository = s0040Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（実行予算管理）取得パラメータ
   * @return 承認一覧（実行予算管理）取得結果
   */
  public ApiResult<S0040ReturnData> getInitInfo(S0040S01Dto inDto) {

    try {
      // 承認一覧（実行予算管理）を取得
      List<ExecBgtApprInfoDto> projectSiteApprList =
          s0040Repository.findInitInfo(inDto.getEmpCd());
      // 戻り値
      S0040ReturnData returnData = new S0040ReturnData();

      // 実行予算管理情報リスト
      returnData.setListExecBgtApprInfo(projectSiteApprList);

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
   * @param inDto 承認一覧（実行予算管理）取得パラメータ
   * @return 承認一覧（実行予算管理）取得結果
   */
  public ApiResult<S0040ReturnData> getExecBgtInfo(S0040S01Dto inDto) {
    try {
      // 承認一覧（実行予算管理）を取得
      List<ExecBgtApprInfoDto> execBgtApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains("1")) {
        execBgtApprList.addAll(
            s0040Repository.findExecBgtInfo(
                inDto.getEmpCd(),
                inDto.getConstrSiteCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm()));

        inDto.getListApprStatus().remove("1");
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains("2") || inDto.getListApprStatus().contains("9")) {
        execBgtApprList.addAll(
            s0040Repository.findExecBgtEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getConstrSiteCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm()));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(execBgtApprList)) {
        // メッセージ
        String msg = messageSource.getMessage(Message.MSGID_K00001, null,
            LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      execBgtApprList = execBgtApprList.stream()
          .sorted(Comparator.comparing(ExecBgtApprInfoDto::getConstrSiteCd))
          .collect(Collectors.toList());

      // 戻り値
      S0040ReturnData returnData = new S0040ReturnData();

      returnData.setListExecBgtApprInfo(execBgtApprList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }
}
