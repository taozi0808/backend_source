package com.daitoj.tkms.modules.common.service;

import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.modules.common.repository.MItemListSettingRepository;
import com.daitoj.tkms.modules.common.repository.mapper.MItemSettingMapper;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.MItemListSettingDto;
import com.daitoj.tkms.modules.common.service.dto.MitemSettingDto;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** マスタデータ情報サービス */
@Service
public class ItemListSettingService {
  private static final Logger LOG = LoggerFactory.getLogger(ItemListSettingService.class);

  /** マスタデータリポジトリ */
  private final MItemListSettingRepository mitemListSettingRepository;

  /** マスタデータのDTOマッパー */
  private final MItemSettingMapper itemSettingMapper;

  /** コンストラクタ */
  public ItemListSettingService(
      MItemListSettingRepository mitemListSettingRepository, MItemSettingMapper itemSettingMapper) {
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.itemSettingMapper = itemSettingMapper;
  }

  /**
   * マスタデータを取得
   *
   * @param itemClassCd 項目分類コード
   * @return マスタデータ
   */
  public ApiResult<MItemListSettingDto> getData(String itemClassCd) {
    try {
      // マスタデータを取得
      List<MItemListSetting> mitemList =
          mitemListSettingRepository
              .findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
                  itemClassCd, DateUtils.formatNow(DateUtils.DATE_FORMAT));

      // マスタデータDtoに変換する
      List<MitemSettingDto> dtoList = itemSettingMapper.toDto(mitemList);

      // 戻り値
      MItemListSettingDto result = new MItemListSettingDto();

      // マスタデータリスト
      result.setItemSettingList(dtoList);

      return ApiResult.success(result);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * マスタデータリストを取得
   *
   * @param itemClassCdList 項目分類コードリスト
   * @return マスタデータリスト
   */
  public ApiResult<List<MItemListSettingDto>> getDataList(String[] itemClassCdList) {
    try {
      // 戻り値
      List<MItemListSettingDto> retList = new ArrayList<>();

      if (itemClassCdList != null) {
        for (String itemClassCd : itemClassCdList) {
          // マスタデータを取得
          List<MItemListSetting> mitemList =
              mitemListSettingRepository
                  .findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
                      itemClassCd, DateUtils.formatNow(DateUtils.DATE_FORMAT));

          // マスタデータDtoに変換する
          List<MitemSettingDto> dtoList = itemSettingMapper.toDto(mitemList);

          // マスタデータ
          MItemListSettingDto itemData = new MItemListSettingDto();
          // 項目分類コード
          itemData.setItemClassCd(itemClassCd);
          // マスタデータリスト
          itemData.setItemSettingList(dtoList);
          // 戻り値設定
          retList.add(itemData);
        }
      }

      return ApiResult.success(retList);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }
}
