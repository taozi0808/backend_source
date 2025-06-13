package com.daitoj.tkms.modules.apis0030.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0030.service.dto.ProjectSiteApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 承認一覧（物件管理）のリポジトリ.
 */
@Repository
public interface S0030Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param empCd          承認者従業員コード
   * @param itemClassCd    項目分類コード
   * @param businessTypeCd 業務種類コード
   * @param apprSt         承認状態
   * @return 承認一覧（物件管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0030.service.dto.ProjectSiteApprInfoDto(
                  ps.projectSiteCd,
                  ps.projectSiteNm,
                  ps.projectSiteKnNm,
                  CONCAT(mc.customerNm1, COALESCE(mc.customerNm2, '')),
                  ps.constrStartYmd,
                  ps.constrCompYmd,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd,
                  bn.finalApprDt)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                          AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn ON bn.businessDataId = wr.businessDataId
                                          AND bn.businessTblId = 'T_PROJECT_SITE'
        INNER JOIN TProjectSite ps        ON wr.businessDataId = ps.id
        INNER JOIN MCustomer mc           ON ps.customerBranchCd = mc.customerBranchCd
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                          AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wa.apprSt                        = :apprSt
               AND wa.apprEmpCd                     = :empCd
          ORDER BY ps.projectSiteCd
      """)
  List<ProjectSiteApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

  /**
   * 検索処理(承認待).
   *
   * @param empCd           従業員コード
   * @param projectSiteCd   物件コード
   * @param requestDateFrom 申請日（開始）
   * @param requestDateTo   申請日（終了）
   * @param requestOfficeNm 申請事業所
   * @param requestEmpNm    申請者
   * @param itemClassCd     項目分類コード
   * @param businessTypeCd  業務種類コード
   * @param apprSt          承認状態
   * @return 承認一覧（物件管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0030.service.dto.ProjectSiteApprInfoDto(
                  ps.projectSiteCd,
                  ps.projectSiteNm,
                  ps.projectSiteKnNm,
                  CONCAT(mc.customerNm1, COALESCE(mc.customerNm2, '')),
                  ps.constrStartYmd,
                  ps.constrCompYmd,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd,
                  bn.finalApprDt)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                          AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn ON bn.businessDataId = wr.businessDataId
                                          AND bn.businessTblId = 'T_PROJECT_SITE'
        INNER JOIN TProjectSite ps        ON wr.businessDataId = ps.id
        INNER JOIN MCustomer mc           ON ps.customerBranchCd = mc.customerBranchCd
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                          AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wa.apprSt                        = :apprSt
               AND wa.apprEmpCd                     = :empCd
               AND (:projectSiteCd IS NULL   OR :projectSiteCd = ''
                                             OR ps.projectSiteCd LIKE %:projectSiteCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL OR :requestOfficeNm = ''
                                             OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL    OR :requestEmpNm = ''
                                             OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY ps.projectSiteCd
      """)
  List<ProjectSiteApprInfoDto> findProjectSiteInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("projectSiteCd") String projectSiteCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt,
      @Param("businessTblId") String businessTblId);

  /**
   * 検索処理(承認済、差戻).
   *
   * @param listApprStatus 承認状態リスト
   * @param empCd           従業員コード
   * @param projectSiteCd   精積算コード
   * @param requestDateFrom 申請日（開始）
   * @param requestDateTo   申請日（終了）
   * @param requestOfficeNm 申請事業所
   * @param requestEmpNm    申請者
   * @param itemClassCd     項目分類コード
   * @param businessTypeCd  業務種類コード
   * @return 承認一覧（物件管理）
   */

  @Query(
       """
            SELECT new com.daitoj.tkms.modules.apis0030.service.dto.ProjectSiteApprInfoDto(
                  ps.projectSiteCd,
                  ps.projectSiteNm,
                  ps.projectSiteKnNm,
                  CONCAT(mc.customerNm1, COALESCE(mc.customerNm2, '')),
                  ps.constrStartYmd,
                  ps.constrCompYmd,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd,
                  bn.finalApprDt)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
        INNER JOIN TBusinessNewestAppr bn ON bn.businessDataId = wr.businessDataId
                                          AND bn.businessTblId = 'T_PROJECT_SITE'
        INNER JOIN TProjectSite ps        ON wr.businessDataId = ps.id
        INNER JOIN MCustomer mc           ON ps.customerBranchCd = mc.customerBranchCd
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wa.apprEmpCd                     = :empCd
               AND wa.apprSt IN :listApprStatus
               AND (:projectSiteCd IS NULL   OR :projectSiteCd = ''
                                             OR ps.projectSiteCd LIKE %:projectSiteCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL OR :requestOfficeNm = ''
                                             OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL    OR :requestEmpNm = ''
                                             OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY ps.projectSiteCd
       """)
  List<ProjectSiteApprInfoDto> findProjectSiteEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("projectSiteCd") String projectSiteCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd);
}
