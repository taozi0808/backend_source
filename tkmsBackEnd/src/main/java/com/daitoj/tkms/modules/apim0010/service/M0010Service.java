package com.daitoj.tkms.modules.apim0010.service;

import com.daitoj.tkms.modules.apim0010.repository.M0010Repository;
import com.daitoj.tkms.modules.apim0010.service.dto.GenbakeihiInfoDto;
import com.daitoj.tkms.modules.apim0010.service.dto.M0010ReturnData;
import com.daitoj.tkms.modules.apim0010.service.dto.M0010S01Dto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.PaginationMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** 現場経費一覧ビジネスロジック */
@Service
public class M0010Service {

  private static final Logger LOG = LoggerFactory.getLogger(M0010Service.class);

  /** 現場経費一覧リポジトリ */
  private final M0010Repository m0010Repository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** コンストラクタ */
  public M0010Service(M0010Repository m0010Repository, MessageSource messageSource) {
    this.m0010Repository = m0010Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示
   *
   * @param inDto 現場経費情報取得パラメータ
   * @return 現場経費情報取得結果
   */
  public ApiResult<M0010ReturnData> getInitInfo(M0010S01Dto inDto, Pageable pageable) {
    try {

      // 現場経費情報を取得
      Page<GenbakeihiInfoDto> genbakeihiList =
          inDto.getBelongOfficeCd().equals(CommonConstants.OFFICE_HONSHA_CODE)
              ? m0010Repository.findInitInfo(inDto.getBelongOfficeCd(), null, Pageable.unpaged())
              : m0010Repository.findInitInfo(
                  inDto.getBelongOfficeCd(), CommonConstants.OFFICE_HONSHA_CODE, Pageable.unpaged());

      // 戻り値
      M0010ReturnData returnData = new M0010ReturnData();
      // 現場経費情報リスト
      returnData.setListGenbakeihiInfo(genbakeihiList.getContent());

      // ページ情報
      PaginationMeta meta = new PaginationMeta(genbakeihiList);

      return ApiResult.success(returnData, meta);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 検索処理
   *
   * @param inDto 現場経費情報取得パラメータ
   * @return 現場経費情報取得結果
   */
  public ApiResult<M0010ReturnData> getGenbakeihiInfo(M0010S01Dto inDto, Pageable pageable) {
    try {
      // 現場経費情報を取得
      Page<GenbakeihiInfoDto> genbakeihiList =
          inDto.getBelongOfficeCd().equals(CommonConstants.OFFICE_HONSHA_CODE)
              ? m0010Repository.findGenbakeihiInfo(
                  inDto.getBelongOfficeCd(),
                  null,
                  inDto.getGenbaCode(),
                  inDto.getGenbaName(),
                  inDto.getShinseisyaCode(),
                  inDto.getGcYmdStart() == null ? null : inDto.getGcYmdStart().replace("-", ""),
                  inDto.getGcYmdEnd() == null ? null : inDto.getGcYmdEnd().replace("-", ""),
                  inDto.getGhYmdStart() == null ? null : inDto.getGhYmdStart().replace("-", ""),
                  inDto.getGhYmdEnd() == null ? null : inDto.getGhYmdEnd().replace("-", ""),
                  Pageable.unpaged())
              : m0010Repository.findGenbakeihiInfo(
                  inDto.getBelongOfficeCd(),
                  CommonConstants.OFFICE_HONSHA_CODE,
                  inDto.getGenbaCode(),
                  inDto.getGenbaName(),
                  inDto.getShinseisyaCode(),
                  inDto.getGcYmdStart() == null ? null : inDto.getGcYmdStart().replace("-", ""),
                  inDto.getGcYmdEnd() == null ? null : inDto.getGcYmdEnd().replace("-", ""),
                  inDto.getGhYmdStart() == null ? null : inDto.getGhYmdStart().replace("-", ""),
                  inDto.getGhYmdEnd() == null ? null : inDto.getGhYmdEnd().replace("-", ""),
                  Pageable.unpaged());

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(genbakeihiList.getContent())) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      // 戻り値
      M0010ReturnData returnData = new M0010ReturnData();
      // 案件情報リスト
      returnData.setListGenbakeihiInfo(genbakeihiList.getContent());

      // ページ情報
      PaginationMeta meta = new PaginationMeta(genbakeihiList);

      return ApiResult.success(returnData, meta);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }
}
