package com.daitoj.tkms.modules.apis0130.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0130.service.dto.EmpApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 承認一覧（会社管理）のリポジトリ. */
@Repository
public interface S0130Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示社員管理取得.
   *
   * @param empCd           従業員コード
   * @param itemClassCd     項目分類コード
   * @param businessTypeCd  業務種類コード
   * @param apprSt          承認状態
   * @param businessTblId   業務テーブルID
   * @return 承認一覧（会社管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0130.service.dto.EmpApprInfoDto(
                  '社員管理',
                  me.empCd,
                  me.empNm,
                  me.empKnNm,
                  mf.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = :businessTblId
        INNER JOIN MEmp me                ON wr.businessDataId = me.id
        INNER JOIN MEmp mf                ON wr.requestAppCd = mf.empCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wr.appAccountK                   = '1'
               AND wa.apprSt                        = :apprSt
               AND wr.requestAppCd                  = :empCd
          ORDER BY me.empCd
      """)
  List<EmpApprInfoDto> findInitEmpInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt,
      @Param("businessTblId") String businessTblId);

  /**
   * 初期表示組織管理取得.
   *
   * @param empCd           従業員コード
   * @param itemClassCd     項目分類コード
   * @param businessTypeCd  業務種類コード
   * @param apprSt          承認状態
   * @param businessTblId   業務テーブルID
   * @return 承認一覧（会社管理）
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apis0130.service.dto.EmpApprInfoDto(
                    '組織管理',
                    mr.effectiveStartDt,
                    '',
                    '',
                    mf.empNm,
                    TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                    bn.finalApprDt,
                    il.itemValue,
                    wa.decisionComment,
                    il.id.itemCd)
                FROM TWfRequest wr
          INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                           AND wr.currentStep = wa.apprStepOrder
          INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                           AND bn.businessTblId = :businessTblId
          INNER JOIN MOrgRev mr             ON wr.businessDataId = mr.id
          INNER JOIN MEmp mf                ON wr.requestAppCd = mf.empCd
          INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                           AND il.id.itemClassCd = :itemClassCd
               WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
                 AND wr.appAccountK                   = '1'
                 AND wa.apprSt                        = :apprSt
                 AND wr.requestAppCd                  = :empCd
            ORDER BY mr.effectiveStartDt
        """)
  List<EmpApprInfoDto> findInitOrgRevInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt,
      @Param("businessTblId") String businessTblId);

  /**
   * 検索処理(承認待).
   *
   * @param empCd                       従業員コード
   * @param codeOrEffectiveStartDate    コード/適用開始日
   * @param requestDateFrom             申請日（開始）
   * @param requestDateTo               申請日（終了）
   * @param requestOfficeNm             申請事業所
   * @param requestEmpNm                申請者
   * @param itemClassCd                 項目分類コード
   * @param businessTypeCd              業務種類コード
   * @param apprSt                      承認状態
   * @param businessTblId               業務テーブルID
   * @return 承認一覧（会社管理-社員管理）
   */
  @Query(
      """
               SELECT new com.daitoj.tkms.modules.apis0130.service.dto.EmpApprInfoDto(
                  '社員管理',
                  me.empCd,
                  me.empNm,
                  me.empKnNm,
                  mf.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = :businessTblId
        INNER JOIN MEmp me                ON wr.businessDataId = me.id
        INNER JOIN MEmp mf                ON wr.requestAppCd = mf.empCd
        INNER JOIN MOffice mof            ON mf.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wr.appAccountK                   = '1'
               AND wa.apprSt                        = :apprSt
               AND wr.requestAppCd                  = :empCd
               AND (:codeOrEffectiveStartDate IS NULL OR :codeOrEffectiveStartDate = ''
                                                      OR me.empCd LIKE %:codeOrEffectiveStartDate%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL          OR :requestOfficeNm = ''
                                                      OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL             OR :requestEmpNm = ''
                                                      OR mf.empNm LIKE %:requestEmpNm%)
          ORDER BY me.empCd
      """)
  List<EmpApprInfoDto> findRequestEmpInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("codeOrEffectiveStartDate") String codeOrEffectiveStartDate,
      @Param("requestDateFrom") OffsetDateTime requestDateFrom,
      @Param("requestDateTo") OffsetDateTime requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt,
      @Param("businessTblId") String businessTblId);

  /**
   * 検索処理(承認済、差戻).
   *
   * @param empCd                       従業員コード
   * @param codeOrEffectiveStartDate    コード/適用開始日
   * @param requestDateFrom             申請日（開始）
   * @param requestDateTo               申請日（終了）
   * @param requestOfficeNm             申請事業所
   * @param requestEmpNm                申請者
   * @param itemClassCd                 項目分類コード
   * @param businessTypeCd              業務種類コード
   * @param businessTblId               業務テーブルID
   * @return 承認一覧（会社管理-社員管理）
   */
  @Query(
      """
               SELECT new com.daitoj.tkms.modules.apis0130.service.dto.EmpApprInfoDto(
                  '社員管理',
                  me.empCd,
                  me.empNm,
                  me.empKnNm,
                  mf.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = :businessTblId
        INNER JOIN MEmp me                ON wr.businessDataId = me.id
        INNER JOIN MEmp mf                ON wr.requestAppCd = mf.empCd
        INNER JOIN MOffice mof            ON mf.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wr.appAccountK                   = '1'
               AND wa.apprSt IN :listApprStatus
               AND wr.requestAppCd                  = :empCd
               AND (:codeOrEffectiveStartDate IS NULL OR :codeOrEffectiveStartDate = ''
                                                      OR me.empCd LIKE %:codeOrEffectiveStartDate%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL          OR :requestOfficeNm = ''
                                                      OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL             OR :requestEmpNm = ''
                                                      OR mf.empNm LIKE %:requestEmpNm%)
          ORDER BY me.empCd
       """)
  List<EmpApprInfoDto> findRequestEmpEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("codeOrEffectiveStartDate") String codeOrEffectiveStartDate,
      @Param("requestDateFrom") OffsetDateTime requestDateFrom,
      @Param("requestDateTo") OffsetDateTime requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("businessTblId") String businessTblId);

  /**
   * 検索処理(承認待).
   *
   * @param empCd                       従業員コード
   * @param codeOrEffectiveStartDate    コード/適用開始日
   * @param requestDateFrom             申請日（開始）
   * @param requestDateTo               申請日（終了）
   * @param requestOfficeNm             申請事業所
   * @param requestEmpNm                申請者
   * @param itemClassCd                 項目分類コード
   * @param businessTypeCd              業務種類コード
   * @param apprSt                      承認状態
   * @param businessTblId               業務テーブルID
   * @return 承認一覧（会社管理-組織管理）
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apis0130.service.dto.EmpApprInfoDto(
                    '組織管理',
                    mr.effectiveStartDt,
                    '',
                    '',
                    mf.empNm,
                    TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                    bn.finalApprDt,
                    il.itemValue,
                    wa.decisionComment,
                    il.id.itemCd)
                FROM TWfRequest wr
          INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                           AND wr.currentStep = wa.apprStepOrder
          INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                           AND bn.businessTblId = :businessTblId
          INNER JOIN MOrgRev mr             ON wr.businessDataId = mr.id
          INNER JOIN MEmp mf                ON wr.requestAppCd = mf.empCd
          INNER JOIN MOffice mof            ON mf.belongOfficeCd.officeCd = mof.officeCd
          INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                           AND il.id.itemClassCd = :itemClassCd
               WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
                 AND wr.appAccountK                   = '1'
                 AND wa.apprSt                        = :apprSt
                 AND wr.requestAppCd                  = :empCd
                 AND (:codeOrEffectiveStartDate IS NULL OR :codeOrEffectiveStartDate = ''
                                                        OR mr.effectiveStartDt LIKE %:codeOrEffectiveStartDate%)
                 AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
                 AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
                 AND (:requestOfficeNm IS NULL          OR :requestOfficeNm = ''
                                                        OR mof.officeNm LIKE %:requestOfficeNm%)
                 AND (:requestEmpNm IS NULL             OR :requestEmpNm = ''
                                                        OR mf.empNm LIKE %:requestEmpNm%)
            ORDER BY mr.effectiveStartDt
        """)
  List<EmpApprInfoDto> findRequestOrgRevInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("codeOrEffectiveStartDate") String codeOrEffectiveStartDate,
      @Param("requestDateFrom") OffsetDateTime requestDateFrom,
      @Param("requestDateTo") OffsetDateTime requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt,
      @Param("businessTblId") String businessTblId);

  /**
   * 検索処理(承認済、差戻).
   *
   * @param empCd                       従業員コード
   * @param codeOrEffectiveStartDate    コード/適用開始日
   * @param requestDateFrom             申請日（開始）
   * @param requestDateTo               申請日（終了）
   * @param requestOfficeNm             申請事業所
   * @param requestEmpNm                申請者
   * @param itemClassCd                 項目分類コード
   * @param businessTypeCd              業務種類コード
   * @param businessTblId               業務テーブルID
   * @return 承認一覧（会社管理-組織管理）
   */
  @Query(
      """
               SELECT new com.daitoj.tkms.modules.apis0130.service.dto.EmpApprInfoDto(
                    '組織管理',
                    mr.effectiveStartDt,
                    '',
                    '',
                    mf.empNm,
                    TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                    bn.finalApprDt,
                    il.itemValue,
                    wa.decisionComment,
                    il.id.itemCd)
                FROM TWfRequest wr
          INNER JOIN TWfApprStep wa         ON wr.id = wa.id
          INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                           AND bn.businessTblId = :businessTblId
          INNER JOIN MOrgRev mr             ON wr.businessDataId = mr.id
          INNER JOIN MEmp mf                ON wr.requestAppCd = mf.empCd
          INNER JOIN MOffice mof            ON mf.belongOfficeCd.officeCd = mof.officeCd
          INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                           AND il.id.itemClassCd = :itemClassCd
               WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
                 AND wr.appAccountK                   = '1'
                 AND wa.apprSt IN :listApprStatus
                 AND wr.requestAppCd                  = :empCd
                 AND (:codeOrEffectiveStartDate IS NULL OR :codeOrEffectiveStartDate = ''
                                                        OR mr.effectiveStartDt LIKE %:codeOrEffectiveStartDate%)
                 AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
                 AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
                 AND (:requestOfficeNm IS NULL          OR :requestOfficeNm = ''
                                                        OR mof.officeNm LIKE %:requestOfficeNm%)
                 AND (:requestEmpNm IS NULL             OR :requestEmpNm = ''
                                                        OR mf.empNm LIKE %:requestEmpNm%)
            ORDER BY mr.effectiveStartDt
        """)
  List<EmpApprInfoDto> findRequestOrgRevEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("codeOrEffectiveStartDate") String codeOrEffectiveStartDate,
      @Param("requestDateFrom") OffsetDateTime requestDateFrom,
      @Param("requestDateTo") OffsetDateTime requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("businessTblId") String businessTblId);
}
