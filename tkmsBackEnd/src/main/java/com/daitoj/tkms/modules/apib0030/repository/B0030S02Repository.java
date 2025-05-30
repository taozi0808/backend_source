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
   * @param buildingCd 棟コード
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
                       hdr.roughEstCd,
                       hdr.delFlg,
                       hdr.regTs,
                       hdr.regUserId,
                       hdr.regPgId,
                       hdr.updTs,
                       hdr.updUserId,
                       hdr.updPgId
                       )
               FROM TRoughEstHdr hdr
          LEFT JOIN TProjectSite tp ON tp.projectCd = hdr.projectCd
          LEFT JOIN TConstrSite  tc ON tp = tc.projectSite AND tc.buildingCd = :buildingCd
              WHERE hdr.projectCd  = :projectCd
                AND hdr.buildingCd = :buildingCd
          """)
  List<ProjectSiteDto> findByProjectCd(String projectCd, String buildingCd);
}
