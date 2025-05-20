package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MEmp;
import java.util.List;
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
   * 役職コードり、従業員情報を取得する
   *
   * @param positionNm 役職名称に長が含まれる
   * @return 従業員情報
   */
  @Query(
      """
                SELECT emp
                  FROM MEmp emp
            INNER JOIN MPosition pos       ON emp.positionCd = pos
                 WHERE pos.positionNm like %:positionNm%
              ORDER BY emp.empCd
            """)
  List<MEmp> findByPositionNm(String positionNm);

}
