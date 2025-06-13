package com.daitoj.tkms.modules.apin0030.repository;

import com.daitoj.tkms.domain.TConstrWbsHdr;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S02ReturnData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 工事予実のリポジトリ */
@Repository
public interface N0030S01Repository extends JpaRepository<TConstrWbsHdr, Long> {
  /**
   * 工事予実データ取得
   *
   * @param constrSiteCd 現場コード
   * @return 工事予実データ
   */
  @Query(
      """
          SELECT new com.daitoj.tkms.modules.apin0030.service.dto.N0030S02ReturnData(
            cwh.id,
            cwh.constrSiteCd,
            cwh.createPicCd,
            cwh.wbsCreateDt,
            cs.constrSiteNm,
            cs.constrSiteKnNm,
            cs.constrSiteStartYmd,
            cs.constrSiteDeliveryYmd,
            cwh.hisNo,
            cwh.updTs,
            null)
          FROM
            TConstrWbsHdr cwh
            INNER JOIN TConstrSite cs ON cs.constrSiteCd = cwh.constrSiteCd
          WHERE
            cwh.constrSiteCd = :constrSiteCd
      """)
  N0030S02ReturnData findKoujiYojitsuInfo(String constrSiteCd);

}
