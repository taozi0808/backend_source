package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TProjectSite;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectSiteDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 物件情報のリポジトリ */
@Repository
public interface B0030S02Repository extends JpaRepository<TProjectSite, Long> {

  /**
   * 物件情報を取得
   *
   * @param projectCd 案件コード
   * @return 物件情報
   */
  @Query(
      """
               SELECT
                   new com.daitoj.tkms.modules.apib0030.service.dto.ProjectSiteDto(
                       tc.constrSiteCd,
                       tc.constrSiteNm,
                       tp.projectSiteCd,
                       tp.projectSiteNm,
                       tp.projectSiteKnNm,
                       null,
                       tp.delFlg,
                       tp.regTs,
                       tp.regUserId,
                       tp.regPgId,
                       tp.updTs,
                       tp.updUserId,
                       tp.updPgId
                       )
               FROM TProjectSite tp
          LEFT JOIN TConstrSite  tc ON tp = tc.projectSite
              WHERE tp.projectCd = :projectCd
          """) // TODO estYmd <- 先行作業明細
  List<ProjectSiteDto> findByProjectCd(String projectCd);
}
