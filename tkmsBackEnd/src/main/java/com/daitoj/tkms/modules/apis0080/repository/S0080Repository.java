package com.daitoj.tkms.modules.apis0080.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0080.service.dto.PartnerVendorApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 承認一覧（協力業者管理）のリポジトリ.
 */
@Repository
public interface S0080Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param empCd          従業員コード
   * @param itemClassCd    項目分類コード
   * @param businessTypeCd 業務種類コード
   * @param apprSt         承認状態
   * @return 承認一覧（協力業者管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0080.service.dto.PartnerVendorApprInfoDto(
                  mv.partnerVendorCd,
                  mv.compNm,
                  mv.compKnNm,
                  mv.branchNm,
                  mv.branchKnNm,
                  CASE WHEN mv.demolPermFlg = '1'    THEN '有' ELSE '無' END,
                  CASE WHEN mv.securityCertFlg = '1' THEN '有' ELSE '無' END,
                  CASE WHEN vi.id IS NOT NULL        THEN '有' ELSE '無' END,
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
                                          AND bn.businessTblId = 'M_VENDOR'
        INNER JOIN MVendor mv             ON wr.businessDataId = mv.id
        INNER JOIN MLogin ml              ON mv.updUserId = ml.loginId
        INNER JOIN MVendorIwPerm vi       ON mv.id = vi.vendor.id
                                          AND vi.seqNo = 1
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                          AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wa.apprSt                        = :apprSt
               AND wa.apprEmpCd                     = :empCd
          ORDER BY mv.partnerVendorCd
      """)
  List<PartnerVendorApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

  /**
   * 検索処理(承認待).
   *
   * @param empCd                従業員コード
   * @param partnerVendorCd      協力業者コード
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @param itemClassCd          項目分類コード
   * @param businessTypeCd       業務種類コード
   * @param apprSt               承認状態
   * @return 承認一覧（協力業者管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0080.service.dto.PartnerVendorApprInfoDto(
                  mv.partnerVendorCd,
                  mv.compNm,
                  mv.compKnNm,
                  mv.branchNm,
                  mv.branchKnNm,
                  CASE WHEN mv.demolPermFlg = '1'    THEN '有' ELSE '無' END,
                  CASE WHEN mv.securityCertFlg = '1' THEN '有' ELSE '無' END,
                  CASE WHEN vi.id IS NOT NULL        THEN '有' ELSE '無' END,
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
                                          AND bn.businessTblId = 'M_VENDOR'
        INNER JOIN MVendor mv             ON wr.businessDataId = mv.id
        INNER JOIN MLogin ml              ON mv.updUserId = ml.loginId
         LEFT JOIN MVendorIwPerm vi       ON mv.id = vi.vendor.id
                                          AND vi.seqNo = 1
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                          AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wa.apprSt                        = :apprSt
               AND wa.apprEmpCd                     = :empCd
               AND (:partnerVendorCd IS NULL      OR :partnerVendorCd = ''
                                                  OR mv.partnerVendorCd LIKE %:partnerVendorCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL      OR :requestOfficeNm = ''
                                                  OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL         OR :requestEmpNm = ''
                                                  OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY mv.partnerVendorCd
      """)
  List<PartnerVendorApprInfoDto> findPartnerVendorInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("partnerVendorCd") String partnerVendorCd,
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
   * @param partnerVendorCd      協力業者コード
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @param itemClassCd          項目分類コード
   * @param businessTypeCd       業務種類コード
   * @return 承認一覧（協力業者管理）
   */
  @Query(
       """
            SELECT new com.daitoj.tkms.modules.apis0080.service.dto.PartnerVendorApprInfoDto(
                  mv.partnerVendorCd,
                  mv.compNm,
                  mv.compKnNm,
                  mv.branchNm,
                  mv.branchKnNm,
                  CASE WHEN mv.demolPermFlg = '1'    THEN '有' ELSE '無' END,
                  CASE WHEN mv.securityCertFlg = '1' THEN '有' ELSE '無' END,
                  CASE WHEN vi.id IS NOT NULL        THEN '有' ELSE '無' END,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd,
                  bn.finalApprDt)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
        INNER JOIN TBusinessNewestAppr bn ON bn.businessDataId = wr.businessDataId
                                          AND bn.businessTblId = 'M_VENDOR'
        INNER JOIN MVendor mv             ON wr.businessDataId = mv.id
        INNER JOIN MLogin ml              ON mv.updUserId = ml.loginId
         LEFT JOIN MVendorIwPerm vi       ON mv.id = vi.vendor.id
                                          AND vi.seqNo = 1
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wa.apprEmpCd                     = :empCd
               AND wa.apprSt IN :listApprStatus
               AND (:partnerVendorCd IS NULL      OR :partnerVendorCd = ''
                                                  OR mv.partnerVendorCd LIKE %:partnerVendorCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL      OR :requestOfficeNm = ''
                                                  OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL         OR :requestEmpNm = ''
                                                  OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY mv.partnerVendorCd
       """)
  List<PartnerVendorApprInfoDto> findPartnerVendorEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("partnerVendorCd") String partnerVendorCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd);
}
