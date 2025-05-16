package com.daitoj.tkms.modules.apia0010.repository;

import com.daitoj.tkms.domain.MWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 作業員情報リポジトリ */
@Repository
public interface A0010S02Repository extends JpaRepository<MWorker, Long> {
  /**
   * 作業員情報を取得
   *
   * @param loginId ログインID
   * @return 作業員情報
   */
  @Query(
      """
              SELECT wk
                FROM MWorker wk
          INNER JOIN wk.vendor vhd
               WHERE wk.login.loginId = :loginId
          """)
  MWorker findWorkerInfo(String loginId);
}
