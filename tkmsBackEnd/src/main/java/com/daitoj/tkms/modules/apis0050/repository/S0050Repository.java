package com.daitoj.tkms.modules.apis0050.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0050.service.dto.AssessApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 承認一覧（査定管理）のリポジトリ.
 */
@Repository
public interface S0050Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   * TODO 工事工程名
   *
   * @param empCd 従業員コード
   * @return 承認一覧（査定管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0050.service.dto.AssessApprInfoDto(
                  cs.constrSiteCd,
                  cs.constrSiteNm,
                  mt.empNm,
                  mo.orgNm,
                  '工事工程名',
                  bn.finalApprDt,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = 'T_ASSESS_HDR'
        INNER JOIN TAssessHdr ah          ON wr.businessDataId = ah.id
        INNER JOIN TConstrSite cs         ON ah.constrSiteCd = cs.constrSiteCd
        INNER JOIN TProjectSite ps        ON cs.projectSite.id = ps.id
        INNER JOIN MOrg mo                ON mo.id = ps.constrOrgId
        INNER JOIN MEmp mt                ON ps.constrSiteDirectorCd = mt.empCd
        INNER JOIN MEmp me                ON wr.requestEmpCd = me.empCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00005'
               AND wr.businessTblId                 = 'T_ASSESS_HDR'
               AND wa.apprSt                        = '1'
               AND wa.apprEmpCd                     = :empCd
          ORDER BY cs.constrSiteCd
      """)
  List<AssessApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd);

  /**
   * 検索処理(承認待).
   * TODO 工事工程名
   *
   * @param empCd                従業員コード
   * @param constrSiteCd         現場コード
   * @param constrSiteDirectorNm 現場所長
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @return 承認一覧（査定管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0050.service.dto.AssessApprInfoDto(
                  cs.constrSiteCd,
                  cs.constrSiteNm,
                  mt.empNm,
                  mo.orgNm,
                  '工事工程名',
                  bn.finalApprDt,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = 'T_ASSESS_HDR'
        INNER JOIN TAssessHdr ah          ON wr.businessDataId = ah.id
        INNER JOIN TConstrSite cs         ON ah.constrSiteCd = cs.constrSiteCd
        INNER JOIN TProjectSite ps        ON cs.projectSite.id = ps.id
        INNER JOIN MOrg mo                ON mo.id = ps.constrOrgId
        INNER JOIN MEmp mt                ON ps.constrSiteDirectorCd = mt.empCd
        INNER JOIN MEmp me                ON wr.requestEmpCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00005'
               AND wr.businessTblId                 = 'T_ASSESS_HDR'
               AND wa.apprSt                        = '1'
               AND wa.apprEmpCd                     = :empCd
               AND (:constrSiteCd IS NULL         OR :constrSiteCd = ''
                                                  OR cs.constrSiteCd LIKE %:constrSiteCd%)
               AND (:constrSiteDirectorNm IS NULL OR :constrSiteDirectorNm = ''
                                                  OR mt.empNm LIKE %:constrSiteDirectorNm%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL      OR :requestOfficeNm = ''
                                                  OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL         OR :requestEmpNm = ''
                                                  OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY cs.constrSiteCd
      """)
  List<AssessApprInfoDto> findAssessInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("constrSiteCd") String constrSiteCd,
      @Param("constrSiteDirectorNm") String constrSiteDirectorNm,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm);

  /**
   * 検索処理(承認済、差戻).
   * TODO 工事工程名
   *
   * @param listApprStatus       承認状態リスト
   * @param empCd                従業員コード
   * @param constrSiteCd         現場コード
   * @param constrSiteDirectorNm 現場所長
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @return 承認一覧（査定管理）
   */
  @Query(
       """
            SELECT new com.daitoj.tkms.modules.apis0050.service.dto.AssessApprInfoDto(
                  cs.constrSiteCd,
                  cs.constrSiteNm,
                  mt.empNm,
                  mo.orgNm,
                  '工事工程名',
                  bn.finalApprDt,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = 'T_ASSESS_HDR'
        INNER JOIN TAssessHdr ah          ON wr.businessDataId = ah.id
        INNER JOIN TConstrSite cs         ON ah.constrSiteCd = cs.constrSiteCd
        INNER JOIN TProjectSite ps        ON cs.projectSite.id = ps.id
        INNER JOIN MOrg mo                ON mo.id = ps.constrOrgId
        INNER JOIN MEmp mt                ON ps.constrSiteDirectorCd = mt.empCd
        INNER JOIN MEmp me                ON wr.requestEmpCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00005'
               AND wr.businessTblId                 = 'T_ASSESS_HDR'
               AND wa.apprEmpCd                     = :empCd
               AND wa.apprSt IN :listApprStatus
               AND (:constrSiteCd IS NULL         OR :constrSiteCd = ''
                                                  OR cs.constrSiteCd LIKE %:constrSiteCd%)
               AND (:constrSiteDirectorNm IS NULL OR :constrSiteDirectorNm = ''
                                                  OR mt.empNm LIKE %:constrSiteDirectorNm%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL      OR :requestOfficeNm = ''
                                                  OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL         OR :requestEmpNm = ''
                                                  OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY cs.constrSiteCd
       """)
  List<AssessApprInfoDto> findAssessEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("constrSiteCd") String constrSiteCd,
      @Param("constrSiteDirectorNm") String constrSiteDirectorNm,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm);
}
