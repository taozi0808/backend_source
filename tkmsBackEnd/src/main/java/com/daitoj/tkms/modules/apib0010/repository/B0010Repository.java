package com.daitoj.tkms.modules.apib0010.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apib0010.service.dto.AnkenInfoDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 案件一覧のリポジトリ */
@Repository
public interface B0010Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得
   *
   * @param itemClassCd 項目分類コード
   * @param sysDate システム日付
   * @return 案件一覧
   */
  // TODO
  // ※（ｶﾅ含む）の項目については、半角全角を区別しないこととする。
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apib0010.service.dto.AnkenInfoDto(
                    ah.id,
                    ah.projectCd,
                    ah.hisNo,
                    ah.projectNm,
                    kh.customerCd,
                    CONCAT(kh.customerNm1, kh.customerNm2),
                    ah.expectAmt,
                    ah.constrSiteAddr1,
                    ah.constrSiteAddr2,
                    ah.orderExpectedYmd,
                    ah.startHopeYmd,
                    si.orgCd,
                    si.orgNm,
                    ah.salesPicCd,
                    ji.empNm,
                    ah.progressCd,
                    is.itemDesc)
                FROM TProject ah
          INNER JOIN MCustomer kh        ON ah.customerBranchCd = kh.customerBranchCd
          INNER JOIN MOrg si             ON ah.salesOrgId = si.id
          INNER JOIN MEmp ji             ON ah.salesPicCd = ji.empCd
          INNER JOIN MItemListSetting is ON ah.progressCd = is.id.itemCd
                 AND is.id.itemClassCd       = :itemClassCd
                 AND is.id.effectiveStartDt <= :sysDate
               WHERE ah.orderStCd           <> '9'
            ORDER BY ah.projectCd
          """)
  Page<AnkenInfoDto> findInitInfo(String itemClassCd, String sysDate, Pageable pageable);

  /**
   * 検索処理
   *
   * @param statusList 受注状態リスト
   * @param ankenCode 案件コード
   * @param ankenName 案件名（ｶﾅ含む）
   * @param kokyakuName 顧客名（ｶﾅ含む）
   * @param startDate 受注見込時期（開始）
   * @param endDate 受注見込時期（終了）
   * @param bumonName 営業部門
   * @param tantouName 営業担当者
   * @param sysDate システム日付
   * @return 案件一覧
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apib0010.service.dto.AnkenInfoDto(
                    ah.id,
                    ah.projectCd,
                    ah.hisNo,
                    ah.projectNm,
                    kh.customerCd,
                    CONCAT(kh.customerNm1, kh.customerNm2),
                    ah.expectAmt,
                    ah.constrSiteAddr1,
                    ah.constrSiteAddr2,
                    ah.orderExpectedYmd,
                    ah.startHopeYmd,
                    si.orgCd,
                    si.orgNm,
                    ah.salesPicCd,
                    ji.empNm,
                    ah.progressCd,
                    is.itemDesc)
                FROM TProject ah
          INNER JOIN MCustomer kh        ON ah.customerBranchCd = kh.customerBranchCd
          INNER JOIN MOrg si             ON ah.salesOrgId = si.id
          INNER JOIN MEmp ji             ON ah.salesPicCd = ji.empCd
          INNER JOIN MItemListSetting is ON ah.progressCd = is.id.itemCd
                                        AND is.id.itemClassCd = :itemClassCd
                                        AND is.id.effectiveStartDt <= :sysDate
               WHERE (:ankenCode   IS NULL OR :ankenCode = ''   OR ah.projectCd LIKE %:ankenCode%)
                 AND (:ankenName   IS NULL OR :ankenName = ''   OR ah.projectNm LIKE %:ankenName%
                                           OR ah.projectKnNm    LIKE %:ankenName%)
                 AND ah.orderStCd  IN :statusList
                 AND (:kokyakuName IS NULL OR :kokyakuName = '' OR kh.customerNm1 LIKE %:kokyakuName%
                                           OR kh.customerKnNm   LIKE %:kokyakuName%)
                 AND (:startDate   IS NULL OR :startDate = ''   OR :startDate <= ah.orderExpectedYmd)
                 AND (:endDate     IS NULL OR :endDate = ''     OR ah.orderExpectedYmd <= :endDate)
                 AND (:bumonName   IS NULL OR :bumonName = ''   OR si.orgNm LIKE %:bumonName%)
                 AND (:tantouName  IS NULL OR :tantouName = ''  OR ji.empNm LIKE %:tantouName%)
            ORDER BY ah.projectCd
          """)
  Page<AnkenInfoDto> findAnkenInfo(
      List<String> statusList,
      String itemClassCd,
      String ankenCode,
      String ankenName,
      String kokyakuName,
      String startDate,
      String endDate,
      String bumonName,
      String tantouName,
      String sysDate,
      Pageable pageable);
}
