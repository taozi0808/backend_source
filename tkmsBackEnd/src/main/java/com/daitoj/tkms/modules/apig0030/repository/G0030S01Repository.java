package com.daitoj.tkms.modules.apig0030.repository;

import com.daitoj.tkms.domain.TExecBgtHdr;
import com.daitoj.tkms.modules.apig0030.service.dto.G0030S02ReturnData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 実行予算のリポジトリ. */
@Repository
public interface G0030S01Repository extends JpaRepository<TExecBgtHdr, Long> {
  /**
   * 実行予算データ取得.
   *
   * @param execBgtCd 実行予算コード
   * @return 実行予算ヘッダデータ
   */
  @Query(
      """
          SELECT new com.daitoj.tkms.modules.apig0030.service.dto.G0030S02ReturnData(
            ebh.id,
            cs.constrSiteCd,
            cs.constrSiteNm,
            cs.constrSiteKnNm,
            ebh.execBgtCd,
            ebh.bgtYmReqDt,
            bna.finalApprDt,
            ebh.bgtCreateOrgId,
            ebh.bgtCreatePicCd,
            e.empNm,
            cs.buildingArea,
            cs.grossFloorArea,
            cs.buildupArea,
            cs.structureK,
            cs.constrSiteStartYmd,
            cs.constrSiteDeliveryYmd,
            cs.memo,
            ebh.updTs
          )
          FROM
            TExecBgtHdr ebh
            INNER JOIN TConstrSite cs ON cs.constrSiteCd = ebh.constrSiteCd
            LEFT JOIN TBusinessNewestAppr bna ON bna.businessTblId = 'T_EXEC_BGT_HDR' AND bna.businessDataId = ebh.id
            LEFT JOIN MEmp e ON e.empCd = ebh.bgtCreatePicCd
          WHERE
            ebh.execBgtCd = :execBgtCd
      """)
  G0030S02ReturnData findJikkouYosanHdrInfo(String execBgtCd);
}
