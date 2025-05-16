package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.domain.MItemListSettingId;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** マスタデータリポジトリ */
@Repository
public interface MItemListSettingRepository
    extends JpaRepository<MItemListSetting, MItemListSettingId> {
  // 項目分類コードキー
  String ITEMLIST_BY_CLASS_CACHE = "findById_ItemClass";
  // 項目分類コード、項目コードキー
  String ITEMLIST_BY_CLASS_CD_CACHE = "findById_ItemClassAndItemCd";

  /**
   * 項目分類コードより、マスタデータを取得する
   *
   * @param itemClassCd 項目分類コード
   * @param effectiveStartDt システム日付
   * @return マスタデータ
   */
  @Cacheable(
      value = ITEMLIST_BY_CLASS_CACHE,
      key = "#itemClassCd",
      unless = "#result == null")
  List<MItemListSetting> findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
      String itemClassCd, String effectiveStartDt);

  /**
   * 項目分類コード、項目コードより、マスタデータを取得する
   *
   * @param itemClassCd 項目分類コード
   * @param itemCd 項目コード
   * @param effectiveStartDt システム日付
   * @return 項目名
   */
  @Cacheable(
      value = ITEMLIST_BY_CLASS_CD_CACHE,
      key = "#itemClassCd + '-' + #itemCd",
      unless = "#result == null")
  Optional<MItemListSetting> findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
      String itemClassCd, String itemCd, String effectiveStartDt);
}
