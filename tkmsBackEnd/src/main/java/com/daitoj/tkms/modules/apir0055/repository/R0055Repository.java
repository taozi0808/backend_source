package com.daitoj.tkms.modules.apir0055.repository;

import com.daitoj.tkms.domain.MCustomer;
import com.daitoj.tkms.modules.apir0055.service.dto.CustomerInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 顧客情報一覧のリポジトリ. */
@Repository
public interface R0055Repository extends JpaRepository<MCustomer, Long> {

  /**
   * 初期表示データを取得.
   *
   * @return 顧客情報一覧
   */
  @Query(
      """
              SELECT  new com.daitoj.tkms.modules.apir0055.service.dto.CustomerInfoDto(
                      mc.id,
                      mc.customerCd,
                      CONCAT(
                        COALESCE(mc.customerNm1, ''),
                        COALESCE(mc.customerNm2, '')
                      ),
                      mc.customerRyakusyou,
                      mc.customerKnNm,
                      mc.tradingK,
                      mis2.itemValue,
                      mc.gyousyuGyoutai,
                      mis1.itemValue,
                      CONCAT(
                        COALESCE(mc.customerAddr1, ''),
                        COALESCE(mc.customerAddr2, '')
                      ),
                      mc.customerTelNo,
                      mc.ceoNm)
                   FROM MCustomer mc
              LEFT JOIN MItemListSetting  mis1 ON mis1.id.itemClassCd = 'D0019'
                                              AND mis1.id.itemCd = mc.gyousyuGyoutai
              LEFT JOIN MItemListSetting  mis2 ON mis2.id.itemClassCd = 'D0018'
                                              AND mis2.id.itemCd = mc.tradingK
               ORDER BY mc.customerCd
      """)
  List<CustomerInfoDto> findInitInfo();

  /**
   * 顧客情報データを取得.
   *
   * @param customerCd 顧客コード
   * @param tradingK 取引区分
   * @param gyousyuGyoutai 業種・業態
   * @return 顧客情報データ
   */
  @Query(
      """
          SELECT new com.daitoj.tkms.modules.apir0055.service.dto.CustomerInfoDto(
                  mc.id,
                  mc.customerCd,
                  CONCAT(
                    COALESCE(mc.customerNm1, ''),
                    COALESCE(mc.customerNm2, '')
                  ),
                  mc.customerRyakusyou,
                  mc.customerKnNm,
                  mc.tradingK,
                  mis2.itemValue,
                  mc.gyousyuGyoutai,
                  mis.itemValue,
                  CONCAT(
                    COALESCE(mc.customerAddr1, ''),
                    COALESCE(mc.customerAddr2, '')
                  ),
                  mc.customerTelNo,
                  mc.ceoNm)
               FROM MCustomer mc
          LEFT JOIN MItemListSetting  mis    ON mis.id.itemClassCd = 'D0019'
                                            AND mis.id.itemCd = mc.gyousyuGyoutai
          LEFT JOIN MItemListSetting  mis2   ON mis2.id.itemClassCd = 'D0018'
                                            AND mis2.id.itemCd = mc.tradingK
              WHERE (:customerCd     IS NULL OR mc.customerCd               LIKE %:customerCd%)
                AND (:tradingK       IS NULL OR mc.tradingK                 LIKE %:tradingK%)
                AND (:gyousyuGyoutai IS NULL OR mc.gyousyuGyoutai           LIKE %:gyousyuGyoutai%)
           ORDER BY mc.customerCd
      """)
  List<CustomerInfoDto> findCustomerInfo(
      @Param("customerCd") String customerCd,
      @Param("tradingK") String tradingK,
      @Param("gyousyuGyoutai") String gyousyuGyoutai);
}
