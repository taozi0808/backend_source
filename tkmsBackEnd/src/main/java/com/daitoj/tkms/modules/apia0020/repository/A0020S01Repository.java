package com.daitoj.tkms.modules.apia0020.repository;

import com.daitoj.tkms.domain.MWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** パスワード通知のリポジトリ */
@Repository
public interface A0020S01Repository extends JpaRepository<MWorker, Long> {

  /**
   * 作業員情報を取得
   *
   * @param loginId ログインID
   * @return 作業員情報
   */
  @Query(
      """
          SELECT mw
            FROM MLogin wl
      INNER JOIN MVendor mv ON wl.loginId   = mv.login.loginId
      INNER JOIN MWorker mw ON wl.loginId   = mw.login.loginId
           WHERE wl.loginId = :loginId
          """)
  MWorker findMLoginInfo(@Param("loginId") String loginId);
}
