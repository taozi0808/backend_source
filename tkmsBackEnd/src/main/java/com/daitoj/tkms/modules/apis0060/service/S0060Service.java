package com.daitoj.tkms.modules.apis0060.service;

import com.daitoj.tkms.modules.apis0060.repository.S0060Repository;
import com.daitoj.tkms.modules.apis0060.service.dto.ConstrSiteExpApprInfoDto;
import com.daitoj.tkms.modules.apis0060.service.dto.S0060ReturnData;
import com.daitoj.tkms.modules.apis0060.service.dto.S0060S01Dto;
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
 * 承認一覧（現場経費管理）ビジネスロジック.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0060Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0060Service.class);

  /**
   * 承認一覧（現場経費管理）のリポジトリ.
   */
  private final S0060Repository s0060Repository;

  /**
   * メッセージ.
   */
  private final MessageSource messageSource;

  /**
   * コンストラクタ.
   */
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
          s0060Repository.findInitInfo(inDto.getEmpCd());
      // 戻り値
      S0060ReturnData returnData = new S0060ReturnData();

      // 現場経費管理情報リスト
      returnData.setListConstrSiteExpApprInfo(constrSiteExpApprList);

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
      if (inDto.getListApprStatus().contains("1")) {
        constrSiteExpApprList.addAll(
            s0060Repository.findConstrSiteExpInfo(
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
        constrSiteExpApprList.addAll(
            s0060Repository.findConstrSiteExpEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getConstrSiteCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm()));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(constrSiteExpApprList)) {
        // メッセージ
        String msg = messageSource.getMessage(Message.MSGID_K00001, null,
            LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      constrSiteExpApprList = constrSiteExpApprList.stream()
          .sorted(Comparator.comparing(ConstrSiteExpApprInfoDto::getConstrSiteCd))
          .collect(Collectors.toList());

      // 戻り値
      S0060ReturnData returnData = new S0060ReturnData();

      returnData.setListConstrSiteExpApprInfo(constrSiteExpApprList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }
}
