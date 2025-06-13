package com.daitoj.tkms.modules.apih0010.repository;

import com.daitoj.tkms.domain.TAssessHdr;
import com.daitoj.tkms.modules.apih0010.service.dto.AssessInfoDto;
import com.daitoj.tkms.modules.apih0010.service.dto.AssessmentInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 査定一覧のリポジトリ. */
@Repository
public interface H0010Repository extends JpaRepository<TAssessHdr, Long> {

  /**
   * 当月の査定がある現場を取得.
   *
   * @return 査定一覧
   */
  @Query(
      """
               SELECT new com.daitoj.tkms.modules.apih0010.service.dto.AssessmentInfoDto(
                      ta.constrSiteCd)
                 FROM TAssessHdr ta
                WHERE (ta.assessYm = TO_CHAR(CURRENT_DATE, 'YYYYMM')
                  AND (:displayobject = '0'))

      """)
  List<AssessmentInfoDto> findConstrSiteInfo(String displayobject);

  /**
   * 初期表示データ取得.
   *
   * @return 査定一覧
   */
  @Query(
      """
               SELECT new com.daitoj.tkms.modules.apih0010.service.dto.AssessInfoDto(
                      ta.id,
                      ta.constrSiteCd,
                      tc.constrSiteNm,
                      mo.orgCd,
                      mo.orgNm,
                      tp.constrSiteDirectorCd,
                      me1.empNm,
                      tfe.picCd,
                      me2.empNm,
                      mcp.constrProcessNm,
                      tbn.finalApprDt,
                      tbh.execBgtTotalAmt,
                      SUM(tph.poTotalAmt),
                      va.befAssessTotalAmt,
                      ta.mtdAssessTotalAmt,
                      ta.assessTotalAmt
                      )
                 FROM TAssessHdr ta
            LEFT JOIN TConstrSite  tc              ON ta.constrSiteCd = tc.constrSiteCd
            LEFT JOIN TProjectSite tp              ON tp.id = tc.projectSite.id
            LEFT JOIN MOrg mo                      ON mo.id = tp.constrOrgId
            LEFT JOIN MEmp me1                     ON tp.constrSiteDirectorCd = me1.empCd
            LEFT JOIN TFtEngineer tfe              ON tp.id = tfe.projectSite.id
                  AND tfe.hireEndYmd = ''
            LEFT JOIN MEmp me2                     ON tp.ftEngineerCd = me2.empCd
            LEFT JOIN TConstrWbsHdr tcw            ON ta.constrSiteCd = tcw.constrSiteCd
            LEFT JOIN MConstrProcess mcp           ON mcp.constrProcessCd = tcw.underConstrProcessCd
            LEFT JOIN TBusinessNewestAppr tbn      ON tbn.businessTblId = 'T_ASSESS_HDR'
                  AND tbn.businessDataId = ta.id
                  AND tbn.businessDataSt = '3'
            LEFT JOIN TExecBgtHdr tbh              ON tbh.constrSiteCd = ta.constrSiteCd
            LEFT JOIN VAssessHdr va                ON ta.constrSiteCd = va.constrSiteCd
                  AND ta.assessYm = va.assessYm
            LEFT JOIN TPoHdr tph                   ON tph.constrSite.id =  tc.id
                WHERE (:belongOfficeCd     IS NULL OR tp.icOfficeCd.officeCd
                                                 LIKE %:belongOfficeCd%)
                  AND (ta.constrSiteCd         NOT IN :consiteCd)
             GROUP BY ta.id,
                      ta.constrSiteCd,
                      tc.constrSiteNm,
                      mo.orgCd,
                      mo.orgNm,
                      tp.constrSiteDirectorCd,
                      me1.empNm,
                      tfe.picCd,
                      me2.empNm,
                      mcp.constrProcessNm,
                      tbn.finalApprDt,
                      tbh.execBgtTotalAmt,
                      va.befAssessTotalAmt,
                      ta.mtdAssessTotalAmt,
                      ta.assessTotalAmt
      """)
  List<AssessInfoDto> findInitInfo(String belongOfficeCd, List<String> consiteCd);

