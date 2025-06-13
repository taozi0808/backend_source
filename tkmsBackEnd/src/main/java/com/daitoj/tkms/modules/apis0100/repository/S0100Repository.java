package com.daitoj.tkms.modules.apis0100.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0100.service.dto.SubConLedgerApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 承認一覧（下請契約台帳）のリポジトリ.
 */
@Repository
public interface S0100Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param empCd          従業員コード
   * @param itemClassCd    項目分類コード
   * @param businessTypeCd 業務種類コード
   * @param apprSt         承認状態
   * @param businessTblId  業務テーブルID
   * @return 承認一覧（下請契約台帳）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0100.service.dto.SubConLedgerApprInfoDto(
                  scl.projectSiteCd,
                  ps.projectSiteNm,
                  ps.projectSiteKnNm,
                  CONCAT(ps.projectSiteAddr1, COALESCE(ps.projectSiteAddr2, '')),
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TSubConLedger scl      ON wr.businessDataId = scl.subConLedgerId
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND wr.businessTblId = :businessTblId
        INNER JOIN TProjectSite ps        ON ps.projectSiteCd = scl.projectSiteCd
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wr.appAccountK                   = '1'
               AND wa.apprSt                        = :apprSt
               AND wr.requestAppCd                     = :empCd

          ORDER BY scl.subConLedgerId
      """)
  List<SubConLedgerApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt,
      @Param("businessTblId") String businessTblId);

  /**
   * 検索処理(承認待).
   *
   * @param empCd                従業員コード
   * @param projectSiteCd        物件コード

   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @param itemClassCd          項目分類コード
   * @param businessTypeCd       業務種類コード
   * @param apprSt               承認状態
   * @param businessTblId        業務テーブルID
   * @return 承認一覧（下請契約台帳）
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apis0100.service.dto.SubConLedgerApprInfoDto(
                  scl.projectSiteCd,
                  ps.projectSiteNm,
                  ps.projectSiteKnNm,
                  CONCAT(ps.projectSiteAddr1, COALESCE(ps.projectSiteAddr2, '')),
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TSubConLedger scl      ON wr.businessDataId = scl.subConLedgerId
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND wr.businessTblId = :businessTblId
        INNER JOIN TProjectSite ps        ON ps.projectSiteCd = scl.projectSiteCd
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mo             ON mo.officeCd = me.belongOfficeCd.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wr.appAccountK                   = '1'
               AND wa.apprSt                        = :apprSt
               AND wr.requestAppCd                     = :empCd
               AND (:projectSiteCd IS NULL     OR :projectSiteCd = ''
                                               OR scl.projectSiteCd LIKE %:projectSiteCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL   OR :requestOfficeNm = ''
                                               OR mo.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL      OR :requestEmpNm = ''
                                               OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY scl.subConLedgerId
      """)
  List<SubConLedgerApprInfoDto> findRequestSubConLedgerInfo(
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
   * @param empCd                従業員コード
   * @param projectSiteCd        物件コード
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @param itemClassCd          項目分類コード
   * @param businessTypeCd       業務種類コード
   * @param businessTblId        業務テーブルID
   * @return 承認一覧（下請契約台帳）
   */
  @Query(
       """
              SELECT new com.daitoj.tkms.modules.apis0100.service.dto.SubConLedgerApprInfoDto(
                  scl.projectSiteCd,
                  ps.projectSiteNm,
                  ps.projectSiteKnNm,
                  CONCAT(ps.projectSiteAddr1, COALESCE(ps.projectSiteAddr2, '')),
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
        INNER JOIN TSubConLedger scl      ON wr.businessDataId = scl.subConLedgerId
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND wr.businessTblId = :businessTblId
        INNER JOIN TProjectSite ps        ON ps.projectSiteCd = scl.projectSiteCd
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mo             ON mo.officeCd = me.belongOfficeCd.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wr.appAccountK                   = '1'
               AND wa.apprSt IN :listApprStatus
               AND wr.requestAppCd                     = :empCd
               AND (:projectSiteCd IS NULL     OR :projectSiteCd = ''
                                               OR scl.projectSiteCd LIKE %:projectSiteCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL   OR :requestOfficeNm = ''
                                               OR mo.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL      OR :requestEmpNm = ''
                                               OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY scl.subConLedgerId
       """)
  List<SubConLedgerApprInfoDto> findRequestSubConLedgerEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("projectSiteCd") String projectSiteCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("businessTblId") String businessTblId);
}
