package com.daitoj.tkms.modules.apis0020.service;

import com.daitoj.tkms.modules.apis0020.repository.S0020Repository;
import com.daitoj.tkms.modules.apis0020.service.dto.DetailedEstApprInfoDto;
import com.daitoj.tkms.modules.apis0020.service.dto.S0020ReturnData;
import com.daitoj.tkms.modules.apis0020.service.dto.S0020S01Dto;
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
 * 承認一覧（精積算管理）ビジネスロジック.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0020Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0020Service.class);

  /**
   * 承認一覧（精積算管理）のリポジトリ.
   */
  private final S0020Repository s0020Repository;

  /**
   * メッセージ.
   */
  private final MessageSource messageSource;

  /**
   * コンストラクタ.
   */
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
          s0020Repository.findInitInfo(inDto.getEmpCd());
      // 戻り値
      S0020ReturnData returnData = new S0020ReturnData();

      // 精積算管理情報リスト
      returnData.setListDetailedEstApprInfo(detailedEstApprList);

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
      if (inDto.getListApprStatus().contains("1")) {
        detailedEstApprList.addAll(
            s0020Repository.findRequestDetailedEstInfo(
                inDto.getEmpCd(),
                inDto.getDetailedEstCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm()));

        inDto.getListApprStatus().remove("1");
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains("2") || inDto.getListApprStatus().contains("9")) {
        detailedEstApprList.addAll(
            s0020Repository.findRequestDetailedEstEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getDetailedEstCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm()));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(detailedEstApprList)) {
        // メッセージ
        String msg = messageSource.getMessage(Message.MSGID_K00001, null,
            LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      detailedEstApprList = detailedEstApprList.stream()
          .sorted(Comparator.comparing(DetailedEstApprInfoDto::getDetailedEstCd))
          .collect(Collectors.toList());

      // 戻り値
      S0020ReturnData returnData = new S0020ReturnData();

      returnData.setListDetailedEstApprInfo(detailedEstApprList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }
}
