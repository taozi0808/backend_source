package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TProjectPreWorkDtl;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 先行作業明細のリポジトリ */
@Repository
public interface B0030S06Repository extends JpaRepository<TProjectPreWorkDtl, Long> {

  /**
   * 先行作業明細を取得
   *
   * @param projectId 案件ID
   * @return 先行作業明細
   */
  @EntityGraph(attributePaths = {"priceId", "priceId.majorWorkCd", "priceId.minorWorkCd"})
  List<TProjectPreWorkDtl> findByProjectId(Long projectId);
}
