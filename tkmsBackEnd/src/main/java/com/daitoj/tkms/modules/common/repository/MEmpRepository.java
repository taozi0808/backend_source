package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MEmp;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 従業員情報リポジトリ */
@Repository
public interface MEmpRepository extends JpaRepository<MEmp, Long> {
  /**
   * ログインIDより、従業員情報を取得する
   *
   * @param loginId ログインID
   * @return 従業員情報
   */
  @EntityGraph(attributePaths = {"positionCd"})
  MEmp findBylogin_LoginId(String loginId);

  /**
   * 社員コードより、従業員情報を取得する
   *
   * @param empCd 社員コード
   * @return 従業員情報
   */
  Optional<MEmp> findByEmpCd(String empCd);

  /**
   * 社員コードを採番
   *
   * @return 社員コード
   */
  @Query(
      value = "SELECT LPAD((MAX(CAST(e.emp_cd AS INT)) + 1)::TEXT, 6, '0') FROM m_emp e",
      nativeQuery = true)
  String getNextValue(); // TODO
}
