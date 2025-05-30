package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TProjectBuildingDtl;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectBuildingDtlDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 現場棟明細情報のリポジトリ */
@Repository
public interface B0030S04Repository extends JpaRepository<TProjectBuildingDtl, Long> {
  /**
   * 現場棟明細情報を取得
   *
   * @param projectId 案件ID
   * @return 現場棟明細情報
   */
  @Query(
      """
            SELECT
                new com.daitoj.tkms.modules.apib0030.service.dto.ProjectBuildingDtlDto(
                 tp.project.id,
                 tp.seqNo,
                 tp.buildingCd,
                 tp.buildingWorkNm,
                 tp.delFlg,
                 tp.regTs,
                 tp.regUserId,
                 tp.regPgId,
                 tp.updTs,
                 tp.updUserId,
                 tp.updPgId)
               FROM TProjectBuildingDtl tp
              WHERE tp.project.id = :projectId
          """)
  List<ProjectBuildingDtlDto> findByProjectId(Long projectId, String projectCd);
}
