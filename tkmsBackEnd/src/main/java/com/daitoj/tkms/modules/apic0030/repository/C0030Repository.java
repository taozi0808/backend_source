package com.daitoj.tkms.modules.apic0030.repository;

import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.domain.TRoughEstHdr;
import com.daitoj.tkms.modules.apic0030.service.dto.*;

import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 概算一覧のリポジトリ */
@Repository
public interface C0030Repository extends JpaRepository<TRoughEstHdr, Long> {

  /**
   * 初期表示データ取得
   *
   * @param effectiveStartDt サーバー.システム日付
   * @return 部門リスト
   */
  @Query(
      """
             SELECT new com.daitoj.tkms.modules.apic0030.service.dto.OrgRevSearchInfoDto(
                    mo.id,
                    mo.orgCd,
                    mo.orgNm
                 )
              FROM MOrgRev mor
         INNER JOIN MOrg mo
                ON mo.orgRevId = mor.id
             WHERE COALESCE(:effectiveStartDt, mor.effectiveStartDt) > mor.effectiveStartDt
          ORDER BY mo.displayOrder
        """)
  List<OrgRevSearchInfoDto> getOrgRevSearchInfoDto(String effectiveStartDt);

  /**
   * 役職コードり、従業員情報を取得する
   *
   * @param empNm 従業員
   * @return 従業員情報
   */
  @Query(
      """
                  SELECT new com.daitoj.tkms.modules.apic0030.service.dto.MEmpInfoDto(
                         emp.empCd,
                         emp.empNm
                       )
                    FROM MEmp emp
                   WHERE (:empNm is null OR emp.empNm like %:empNm%)
                ORDER BY emp.empCd
              """)
  List<MEmpInfoDto> findByEmpNm(String empNm);

