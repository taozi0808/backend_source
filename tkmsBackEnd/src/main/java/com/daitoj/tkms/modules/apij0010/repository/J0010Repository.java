package com.daitoj.tkms.modules.apij0010.repository;

import com.daitoj.tkms.domain.TAssessDtl;
import com.daitoj.tkms.modules.apij0010.service.dto.SateiShiharaiInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 概算一覧のリポジトリ */
@Repository
public interface J0010Repository extends JpaRepository<TAssessDtl, Long> {

  /**
   * 初期表示データ取得
   *
   * @return 概算一覧
   */
  @Query(
      """
         SELECT new com.daitoj.tkms.modules.apij0010.service.dto.SateiShiharaiInfoDto(
             tal.id,
             tal.partnerVendorCd,
             mv.compNm,
             tah.constrSiteCd,
             tcs.constrSiteNm,
             tal.majorWorkCd,
             mmaw.majorWorkNm,
             tal.minorWorkCd,
             mmiw.minorWorkNm,
             tal.poNo)
          FROM TAssessDtl  tal
    INNER JOIN TAssessHdr  tah   ON tal.assessHid.id    = tah.id
    INNER JOIN TConstrSite tcs   ON tcs.constrSiteCd    = tah.constrSiteCd
    INNER JOIN MMajorWork  mmaw  ON mmaw.majorWorkCd    = tal.majorWorkCd
    INNER JOIN MMinorWork  mmiw  ON mmiw.id.majorWorkCd = tal.majorWorkCd
                                AND mmiw.id.minorWorkCd = tal.minorWorkCd
    INNER JOIN MVendor     mv    ON mv.partnerVendorCd  = tal.partnerVendorCd
         WHERE (:assessYm      IS NULL OR tah.assessYm = :assessYm)
           AND (:partnerVendorCd IS NULL OR mv.partnerVendorCd LIKE %:partnerVendorCd%)
           AND (:compNm IS NULL OR mv.compNm LIKE %:compNm% OR mv.compKnNm LIKE %:compNm%)
           AND (:constrSiteCd IS NULL OR tah.constrSiteCd LIKE %:constrSiteCd%)
           AND (:constrSiteNm IS NULL OR tcs.constrSiteNm LIKE %:constrSiteNm% OR tcs.constrSiteKnNm LIKE %:constrSiteNm%)
           AND (:assessPaymentDStart IS NULL OR :assessPaymentDStart <= tal.assessPaymentD)
           AND (:assessPaymentDStartEnd IS NULL OR :assessPaymentDStartEnd >= tal.assessPaymentD)
           AND :paymentDataCreateFlg = tal.paymentDataCreateFlg
      """)
  List<SateiShiharaiInfoDto> getSateiShiharai(
      @Param("assessYm") String assessYm,
      @Param("partnerVendorCd") String partnerVendorCd,
      @Param("compNm") String compNm,
      @Param("constrSiteCd") String constrSiteCd,
      @Param("constrSiteNm") String constrSiteNm,
      @Param("assessPaymentDStart") String assessPaymentDStart,
      @Param("assessPaymentDStartEnd") String assessPaymentDStartEnd,
      @Param("paymentDataCreateFlg") String paymentDataCreateFlg);
}
