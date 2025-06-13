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
   * @param empCd          従業員コード
   * @param itemClassCd    項目分類コード
   * @param businessTypeCd 業務種類コード
   * @param apprSt         承認状態
   * @return 承認一覧（作業員情報管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0090.service.dto.WorkerApprInfoDto(
                  mw.workerCd,
                  mw.workerNm,
                  mw.workerKnNm,
                  ilemk.itemValue,
                  mw.healthCheckupDay,
                  ilwpk.itemValue,
                  ilehk.itemValue,
                  ileik.itemValue,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa          ON wr.id = wa.id
                                           AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn  ON wr.businessDataId = bn.businessDataId
                                           AND bn.businessTblId = 'M_WORKER'
        INNER JOIN MWorker mw              ON wr.businessDataId = mw.id
        INNER JOIN MItemListSetting ilemk  ON mw.employK = ilemk.id.itemCd
                                           AND ilemk.id.itemClassCd = 'D0023'
        INNER JOIN MItemListSetting ilwpk  ON mw.wpiTypeK = ilwpk.id.itemCd
                                           AND ilwpk.id.itemClassCd = 'D0028'
        INNER JOIN MItemListSetting ilehk  ON mw.ehiTypeK = ilehk.id.itemCd
                                           AND ilehk.id.itemClassCd = 'D0027'
        INNER JOIN MItemListSetting ileik  ON mw.eisTypeK = ileik.id.itemCd
                                           AND ileik.id.itemClassCd = 'D0029'
        INNER JOIN MEmp me                 ON wr.requestAppCd = me.empCd
        INNER JOIN MItemListSetting il     ON wa.apprSt = il.id.itemCd
                                           AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wa.apprSt                        = :apprSt
               AND wr.requestAppCd                  = :empCd
               AND wr.appAccountK                   = '1'
          ORDER BY mw.workerCd
      """)
  List<WorkerApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

  /**
   * 検索処理(承認待).
   *
   * @param empCd                従業員コード
   * @param workerCd             作業員コード
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @param itemClassCd          項目分類コード
   * @param businessTypeCd       業務種類コード
   * @param apprSt               承認状態
   * @return 承認一覧（作業員情報管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0090.service.dto.WorkerApprInfoDto(
                  mw.workerCd,
                  mw.workerNm,
                  mw.workerKnNm,
                  ilemk.itemValue,
                  mw.healthCheckupDay,
                  ilwpk.itemValue,
                  ilehk.itemValue,
                  ileik.itemValue,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa          ON wr.id = wa.id
                                           AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn  ON wr.businessDataId = bn.businessDataId
                                           AND bn.businessTblId = 'M_WORKER'
        INNER JOIN MWorker mw              ON wr.businessDataId = mw.id
        INNER JOIN MItemListSetting ilemk  ON mw.employK = ilemk.id.itemCd
                                           AND ilemk.id.itemClassCd = 'D0023'
        INNER JOIN MItemListSetting ilwpk  ON mw.wpiTypeK = ilwpk.id.itemCd
                                           AND ilwpk.id.itemClassCd = 'D0028'
        INNER JOIN MItemListSetting ilehk  ON mw.ehiTypeK = ilehk.id.itemCd
                                           AND ilehk.id.itemClassCd = 'D0027'
        INNER JOIN MItemListSetting ileik  ON mw.eisTypeK = ileik.id.itemCd
                                           AND ileik.id.itemClassCd = 'D0029'
        INNER JOIN MEmp me                 ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mof             ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il     ON wa.apprSt = il.id.itemCd
                                           AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wa.apprSt                        = :apprSt
               AND wr.requestAppCd                  = :empCd
               AND wr.appAccountK                   = '1'
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
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

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
   * @param itemClassCd          項目分類コード
   * @param businessTypeCd       業務種類コード
   * @return 承認一覧（作業員情報管理）
   */
  @Query(
       """
            SELECT new com.daitoj.tkms.modules.apis0090.service.dto.WorkerApprInfoDto(
                  mw.workerCd,
                  mw.workerNm,
                  mw.workerKnNm,
                  ilemk.itemValue,
                  mw.healthCheckupDay,
                  ilwpk.itemValue,
                  ilehk.itemValue,
                  ileik.itemValue,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa          ON wr.id = wa.id
        INNER JOIN TBusinessNewestAppr bn  ON wr.businessDataId = bn.businessDataId
                                           AND bn.businessTblId = 'M_WORKER'
        INNER JOIN MWorker mw              ON wr.businessDataId = mw.id
        INNER JOIN MItemListSetting ilemk  ON mw.employK = ilemk.id.itemCd
                                           AND ilemk.id.itemClassCd = 'D0023'
        INNER JOIN MItemListSetting ilwpk  ON mw.wpiTypeK = ilwpk.id.itemCd
                                           AND ilwpk.id.itemClassCd = 'D0028'
        INNER JOIN MItemListSetting ilehk  ON mw.ehiTypeK = ilehk.id.itemCd
                                           AND ilehk.id.itemClassCd = 'D0027'
        INNER JOIN MItemListSetting ileik  ON mw.eisTypeK = ileik.id.itemCd
                                           AND ileik.id.itemClassCd = 'D0029'
        INNER JOIN MEmp me                 ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mof             ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il     ON wa.apprSt = il.id.itemCd
                                           AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wa.apprSt                      IN :listApprStatus
               AND wr.requestAppCd                = :empCd
               AND wr.appAccountK                 = '1'
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
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd);
}
