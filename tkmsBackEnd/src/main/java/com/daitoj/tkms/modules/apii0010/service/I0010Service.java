package com.daitoj.tkms.modules.apii0010.service;

import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.modules.apii0010.repository.I0010Repository;
import com.daitoj.tkms.modules.apii0010.service.dto.DekidakaShimyurēsyonInfoDto;
import com.daitoj.tkms.modules.apii0010.service.dto.I0010ReturnData;
import com.daitoj.tkms.modules.apii0010.service.dto.I0010S01Dto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MItemListSettingRepository;
import com.daitoj.tkms.modules.common.repository.mapper.MItemSettingMapper;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.MitemSettingDto;
import com.daitoj.tkms.modules.common.service.dto.PaginationMeta;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** 出来高シュミレーション一覧ビジネスロジック */
@Service
public class I0010Service {

  private static final Logger LOG = LoggerFactory.getLogger(I0010Service.class);

  /** マスタデータリポジトリ */
  private final MItemListSettingRepository mitemListSettingRepository;

  /** 出来高シュミレーション一覧リポジトリ */
  private final I0010Repository i0010Repository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** マスタデータのDTOマッパー */
  private final MItemSettingMapper itemSettingMapper;

  /** コンストラクタ */
  public I0010Service(
      MItemListSettingRepository mitemListSettingRepository,
      I0010Repository i0010Repository,
      MessageSource messageSource,
      MItemSettingMapper itemSettingMapper) {
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.itemSettingMapper = itemSettingMapper;
    this.i0010Repository = i0010Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示
   *
   * @param inDto 出来高シュミレーション情報取得パラメータ
   * @return 出来高シュミレーション情報取得結果
   */
  public ApiResult<I0010ReturnData> getInitInfo(I0010S01Dto inDto, Pageable pageable) {
    try {
      // 出来高シュミレーション情報を取得
      Page<DekidakaShimyurēsyonInfoDto> dekidakaShimyurēsyonList =
          i0010Repository.findInitInfo(pageable);

      // マスタデータを取得
      List<MItemListSetting> mitemList =
          mitemListSettingRepository
              .findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
                  MasterData.ITEM_CLASS_CD_D0005, DateUtils.formatNow(DateUtils.DATE_FORMAT));

      // マスタデータDtoに変換する
      List<MitemSettingDto> dtoList = itemSettingMapper.toDto(mitemList);

      // 戻り値
      I0010ReturnData returnData = new I0010ReturnData();

      // 出来高シュミレーション情報リスト
      returnData.setListDekidakaShimyurēsyonInfo(dekidakaShimyurēsyonList.getContent());

      // マスタデータ(現場区分)
      returnData.setMitemSettingD0005(dtoList);

      // ページ情報
      PaginationMeta meta = new PaginationMeta(dekidakaShimyurēsyonList);

      return ApiResult.success(returnData, meta);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);
      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 検索処理
   *
   * @param inDto 出来高シュミレーション情報取得パラメータ
   * @return 出来高シュミレーション情報取得結果
   */
  public ApiResult<I0010ReturnData> getDekidakaShimyurēsyonInfo(
      I0010S01Dto inDto, Pageable pageable) {
    try {
      // 出来高シュミレーション情報を取得
      Page<DekidakaShimyurēsyonInfoDto> dekidakaShimyurēsyonList =
          i0010Repository.findDekidakaShimyurēsyonInfo(
              inDto.getGenbaCode(),
              inDto.getGenbaName(),
              inDto.getGenbaChakkouYmdStart(),
              inDto.getGenbaChakkouYmdEnd(),
              inDto.getGenbaKankouYmdStart(),
              inDto.getGenbaKankouYmdEnd(),
              pageable);

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(dekidakaShimyurēsyonList.getContent())
          && dekidakaShimyurēsyonList.getTotalElements() > CommonConstants.SEARCH_MAX_COUNT) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_00000, msg);
      }

      // 戻り値
      I0010ReturnData returnData = new I0010ReturnData();
      // 出来高シュミレーション情報リスト
      returnData.setListDekidakaShimyurēsyonInfo(dekidakaShimyurēsyonList.getContent());

      // ページ情報
      PaginationMeta meta = new PaginationMeta(dekidakaShimyurēsyonList);

      return ApiResult.success(returnData, meta);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }
}
