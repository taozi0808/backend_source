package com.daitoj.tkms.modules.apis0070.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0070.service.dto.ConstrWbsApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 承認一覧（工事予実管理）のリポジトリ.
 */
@Repository
public interface S0070Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param empCd          従業員コード
   * @param itemClassCd    項目分類コード
   * @param businessTypeCd 業務種類コード
   * @param apprSt         承認状態
   * @return 承認一覧（工事予実管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0070.service.dto.ConstrWbsApprInfoDto(
                  cw.constrSiteCd,
                  cs.constrSiteNm,
                  cs.constrSiteKnNm,
                  cs.constrSiteStartYmd,
                  cs.constrSiteDeliveryYmd,
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
                                          AND bn.businessTblId = 'T_CONSTR_WBS_HDR'
        INNER JOIN TConstrWbsHdr cw       ON wr.businessDataId = cw.id
        INNER JOIN TConstrSite cs         ON cw.constrSiteCd = cs.constrSiteCd
        INNER JOIN TProjectSite ps        ON cs.projectSite.id = ps.id
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                          AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd    = :businessTypeCd
               AND wa.apprSt                           = :apprSt
               AND wr.requestAppCd                     = :empCd
               AND wr.appAccountK                      = '1'
          ORDER BY cs.constrSiteCd
      """)
  List<ConstrWbsApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

  /**
   * 検索処理(承認待).
   *
   * @param empCd                従業員コード
   * @param constrSiteCd         現場コード
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @param itemClassCd          項目分類コード
   * @param businessTypeCd       業務種類コード
   * @param apprSt               承認状態
   * @return 承認一覧（工事予実管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0070.service.dto.ConstrWbsApprInfoDto(
                  cw.constrSiteCd,
                  cs.constrSiteNm,
                  cs.constrSiteKnNm,
                  cs.constrSiteStartYmd,
                  cs.constrSiteDeliveryYmd,
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
                                          AND bn.businessTblId = 'T_CONSTR_WBS_HDR'
        INNER JOIN TConstrWbsHdr cw       ON wr.businessDataId = cw.id
        INNER JOIN TConstrSite cs         ON cw.constrSiteCd = cs.constrSiteCd
        INNER JOIN TProjectSite ps        ON cs.projectSite.id = ps.id
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd    = :businessTypeCd
               AND wa.apprSt                           = :apprSt
               AND wr.requestAppCd                     = :empCd
               AND wr.appAccountK                      = '1'
               AND (:constrSiteCd IS NULL         OR :constrSiteCd = ''
                                                  OR cw.constrSiteCd LIKE %:constrSiteCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL      OR :requestOfficeNm = ''
                                                  OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL         OR :requestEmpNm = ''
                                                  OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY cs.constrSiteCd
      """)
  List<ConstrWbsApprInfoDto> findConstrWbsInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("constrSiteCd") String constrSiteCd,
      @Param("requestDateFrom") OffsetDateTime requestDateFrom,
      @Param("requestDateTo") OffsetDateTime requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

  /**
   * 検索処理(承認済、差戻).
   *
   * @param listApprStatus       承認状態リスト
   * @param empCd                従業員コード
   * @param constrSiteCd         現場コード
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @param itemClassCd          項目分類コード
   * @param businessTypeCd       業務種類コード
   * @return 承認一覧（工事予実管理）
   */
  @Query(
       """
            SELECT new com.daitoj.tkms.modules.apis0070.service.dto.ConstrWbsApprInfoDto(
                  cw.constrSiteCd,
                  cs.constrSiteNm,
                  cs.constrSiteKnNm,
                  cs.constrSiteStartYmd,
                  cs.constrSiteDeliveryYmd,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd,
                  bn.finalApprDt)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
        INNER JOIN TBusinessNewestAppr bn ON bn.businessDataId = wr.businessDataId
                                          AND bn.businessTblId = 'T_CONSTR_WBS_HDR'
        INNER JOIN TConstrWbsHdr cw       ON wr.businessDataId = cw.id
        INNER JOIN TConstrSite cs         ON cw.constrSiteCd = cs.constrSiteCd
        INNER JOIN TProjectSite ps        ON cs.projectSite.id = ps.id
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd    = :businessTypeCd
               AND wr.requestAppCd                     = :empCd
               AND wr.appAccountK                      = '1'
               AND wa.apprSt IN :listApprStatus
               AND (:constrSiteCd IS NULL         OR :constrSiteCd = ''
                                                  OR cw.constrSiteCd LIKE %:constrSiteCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL      OR :requestOfficeNm = ''
                                                  OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL         OR :requestEmpNm = ''
                                                  OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY cs.constrSiteCd
       """)
  List<ConstrWbsApprInfoDto> findConstrWbsEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("constrSiteCd") String constrSiteCd,
      @Param("requestDateFrom") OffsetDateTime requestDateFrom,
      @Param("requestDateTo") OffsetDateTime requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd);
}
