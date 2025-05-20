package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MOrg;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 組織情報リポジトリ */
@Repository
public interface MOrgRepository extends JpaRepository<MOrg, Long> {

  /**
   * 組織情報取得
   *
   * @param sysDate システム日付
   * @return 組織情報
   */
  @Query(
      """
              SELECT org
                FROM MOrgRev rev
          INNER JOIN MOrg org       ON rev.id = org.orgRevId
               WHERE rev.effectiveStartDt <= :sysDate
            ORDER BY org.lvl, org.displayOrder
          """)
  List<MOrg> findBusyos(String sysDate);

    /**
     * 異動組織情報取得
     *
     * @param sysDate システム日付
     * @return 異動組織情報
     */
    @Query(
        """
                SELECT org
                  FROM MOrgRev rev
            INNER JOIN MOrg org       ON rev.id = org.orgRevId
                 WHERE rev.effectiveStartDt > :sysDate
              ORDER BY org.lvl, org.displayOrder
            """)
    List<MOrg> findTransferBusyos(String sysDate);

}
