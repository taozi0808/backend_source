package com.daitoj.tkms.modules.apis0010.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0010.service.dto.RoughEstApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 承認一覧（概算管理）のリポジトリ.
 */
@Repository
public interface S0010Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param empCd          承認者従業員コード
   * @param itemClassCd    項目分類コード
   * @param businessTypeCd 業務種類コード
   * @param apprSt         承認状態
   * @return 承認一覧（概算管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0010.service.dto.RoughEstApprInfoDto(
                    re.roughEstCd,
                    re.projectCd,
                    tp.projectNm,
                    tp.customerBranchCd,
                    CONCAT(mc.customerNm1, COALESCE(mc.customerNm2, '')),
                    TO_CHAR(tp.estSubmitDueTs, 'YYYYMMDD'),
                    re.roughEstTotalAmt,
                    tp.startHopeYmd,
                    tp.compHopeYmd,
                    mo.orgNm,
                    me.empNm,
                    mf.empNm,
                    TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                    il.itemValue,
                    wa.decisionComment,
                    tp.projectKnNm,
                    il.id.itemCd,
                    bn.finalApprDt)
                FROM TWfRequest wr
          INNER JOIN TWfApprStep wa      ON wr.id = wa.id
                                        AND wr.currentStep = wa.apprStepOrder
          INNER JOIN TBusinessNewestAppr bn ON bn.businessDataId = wr.businessDataId
                 AND bn.businessTblId = 'T_ROUGH_EST_HDR'
          INNER JOIN TRoughEstHdr re     ON wr.businessDataId = re.id
          INNER JOIN TProject tp         ON re.projectCd = tp.projectCd
          INNER JOIN MCustomer mc        ON tp.customerBranchCd = mc.customerBranchCd
          INNER JOIN MOrg mo             ON re.roughEstOrgId = mo.id
          INNER JOIN MEmp me             ON re.roughEstPicCd = me.empCd
          INNER JOIN MEmp mf             ON wr.requestAppCd = mf.empCd
          INNER JOIN MItemListSetting il ON wa.apprSt = il.id.itemCd
                                        AND il.id.itemClassCd = :itemClassCd
          WHERE wr.businessTypeCd.businessTypeCd    = :businessTypeCd
            AND wa.apprSt                           = :apprSt
            AND wa.apprEmpCd                        = :empCd
       ORDER BY re.projectCd
       """)
  List<RoughEstApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

  /**
   * 検索処理(承認待).
   *
   * @param empCd           従業員コード
   * @param projectCd       案件コード
   * @param requestDateFrom 申請日（開始）
   * @param requestDateTo   申請日（終了）
   * @param requestOfficeNm 申請事業所
   * @param requestEmpNm    申請者
   * @param itemClassCd     項目分類コード
   * @param businessTypeCd  業務種類コード
   * @param apprSt          承認状態
   * @return 承認一覧（概算管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0010.service.dto.RoughEstApprInfoDto(
                    re.roughEstCd,
                    re.projectCd,
                    tp.projectNm,
                    tp.customerBranchCd,
                    CONCAT(mc.customerNm1, COALESCE(mc.customerNm2, '')),
                    TO_CHAR(tp.estSubmitDueTs, 'YYYYMMDD'),
                    re.roughEstTotalAmt,
                    tp.startHopeYmd,
                    tp.compHopeYmd,
                    mo.orgNm,
                    me.empNm,
                    mf.empNm,
                    TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                    il.itemValue,
                    wa.decisionComment,
                    tp.projectKnNm,
                    il.id.itemCd,
                    bn.finalApprDt)
                FROM TWfRequest wr
          INNER JOIN TWfApprStep wa      ON wr.id = wa.id
                                        AND wr.currentStep = wa.apprStepOrder
          INNER JOIN TBusinessNewestAppr bn ON bn.businessDataId = wr.businessDataId
                 AND bn.businessTblId = 'T_ROUGH_EST_HDR'
          INNER JOIN TRoughEstHdr re     ON wr.businessDataId = re.id
          INNER JOIN TProject tp         ON re.projectCd = tp.projectCd
          INNER JOIN MCustomer mc        ON tp.customerBranchCd = mc.customerBranchCd
          INNER JOIN MOrg mo             ON re.roughEstOrgId = mo.id
          INNER JOIN MEmp me             ON re.roughEstPicCd = me.empCd
          INNER JOIN MEmp mf             ON wr.requestAppCd = mf.empCd
          INNER JOIN MOffice mof         ON mf.belongOfficeCd.officeCd = mof.officeCd
          INNER JOIN MItemListSetting il ON wa.apprSt = il.id.itemCd
                                        AND il.id.itemClassCd = :itemClassCd
          WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
            AND wa.apprSt                        = :apprSt
            AND wa.apprEmpCd                     = :empCd
            AND (:projectCd  IS NULL      OR :projectCd = ''
                                          OR re.projectCd LIKE %:projectCd%)
            AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
            AND COALESCE(:requestDateTo,  wr.requestTs) >= wr.requestTs
            AND (:requestOfficeNm IS NULL OR :requestOfficeNm = ''
                                          OR mof.officeNm LIKE %:requestOfficeNm%)
            AND (:requestEmpNm IS NULL    OR :requestEmpNm = ''
                                          OR mf.empNm LIKE %:requestEmpNm%)
       ORDER BY re.projectCd
      """)
  List<RoughEstApprInfoDto> findRequestRoughEstInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("projectCd") String projectCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

  /**
   * 検索処理(承認済、差戻).
   *
   * @param listApprStatus  承認状態リスト
   * @param empCd           従業員コード
   * @param projectCd       案件コード
   * @param requestDateFrom 申請日（開始）
   * @param requestDateTo   申請日（終了）
   * @param requestOfficeNm 申請事業所
   * @param requestEmpNm    申請者
   * @param itemClassCd     項目分類コード
   * @param businessTypeCd  業務種類コード
   * @return 承認一覧（概算管理）
   */
  @Query(
      """
          SELECT new com.daitoj.tkms.modules.apis0010.service.dto.RoughEstApprInfoDto(
                  re.roughEstCd,
                  re.projectCd,
                  tp.projectNm,
                  tp.customerBranchCd,
                  CONCAT(mc.customerNm1, COALESCE(mc.customerNm2, '')),
                  TO_CHAR(tp.estSubmitDueTs, 'YYYYMMDD'),
                  re.roughEstTotalAmt,
                  tp.startHopeYmd,
                  tp.compHopeYmd,
                  mo.orgNm,
                  me.empNm,
                  mf.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment,
                  tp.projectKnNm,
                  il.id.itemCd,
                  bn.finalApprDt)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa      ON wr.id = wa.id
        INNER JOIN TBusinessNewestAppr bn ON bn.businessDataId = wr.businessDataId
                   AND bn.businessTblId = 'T_ROUGH_EST_HDR'
        INNER JOIN TRoughEstHdr re     ON wr.businessDataId = re.id
        INNER JOIN TProject tp         ON re.projectCd = tp.projectCd
        INNER JOIN MCustomer mc        ON tp.customerBranchCd = mc.customerBranchCd
        INNER JOIN MOrg mo             ON re.roughEstOrgId = mo.id
        INNER JOIN MEmp me             ON re.roughEstPicCd = me.empCd
        INNER JOIN MEmp mf             ON wr.requestAppCd = mf.empCd
        INNER JOIN MOffice mof         ON mf.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il ON wa.apprSt = il.id.itemCd
                                      AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd    = :businessTypeCd
               AND wa.apprEmpCd                        = :empCd
               AND wa.apprSt                 IN :listApprStatus
               AND (:projectCd IS NULL       OR :projectCd = ''
                                             OR re.projectCd LIKE %:projectCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo,  wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL OR :requestOfficeNm = ''
                                             OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL    OR :requestEmpNm = ''
                                             OR mf.empNm LIKE %:requestEmpNm%)
          ORDER BY re.projectCd
        """)
  List<RoughEstApprInfoDto> findRequestRoughEstEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("projectCd") String projectCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd);
}