  /**
   * 検索処理.
   *
   * @param constrSiteCd 現場コード
   * @param empNm 現場所長
   * @param displayObject 表示対象
   * @param orgNm 組織名
   * @param execBgtCd 予算作成部門ID
   * @return 査定一覧
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apih0010.service.dto.AssessInfoDto(
                      ta.id,
                      ta.constrSiteCd,
                      tc.constrSiteNm,
                      mo.orgCd,
                      mo.orgNm,
                      tp.constrSiteDirectorCd,
                      me1.empNm,
                      tfe.picCd,
                      me2.empNm,
                      mcp.constrProcessNm,
                      tbn.finalApprDt,
                      tbh.execBgtTotalAmt,
                      SUM(tph.poTotalAmt),
                      va.befAssessTotalAmt,
                      ta.mtdAssessTotalAmt,
                      ta.assessTotalAmt
                      )
                 FROM TAssessHdr ta
            LEFT JOIN TConstrSite  tc              ON ta.constrSiteCd = tc.constrSiteCd
            LEFT JOIN TProjectSite tp              ON tp.id = tc.projectSite.id
            LEFT JOIN MOrg mo                      ON mo.id = tp.constrOrgId
            LEFT JOIN MEmp me1                     ON tp.constrSiteDirectorCd = me1.empCd
            LEFT JOIN TFtEngineer tfe              ON tp.id = tfe.projectSite.id
                  AND tfe.hireEndYmd = ''
            LEFT JOIN MEmp me2                     ON tp.ftEngineerCd = me2.empCd
            LEFT JOIN TConstrWbsHdr tcw            ON ta.constrSiteCd = tcw.constrSiteCd
            LEFT JOIN MConstrProcess mcp           ON mcp.constrProcessCd = tcw.underConstrProcessCd
            LEFT JOIN TBusinessNewestAppr tbn      ON tbn.businessTblId = 'T_ASSESS_HDR'
                  AND tbn.businessDataId = ta.id
                  AND tbn.businessDataSt = '3'
            LEFT JOIN TExecBgtHdr tbh              ON tbh.constrSiteCd = ta.constrSiteCd
            LEFT JOIN VAssessHdr va                ON ta.constrSiteCd = va.constrSiteCd
                  AND ta.assessYm = va.assessYm
            LEFT JOIN TPoHdr tph                   ON tph.constrSite.id =  tc.id
                WHERE (:belongOfficeCd     IS NULL OR tp.icOfficeCd.officeCd
                                                 LIKE %:belongOfficeCd%)
                  AND (ta.constrSiteCd         NOT IN :consiteCd)
                  AND (:constrSiteCd       IS NULL OR :constrSiteCd = '' OR ta.constrSiteCd
                                                 LIKE %:constrSiteCd%)
                  AND (:execBgtCd          IS NULL OR :execBgtCd = ''    OR tbh.execBgtCd
                                                 LIKE %:execBgtCd%)
                  AND (:orgNm              IS NULL OR :orgNm = ''        OR mo.orgNm
                                                 LIKE %:orgNm%)
                  AND (:empNm              IS NULL OR :empNm = ''        OR me2.empNm
                                                 LIKE %:empNm%)
             GROUP BY ta.id,
                      ta.constrSiteCd,
                      tc.constrSiteNm,
                      mo.orgCd,
                      mo.orgNm,
                      tp.constrSiteDirectorCd,
                      me1.empNm,
                      tfe.picCd,
                      me2.empNm,
                      mcp.constrProcessNm,
                      tbn.finalApprDt,
                      tbh.execBgtTotalAmt,
                      va.befAssessTotalAmt,
                      ta.mtdAssessTotalAmt,
                      ta.assessTotalAmt
        """)
  List<AssessInfoDto> findSateiInfo(
      @Param("belongOfficeCd") String belongOfficeCd,
      @Param("displayObject") String displayObject,
      @Param("constrSiteCd") String constrSiteCd,
      @Param("execBgtCd") String execBgtCd,
      @Param("orgNm") String orgNm,
      @Param("empNm") String empNm,
      List<String> consiteCd);
}
