package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TBusinessNewestAppr;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 業務データ最新承認情報リポジトリ */
@Repository
public interface TBusinessNewestApprRepository extends JpaRepository<TBusinessNewestAppr, Long> {

  /**
   * 業務テーブルID、業務データIDをキーに、業務データ最新承認情報テーブルを検索
   *
   * @param businessTblId 業務テーブルID
   * @param businessDataId 業務データID
   * @return 業務データ最新承認情報
   */
  Optional<TBusinessNewestAppr> findByBusinessTblIdAndBusinessDataId(
      String businessTblId, Long businessDataId);

  /**
   * 業務テーブルID、業務データIDをキーに、業務データ最新承認情報テーブルを検索
   *
   * @param businessTblId 業務テーブルID
   * @param businessDataId 業務データID
   * @param businessDataId 業務データステータス
   * @return 業務データ最新承認情報
   */
  Optional<TBusinessNewestAppr> findByBusinessTblIdAndBusinessDataIdAndBusinessDataSt(
      String businessTblId, Long businessDataId, String businessDataSt);

  /**
   * 最新申請IDをキーに、業務データ最新承認情報テーブルを検索
   *
   * @param newestWfRequestId 最新申請ID
   * @return 業務データ最新承認情報
   */
  Optional<TBusinessNewestAppr> findByNewestWfRequestId(Long newestWfRequestId);
}
