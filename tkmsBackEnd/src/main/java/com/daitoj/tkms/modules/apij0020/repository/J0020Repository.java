package com.daitoj.tkms.modules.apij0020.repository;

import com.daitoj.tkms.domain.TConstrSiteExpDtl;
import com.daitoj.tkms.modules.apij0020.service.dto.KeihiShiharaiInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 概算一覧のリポジトリ */
@Repository
public interface J0020Repository extends JpaRepository<TConstrSiteExpDtl, Long> {

  /**
   * 初期表示データ取得
   *
   * @return 概算一覧
   */
  @Query(
      """
         SELECT new com.daitoj.tkms.modules.apij0020.service.dto.KeihiShiharaiInfoDto(
            tcsed.id,
            tcseh.constrSiteCd,
            tcs.constrSiteNm,
            tcseh.projectCd,
            tp.projectNm,
            tcsed.expPaymentD,
            tcsed.payeeK,
            mils1.itemValue,
            tcsed.payeePartnerVendorCd,
            mv.compNm,
            tcsed.payeeEmpCd,
            me.empNm,
            tcsed.remarks,
            tcsed.expItemCd,
            mils2.itemValue,
            tcsed.majorWorkCd,
            mmaw.majorWorkNm,
            tcsed.minorWorkCd,
            mmiw.minorWorkNm,
            tcsed.inclTaxPaymentAmt,
            tcsed.remarks
             )
          FROM TConstrSiteExpDtl  tcsed
    INNER JOIN TConstrSiteExpHdr  tcseh   ON tcsed.constrSiteExpHid.id    = tcseh.id
    INNER JOIN TProject tp   ON tp.projectCd    = tcseh.projectCd
    INNER JOIN TConstrSite  tcs   ON tcs.constrSiteCd    = tcseh.constrSiteCd
    INNER JOIN MItemListSetting  mils1   ON mils1.id.itemClassCd = 'D0008'
                                AND mils1.id.itemCd = tcsed.payeeK
                                AND mils1.id.effectiveStartDt <= :sysDate
    INNER JOIN MItemListSetting  mils2   ON mils2.id.itemClassCd = 'D0009'
                                AND mils2.id.itemCd = tcsed.expItemCd
                                AND mils2.id.effectiveStartDt <= :sysDate
    INNER JOIN MMajorWork  mmaw  ON mmaw.majorWorkCd    = tcsed.majorWorkCd
    INNER JOIN MMinorWork  mmiw  ON mmiw.id.majorWorkCd = tcsed.majorWorkCd
                                AND mmiw.id.minorWorkCd = tcsed.minorWorkCd
    INNER JOIN MVendor     mv    ON mv.partnerVendorCd  = tcsed.payeePartnerVendorCd
    INNER JOIN MEmp     me    ON me.empCd  = tcsed.payeeEmpCd
         WHERE tcsed.payeeK IN ('1','3','4')
           AND (:constrSiteCd IS NULL OR tcseh.constrSiteCd LIKE %:constrSiteCd% OR tp.projectCd LIKE %:constrSiteCd%)
           AND (:constrSiteNm IS NULL OR tcs.constrSiteNm LIKE %:constrSiteNm% OR tcs.constrSiteKnNm LIKE %:constrSiteNm%
               OR tp.projectNm LIKE %:constrSiteNm% OR tp.projectKnNm LIKE %:constrSiteNm% )
           AND (:assessPaymentDStart IS NULL OR :assessPaymentDStart <= tcsed.expPaymentD)
           AND (:assessPaymentDStartEnd IS NULL OR :assessPaymentDStartEnd >= tcsed.expPaymentD)
           AND :paymentDataCreateFlg = tcsed.paymentDataCreateFlg
      """)
  List<KeihiShiharaiInfoDto> getKeihiShiharai(
      @Param("constrSiteCd") String constrSiteCd,
      @Param("constrSiteNm") String constrSiteNm,
      @Param("assessPaymentDStart") String assessPaymentDStart,
      @Param("assessPaymentDStartEnd") String assessPaymentDStartEnd,
      @Param("paymentDataCreateFlg") String paymentDataCreateFlg,
      @Param("sysDate") String sysDate);
}