  /**
   * 単価地域情報取得
   *
   * @param userPgK 利用機能区分
   * @return 地域リスト
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apic0030.service.dto.RegionInfoDto(
                   mpr.regionCd,
                   mpr.regionNm
                  )
               FROM MPriceRegion mpr
              WHERE mpr.userPgK     = :userPgK
              ORDER BY mpr.regionCd
          """)
  List<RegionInfoDto> getRegionInfo(String userPgK);

  /**
   * 初期表示データ取得
   *
   * @param id 概算ヘッダID
   * @return 概算明細リスト
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apic0030.service.dto.RoughEstDtlInfoDto(
                   tred.id,
                   tred.majorWorkCd,
                   mmaw.majorWorkNm,
                   tred.minorWorkCd,
                   mmiw.minorWorkNm,
                   tred.spec,
                   tred.qty,
                   tred.price,
                   tred.unitK,
                   tred.roughEstAmt ,
                   tred.remarks,
                   tred.vendorEstNo,
                   tred.partnerVendorCd,
                   mv.compNm
                )
             FROM TRoughEstDtl tred
        LEFT JOIN MVendor mv
               ON mv.partnerVendorCd = tred.partnerVendorCd
       INNER JOIN MMajorWork mmaw
               ON mmaw.majorWorkCd = tred.majorWorkCd
       INNER JOIN MMinorWork mmiw
               ON mmiw.id.majorWorkCd  = mmaw.majorWorkCd
              AND mmiw.id.minorWorkCd  = tred.minorWorkCd
            WHERE (:id IS NULL OR tred.roughEstHid.id = :id)
         ORDER BY tred.seqNo
       """)
  List<RoughEstDtlInfoDto> getRoughEstDtlInfoDto(Long id);

  /**
   * 初期表示データ取得
   *
   * @param id 案件ID
   * @return 請求条件リスト
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apic0030.service.dto.ProjectPaymentTermsInfoDto(
                   tppt.paymentTermsCd.paymentTermsCd,
                   mpt.paymentTermsNm,
                   tppt.paymentRatio,
                   tppt.exclTaxPaymentAmt,
                   tppt.inclTaxPaymentAmt,
                   tppt.taxRate.id,
                   mtr.taxRate,
                   tppt.salesTaxAmt
                )
             FROM TProjectPaymentTerms tppt
       INNER JOIN MPaymentTerm mpt  ON mpt = tppt.paymentTermsCd
       LEFT  JOIN MTaxRate mtr ON mtr  = tppt.taxRate
            WHERE (:id IS NULL OR tppt.project.id = :id)
         ORDER BY tppt.seqNo
        """)
  List<ProjectPaymentTermsInfoDto> getProjectPaymentTermsInfoDto(Long id);

  /**
   * 初期表示データ取得
   *
   * @param id 概算ヘッダID
   * @return 添付ファイルリスト
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apic0030.service.dto.RoughEstFileDtlInfoDto(
                   trefd.file.id,
                   CONCAT(tf.fileNm,'.', tf.fileExt),
                   trefd.roughEstHid.id
                )
             FROM TRoughEstFileDtl trefd
       INNER JOIN TFifle tf  ON tf.id = trefd.file.id
            WHERE (:id IS NULL OR trefd.roughEstHid.id = :id)
         ORDER BY trefd.seqNo
        """)
  List<RoughEstFileDtlInfoDto> getRoughEstFileDtlInfoDto(Long id);

  /**
   * 初期表示データ取得 概算情報
   *
   * @param roughEstCd 概算コード
   * @return 添付ファイルリスト
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apic0030.service.dto.RoughInfoDto(
                treh.id,
                treh.roughEstCd,
                treh.hisNo,
                tp.id,
                tp.projectCd,
                tp.projectNm,
                tp.projectKnNm,
                treh.roughEstYmd,
                treh.roughEstOrgId,
                mo1.orgNm,
                treh.roughEstPicCd,
                me1.empNm,
                mpr.regionCd,
                mpr.regionNm,
                treh.constrSiteK,
                mils1.itemValue,
                substring(tp.postNo,1,3),
                substring(tp.postNo,4,4),
                tp.constrSiteAddr1,
                tp.constrSiteAddr2,
                mc.customerCd,
                CONCAT(mc.customerNm1, mc.customerNm2),
                substring(mc.customerPostNo,1,3),
                substring(mc.customerPostNo,4,4),
                mc.customerAddr1,
                mc.customerAddr2,
                tp.govPeoK,
                mils2.itemValue,
                TO_CHAR(tp.estSubmitDueTs, 'YYYYMMDD'),
                tp.siteArea,
                tp.buildingArea,
                tp.grossFloorArea,
                tp.buildupArea,
                tp.occupiedArea,
                tp.households,
                tp.floorCnt,
                tp.basementCnt,
                tp.orderExpectedYmd,
                tp.startHopeYmd,
                tp.compHopeYmd,
                tp.salesOrgId,
                mo2.orgNm,
                tp.salesMgrCd,
                me2.empNm,
                tp.salesPicCd,
                me3.empNm,
                tp.designVendorNm,
                tp.designPicNm,
                treh.roughEstTotalAmt,
                tbna.businessDataSt
                )
             FROM TRoughEstHdr treh
       INNER JOIN TProject tp  ON tp.projectCd = treh.projectCd
       INNER JOIN MCustomer mc ON mc.customerBranchCd  = tp.customerBranchCd
       LEFT  JOIN TBusinessNewestAppr tbna ON tbna.businessDataId = treh.id
              AND tbna.businessTblId = "T_ROUGH_EST_HDR"
        LEFT JOIN MOrg mo1
               on treh.roughEstOrgId = mo1.id
        LEFT JOIN MEmp me1
               on treh.roughEstPicCd = me1.empCd
        LEFT JOIN MPriceRegion mpr
               on treh.priceRegion.id = mpr.id
        LEFT JOIN MItemListSetting mils1
               on mils1.id.itemClassCd = 'D0005'
              and treh.constrSiteK = mils1.id.itemCd
        LEFT JOIN MItemListSetting mils2
               on mils2.id.itemClassCd = 'D0006'
              and tp.govPeoK = mils2.id.itemCd
        LEFT JOIN MOrg mo2
              on tp.salesOrgId = mo2.id
        LEFT JOIN MEmp me2
               on tp.salesMgrCd = me2.empCd
        LEFT JOIN MEmp me3
               on tp.salesPicCd = me3.empCd
            WHERE treh.roughEstCd = :roughEstCd
        """)
  Optional<RoughInfoDto> getRoughInfoDto(String roughEstCd);

  /**
   * 単価情報取得
   *
   * @param userPgK 利用機能区分
   * @return 単価リスト
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apic0030.service.dto.MPriceRegionInfoDto(
                   mpr.id,
                   mpr.regionCd,
                   mpr.regionNm,
                   mp.majorWorkCd.majorWorkCd,
                   mmaw.majorWorkNm,
                   mmaw.displayOrder,
                   mp.minorWorkCd,
                   mmiw.minorWorkNm,
                   mmiw.displayOrder,
                   mp.spec,
                   mp.price,
                   mp.unitK
                  )
               FROM MPriceRegion mpr
          LEFT JOIN MPriceRegionPrice mprp ON mpr                 = mprp.priceRegion
          LEFT JOIN MPrice mp              ON mp                  = mprp.price
          LEFT JOIN MMajorWork mmaw        ON mmaw                = mp.majorWorkCd
          LEFT JOIN MMinorWork mmiw        ON mmiw.id.minorWorkCd = mp.minorWorkCd
              WHERE mpr.userPgK     = :userPgK
                AND mpr.regionCd    = :regionCd
                AND mpr.constrSiteK = :constrSiteK
          """)
  List<MPriceRegionInfoDto> getMPriceInfo(String userPgK, String regionCd, String constrSiteK);
}
