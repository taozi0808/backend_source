package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MTaxRate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 消費税率リポジトリ */
@Repository
public interface MTaxRateRepository extends JpaRepository<MTaxRate, Integer> {
  /**
   * 消費税率を取得
   *
   * @param sysDate システム日付
   * @return 消費税率リスト
   */
  @Query(
      """
            SELECT
               tax
              FROM MTaxRate tax
             WHERE tax.effectiveStartDt <= :sysDate
               AND (tax.effectiveEndDt  IS NULL OR tax.effectiveEndDt   >= :sysDate)
          ORDER BY tax.displayOrder
          """)
  List<MTaxRate> findByTaxRateList(String sysDate);
}
