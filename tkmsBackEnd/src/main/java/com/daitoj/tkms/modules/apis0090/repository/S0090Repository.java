package com.daitoj.tkms.modules.apis0090.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0090.service.dto.WorkerApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 承認一覧（作業員情報管理）のリポジトリ.
 */
@Repository
public interface S0090Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param empCd 従業員コード
   * @return 承認一覧（作業員情報管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0090.service.dto.WorkerApprInfoDto(
                  mw.workerCd,
                  mw.workerNm,
                  mw.employK,
                  CASE WHEN mw.healthCheckupDay IS NOT NULL THEN '受信済' ELSE '未受診' END,
                  CASE WHEN mw.wpiTypeK <> '4'              THEN '加入'   ELSE '非加入' END,
                  CASE WHEN mw.ehiTypeK <> '4'              THEN '加入'   ELSE '非加入' END,
                  CASE WHEN mw.eisTypeK <> '2'              THEN '加入'   ELSE '非加入' END,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN MWorker mw             ON wr.businessDataId = mw.id
        INNER JOIN MEmp me                ON wr.requestEmpCd = me.empCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00009'
               AND wr.businessTblId                 = 'M_WORKER'
               AND wa.apprSt                        = '1'
               AND wa.apprEmpCd                     = :empCd
          ORDER BY mw.workerCd
      """)
  List<WorkerApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd);

  /**
   * 検索処理(承認待).
   *
   * @param empCd                従業員コード
   * @param workerCd             作業員コード
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @return 承認一覧（作業員情報管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0090.service.dto.WorkerApprInfoDto(
                  mw.workerCd,
                  mw.workerNm,
                  mw.employK,
                  CASE WHEN mw.healthCheckupDay IS NOT NULL THEN '受信済' ELSE '未受診' END,
                  CASE WHEN mw.wpiTypeK <> '4'              THEN '加入'   ELSE '非加入' END,
                  CASE WHEN mw.ehiTypeK <> '4'              THEN '加入'   ELSE '非加入' END,
                  CASE WHEN mw.eisTypeK <> '2'              THEN '加入'   ELSE '非加入' END,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN MWorker mw             ON wr.businessDataId = mw.id
        INNER JOIN MEmp me                ON wr.requestEmpCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00009'
               AND wr.businessTblId                 = 'M_WORKER'
               AND wa.apprSt                        = '1'
               AND wa.apprEmpCd                     = :empCd
               AND (:workerCd IS NULL             OR :workerCd = ''
                                                  OR mw.workerCd LIKE %:workerCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL      OR :requestOfficeNm = ''
                                                  OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL         OR :requestEmpNm = ''
                                                  OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY mw.workerCd
      """)
  List<WorkerApprInfoDto> findWorkerInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("workerCd") String workerCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm);

  /**
   * 検索処理(承認済、差戻).
   *
   * @param listApprStatus       承認状態リスト
   * @param empCd                従業員コード
   * @param workerCd             作業員コード
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @return 承認一覧（作業員情報管理）
   */
  @Query(
       """
            SELECT new com.daitoj.tkms.modules.apis0090.service.dto.WorkerApprInfoDto(
                  mw.workerCd,
                  mw.workerNm,
                  mw.employK,
                  CASE WHEN mw.healthCheckupDay IS NOT NULL THEN '受信済' ELSE '未受診' END,
                  CASE WHEN mw.wpiTypeK <> '4'              THEN '加入'   ELSE '非加入' END,
                  CASE WHEN mw.ehiTypeK <> '4'              THEN '加入'   ELSE '非加入' END,
                  CASE WHEN mw.eisTypeK <> '2'              THEN '加入'   ELSE '非加入' END,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
        INNER JOIN MWorker mw             ON wr.businessDataId = mw.id
        INNER JOIN MEmp me                ON wr.requestEmpCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00009'
               AND wr.businessTblId                 = 'M_WORKER'
               AND wa.apprEmpCd                     = :empCd
               AND wa.apprSt IN :listApprStatus
               AND (:workerCd IS NULL             OR :workerCd = ''
                                                  OR mw.workerCd LIKE %:workerCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL      OR :requestOfficeNm = ''
                                                  OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL         OR :requestEmpNm = ''
                                                  OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY mw.workerCd
       """)
  List<WorkerApprInfoDto> findWorkerEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("workerCd") String workerCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm);
}
