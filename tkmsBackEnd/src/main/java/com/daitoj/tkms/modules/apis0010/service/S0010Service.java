package com.daitoj.tkms.modules.apis0010.service;

import com.daitoj.tkms.modules.apis0010.repository.S0010Repository;
import com.daitoj.tkms.modules.apis0010.service.dto.RoughEstApprInfoDto;
import com.daitoj.tkms.modules.apis0010.service.dto.S0010ReturnData;
import com.daitoj.tkms.modules.apis0010.service.dto.S0010S01Dto;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.EnhancedFullWidthConverterUtils;
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
 * 承認一覧（概算管理）ビジネスロジック.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class S0010Service {

  private static final Logger LOG = LoggerFactory.getLogger(S0010Service.class);

  /**
   * 承認一覧（概算管理）のリポジトリ.
   */
  private final S0010Repository s0010Repository;

  /**
   * メッセージ.
   */
  private final MessageSource messageSource;

  /**
   * コンストラクタ.
   */
  public S0010Service(S0010Repository s0010Repository, MessageSource messageSource) {
    this.s0010Repository = s0010Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @param inDto 承認一覧（概算管理）取得パラメータ
   * @return 承認一覧（概算管理）取得結果
   */
  public ApiResult<S0010ReturnData> getInitInfo(S0010S01Dto inDto) {

    try {
      // 承認一覧（概算管理）を取得
      List<RoughEstApprInfoDto> roughEstApprList = s0010Repository.findInitInfo(inDto.getEmpCd());
      // 戻り値
      S0010ReturnData returnData = new S0010ReturnData();

      // 概算管理情報リスト
      returnData.setListRoughEstApprInfo(roughEstApprList);

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
   * @param inDto 承認一覧（概算管理）取得パラメータ
   * @return 承認一覧（概算管理）取得結果
   */
  @SuppressWarnings("checkstyle:Indentation")
  public ApiResult<S0010ReturnData> getRoughEstApprInfo(S0010S01Dto inDto) {
    try {
      // 承認一覧（概算管理）を取得
      List<RoughEstApprInfoDto> roughEstApprList = new ArrayList<>();
      // 承認状態リストに”1”（承認待）が含まれる場合
      if (inDto.getListApprStatus().contains("1")) {
        roughEstApprList.addAll(
            s0010Repository.findRequestRoughEstInfo(
                inDto.getEmpCd(),
                inDto.getProjectCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm()));

        inDto.getListApprStatus().remove("1");
      }
      // 承認状態リストに”2”（承認済）か”9”（差戻）が含まれる場合
      if (inDto.getListApprStatus().contains("2") || inDto.getListApprStatus().contains("9")) {
        roughEstApprList.addAll(
            s0010Repository.findRequestRoughEstEndInfo(
                inDto.getListApprStatus(),
                inDto.getEmpCd(),
                inDto.getProjectCd(),
                DateUtils.stringToInstant(inDto.getRequestDateFrom()),
                DateUtils.stringToInstant(inDto.getRequestDateTo()),
                inDto.getRequestOfficeNm(),
                inDto.getRequestEmpNm()));
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(roughEstApprList)) {
        // メッセージ
        String msg = messageSource.getMessage(Message.MSGID_K00001, null,
            LocaleContextHolder.getLocale());
        LOG.info(msg);
        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      roughEstApprList = roughEstApprList.stream().peek(dto -> {
            dto.setFullProjectNm(EnhancedFullWidthConverterUtils.convert(dto.getProjectNm()));
            dto.setProjectKnNm(EnhancedFullWidthConverterUtils.convert(dto.getProjectKnNm()));
          }).filter(dto -> {
            if (inDto.getProjectNm() == null || inDto.getProjectNm().isEmpty()) {
              return true;
            }
            if (dto.getFullProjectNm() != null && !dto.getFullProjectNm().isEmpty()) {
              return dto.getFullProjectNm()
                  .contains(EnhancedFullWidthConverterUtils.convert(inDto.getProjectNm()));
            }
            if (dto.getProjectKnNm() != null && !dto.getProjectKnNm().isEmpty()) {
              return dto.getProjectKnNm()
                  .contains(EnhancedFullWidthConverterUtils.convert(inDto.getProjectNm()));
            }
            return true;
          }).sorted(Comparator.comparing(RoughEstApprInfoDto::getProjectCd))
          .collect(Collectors.toList());

      // 戻り値
      S0010ReturnData returnData = new S0010ReturnData();

      returnData.setListRoughEstApprInfo(roughEstApprList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }
}
