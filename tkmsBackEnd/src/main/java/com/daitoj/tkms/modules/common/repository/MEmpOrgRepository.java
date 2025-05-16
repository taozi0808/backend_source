package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MEmpOrg;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 従業員・組織・対照リポジトリ */
@Repository
public interface MEmpOrgRepository extends JpaRepository<MEmpOrg, Long> {

  /**
   * 従業員・組織・対照情報取得
   *
   * @param empId 従業員ID
   * @return 従業員・組織・対照情報
   */
  @EntityGraph(attributePaths = {"org"})
  List<MEmpOrg> findByEmpId(Long empId);
}
