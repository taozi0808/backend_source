package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TConstrSite;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 現場情報のリポジトリ */
@Repository
public interface B0030S09Repository extends JpaRepository<TConstrSite, Long> {

  /**
   * 現場情報を取得
   *
   * @param projectCd 案件コード
   * @return 物件情報
   */
  @Query(
      """
                 SELECT
                     tc
                 FROM TConstrSite tc
            LEFT JOIN TProjectSite tp ON tp = tc.projectSite
                WHERE tp.projectCd  = :projectCd
            """)
  List<TConstrSite> findByProjectCd(String projectCd);
}
