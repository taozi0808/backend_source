package com.daitoj.tkms.modules.apis0020.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0020.service.dto.DetailedEstApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 承認一覧（精積算管理）のリポジトリ.
 */
@Repository
public interface S0020Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param empCd 承認者従業員コード
   * @return 承認一覧（精積算管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0020.service.dto.DetailedEstApprInfoDto(
                  de.detailedEstCd,
                  tp.projectNm,
                  de.customerBranchCd,
                  CONCAT(mc.customerNm1, mc.customerNm2),
                  re.roughEstTotalAmt,
                  de.detailedEstTotalAmt,
                  de.detailedEstYmd,
                  bn.finalApprDt,
                  mo.orgNm,
                  me.empNm,
                  mf.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = 'T_DETAILED_EST_HDR'
        INNER JOIN TDetailedEstHdr de     ON wr.businessDataId = de.id
        INNER JOIN TRoughEstHdr re        ON de.projectCd = re.projectCd
                                         AND de.buildingCd = re.buildingCd
        INNER JOIN TProject tp            ON de.projectCd = tp.projectCd
        INNER JOIN MCustomer mc           ON de.customerBranchCd = mc.customerBranchCd
        INNER JOIN MOrg mo                ON de.detailedEstOrgId = mo.id
        INNER JOIN MEmp me                ON de.detailedEstPicCd = me.empCd
        INNER JOIN MEmp mf                ON wr.requestEmpCd = mf.empCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00002'
               AND wr.businessTblId                 = 'T_DETAILED_EST_HDR'
               AND wa.apprSt                        = '1'
               AND wa.apprEmpCd                     = :empCd
          ORDER BY de.detailedEstCd
      """)
  List<DetailedEstApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd);

  /**
   * 検索処理(承認待).
   *
   * @param empCd           従業員コード
   * @param detailedEstCd   精積算コード
   * @param requestDateFrom 申請日（開始）
   * @param requestDateTo   申請日（終了）
   * @param requestOfficeNm 申請事業所
   * @param requestEmpNm    申請者
   * @return 承認一覧（精積算管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0020.service.dto.DetailedEstApprInfoDto(
                  de.detailedEstCd,
                  tp.projectNm,
                  de.customerBranchCd,
                  CONCAT(mc.customerNm1, mc.customerNm2),
                  re.roughEstTotalAmt,
                  de.detailedEstTotalAmt,
                  de.detailedEstYmd,
                  bn.finalApprDt,
                  mo.orgNm,
                  me.empNm,
                  mf.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = 'T_DETAILED_EST_HDR'
        INNER JOIN TDetailedEstHdr de     ON wr.businessDataId = de.id
        INNER JOIN TRoughEstHdr re        ON de.projectCd = re.projectCd
                                         AND de.buildingCd = re.buildingCd
        INNER JOIN TProject tp            ON de.projectCd = tp.projectCd
        INNER JOIN MCustomer mc           ON de.customerBranchCd = mc.customerBranchCd
        INNER JOIN MOrg mo                ON de.detailedEstOrgId = mo.id
        INNER JOIN MEmp me                ON de.detailedEstPicCd = me.empCd
        INNER JOIN MEmp mf                ON wr.requestEmpCd = mf.empCd
        INNER JOIN MOffice mof            ON mf.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00002'
               AND wr.businessTblId                 = 'T_DETAILED_EST_HDR'
               AND wa.apprSt                        = '1'
               AND wa.apprEmpCd                     = :empCd
               AND (:detailedEstCd IS NULL   OR :detailedEstCd = ''
                                             OR de.detailedEstCd LIKE %:detailedEstCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL OR :requestOfficeNm = ''
                                             OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL    OR :requestEmpNm = ''
                                             OR mf.empNm LIKE %:requestEmpNm%)
          ORDER BY de.detailedEstCd
      """)
  List<DetailedEstApprInfoDto> findRequestDetailedEstInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("detailedEstCd") String detailedEstCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm);

  /**
   * 検索処理(承認済、差戻).
   *
   * @param listApprStatus 承認状態リスト
   * @param empCd           従業員コード
   * @param detailedEstCd   精積算コード
   * @param requestDateFrom 申請日（開始）
   * @param requestDateTo   申請日（終了）
   * @param requestOfficeNm 申請事業所
   * @param requestEmpNm    申請者
   * @return 承認一覧（精積算管理）
   */

  @Query(
       """
            SELECT new com.daitoj.tkms.modules.apis0020.service.dto.DetailedEstApprInfoDto(
                  de.detailedEstCd,
                  tp.projectNm,
                  de.customerBranchCd,
                  CONCAT(mc.customerNm1, mc.customerNm2),
                  re.roughEstTotalAmt,
                  de.detailedEstTotalAmt,
                  de.detailedEstYmd,
                  bn.finalApprDt,
                  mo.orgNm,
                  me.empNm,
                  mf.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = 'T_DETAILED_EST_HDR'
        INNER JOIN TDetailedEstHdr de     ON wr.businessDataId = de.id
        INNER JOIN TRoughEstHdr re        ON de.projectCd = re.projectCd
                                         AND de.buildingCd = re.buildingCd
        INNER JOIN TProject tp            ON de.projectCd = tp.projectCd
        INNER JOIN MCustomer mc           ON de.customerBranchCd = mc.customerBranchCd
        INNER JOIN MOrg mo                ON de.detailedEstOrgId = mo.id
        INNER JOIN MEmp me                ON de.detailedEstPicCd = me.empCd
        INNER JOIN MEmp mf                ON wr.requestEmpCd = mf.empCd
        INNER JOIN MOffice mof            ON mf.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00002'
               AND wr.businessTblId                 = 'T_DETAILED_EST_HDR'
               AND wa.apprEmpCd                     = :empCd
               AND wa.apprSt IN :listApprStatus
               AND (:detailedEstCd IS NULL   OR :detailedEstCd = ''
                                             OR de.detailedEstCd LIKE %:detailedEstCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL OR :requestOfficeNm = ''
                                             OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL    OR :requestEmpNm = ''
                                             OR mf.empNm LIKE %:requestEmpNm%)
          ORDER BY de.detailedEstCd
       """)
  List<DetailedEstApprInfoDto> findRequestDetailedEstEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("detailedEstCd") String detailedEstCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm);
}
