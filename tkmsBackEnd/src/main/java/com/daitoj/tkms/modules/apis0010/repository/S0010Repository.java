package com.daitoj.tkms.modules.apis0010.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0010.service.dto.RoughEstApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 承認一覧（概算管理）のリポジトリ. */
@Repository
public interface S0010Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param empCd 承認者従業員コード
   * @return 承認一覧（概算管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0010.service.dto.RoughEstApprInfoDto(
                    re.roughEstCd,
                    re.projectCd,
                    tp.projectNm,
                    mc.customerCd,
                    mc.customerNm1,
                    mc.customerNm2,
                    TO_CHAR(tp.estSubmitDueTs, 'YYYYMMDD'),
                    re.roughEstTotalAmt,
                    tp.startHopeYmd,
                    tp.compHopeYmd,
                    mo.orgNm,
                    me.empNm,
                    mf.empNm,
                    TO_CHAR(vwa.requestTs, 'YYYYMMDD'),
                    il.itemValue,
                    vwa.requestComment,
                    tp.projectKnNm,
                    il.id.itemCd,
                    TO_CHAR(vwa.finalApprTs, 'YYYYMMDD'))
                FROM VWfAppr vwa
          INNER JOIN TRoughEstHdr re         ON vwa.businessDataId = re.id
          INNER JOIN TProject tp             ON re.projectCd = tp.projectCd
          INNER JOIN MCustomer mc            ON tp.customerBranchCd = mc.customerBranchCd
          INNER JOIN MOrg mo                 ON re.roughEstOrgId = mo.id
          INNER JOIN MEmp me                 ON re.roughEstPicCd = me.empCd
          INNER JOIN MEmp mf                 ON vwa.requestAppCd = mf.empCd
          INNER JOIN MItemListSetting il     ON vwa.apprSt = il.id.itemCd
                                            AND il.id.itemClassCd = 'C0001'
               WHERE vwa.businessTypeCd      = 'B00001'
                 AND vwa.apprSt              = '1'
                 AND vwa.apprEmpCd           = :empCd
                 AND vwa.appAccountK         = '1'
           ORDER BY  re.projectCd
       """)
  List<RoughEstApprInfoDto> findInitInfo(@NotNull @Param("empCd") String empCd);

  /**
   * 検索処理(承認待).
   *
   * @param empCd 従業員コード
   * @param projectCd 案件コード
   * @param requestDateFrom 申請日（開始）
   * @param requestDateTo 申請日（終了）
   * @param requestOfficeNm 申請事業所
   * @param requestEmpNm 申請者
   * @param listApprStatus 承認待ち、承認済、差戻('1', '2', '9')
   * @return 承認一覧（概算管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0010.service.dto.RoughEstApprInfoDto(
                    re.roughEstCd,
                    re.projectCd,
                    tp.projectNm,
                    mc.customerCd,
                    mc.customerNm1,
                    mc.customerNm2,
                    TO_CHAR(tp.estSubmitDueTs, 'YYYYMMDD'),
                    re.roughEstTotalAmt,
                    tp.startHopeYmd,
                    tp.compHopeYmd,
                    mo.orgNm,
                    me.empNm,
                    mf.empNm,
                    TO_CHAR(vwa.requestTs, 'YYYYMMDD'),
                    il.itemValue,
                    vwa.requestComment,
                    tp.projectKnNm,
                    il.id.itemCd,
                    TO_CHAR(vwa.finalApprTs, 'YYYYMMDD'))
                FROM VWfAppr vwa
          INNER JOIN TRoughEstHdr re         ON vwa.businessDataId = re.id
          INNER JOIN TProject tp             ON re.projectCd = tp.projectCd
          INNER JOIN MCustomer mc            ON tp.customerBranchCd = mc.customerBranchCd
          INNER JOIN MOrg mo                 ON re.roughEstOrgId = mo.id
          INNER JOIN MEmp me                 ON re.roughEstPicCd = me.empCd
          INNER JOIN MEmp mf                 ON vwa.requestAppCd = mf.empCd
          INNER JOIN MOffice mof             ON mf.belongOfficeCd.officeCd = mof.officeCd
          INNER JOIN MItemListSetting il     ON vwa.apprSt = il.id.itemCd
                                            AND il.id.itemClassCd = 'C0001'
          WHERE vwa.businessTypeCd            = 'B00001'
            AND vwa.apprSt                   IN :listApprStatus
            AND vwa.apprEmpCd                 = :empCd
            AND vwa.appAccountK               = '1'
            AND (:projectCd  IS NULL         OR :projectCd = ''
                                             OR re.projectCd LIKE %:projectCd%)
            AND (:requestDateFrom IS NULL    OR :requestDateFrom = ''
                OR TO_CHAR(vwa.requestTs , 'YYYYMMDD') >= :requestDateFrom)
            AND (:requestDateTo IS NULL      OR :requestDateTo = ''
                OR TO_CHAR(vwa.requestTs , 'YYYYMMDD') <= :requestDateTo)
            AND (:requestOfficeNm IS NULL    OR :requestOfficeNm = ''
                                             OR mof.officeNm LIKE %:requestOfficeNm%)
            AND (:requestEmpNm IS NULL       OR :requestEmpNm = ''
                                             OR mf.empNm LIKE %:requestEmpNm%)
       ORDER BY re.projectCd
      """)
  List<RoughEstApprInfoDto> findRequestRoughEstInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("listApprStatus") List<String> listApprStatus,
      @Param("projectCd") String projectCd,
      @Param("requestDateFrom") String requestDateFrom,
      @Param("requestDateTo") String requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm);
}
