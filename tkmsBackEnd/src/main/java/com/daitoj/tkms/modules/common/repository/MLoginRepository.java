package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MLogin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** ログインのリポジトリ */
@Repository
public interface MLoginRepository extends JpaRepository<MLogin, String> {
  /**
   * ログイン情報を取得する
   *
   * @param loginId ユーザーID
   * @param sysdate システム日付
   * @return ログイン情報
   */
  @Query(
      """
          SELECT m
            FROM MLogin m
           WHERE m.loginId   = :loginId
             AND m.startYmd <= :sysdate
             AND (m.endYmd  >= :sysdate OR m.endYmd IS NULL)
          """)
  Optional<MLogin> findLoginInfo(String loginId, String sysdate);
}
