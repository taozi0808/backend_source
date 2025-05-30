package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TProjectRequestDtl;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectRequestDtlDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 案件要望明細情報のリポジトリ */
@Repository
public interface B0030S05Repository extends JpaRepository<TProjectRequestDtl, Long> {
  /**
   * 案件要望明細を取得
   *
   * @param projectId 案件ID
   * @return 案件要望明細情報
   */
  @Query(
      """
          SELECT
              new com.daitoj.tkms.modules.apib0030.service.dto.ProjectRequestDtlDto(
              tp.project.id,
              tp.seqNo,
              tp.requestYmd,
              tp.requestContent,
              tp.requestAnswer,
              tp.delFlg,
              tp.regTs,
              tp.regUserId,
              tp.regPgId,
              tp.updTs,
              tp.updUserId,
              tp.updPgId)
           FROM TProjectRequestDtl tp
          WHERE tp.project.id = :projectId
          """)
  List<ProjectRequestDtlDto> findByProjectId(Long projectId);
}
