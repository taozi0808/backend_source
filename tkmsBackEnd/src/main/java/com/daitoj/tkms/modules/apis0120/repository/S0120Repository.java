package com.daitoj.tkms.modules.apis0120.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0120.service.dto.CustomerApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 承認一覧（顧客管理）のリポジトリ.
 */
@Repository
public interface S0120Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param empCd 従業員コード
   * @return 承認一覧（顧客管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0120.service.dto.CustomerApprInfoDto(
                  mc.customerCd,
                  CONCAT(mc.customerNm1, mc.customerNm2),
                  mc.tradingK,
                  mc.gyousyuGyoutai,
                  mc.ceoNm,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN MCustomer mc           ON wr.businessDataId = mc.id
        INNER JOIN MEmp me                ON wr.requestEmpCd = me.empCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00012'
               AND wr.businessTblId                 = 'M_CUSTOMER'
               AND wa.apprSt                        = '1'
               AND wa.apprEmpCd                     = :empCd
          ORDER BY mc.customerCd
      """)
  List<CustomerApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd);

  /**
   * 検索処理(承認待).
   *
   * @param empCd                従業員コード
   * @param customerCd           顧客コード
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @return 承認一覧（顧客管理）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0120.service.dto.CustomerApprInfoDto(
                  mc.customerCd,
                  CONCAT(mc.customerNm1, mc.customerNm2),
                  mc.tradingK,
                  mc.gyousyuGyoutai,
                  mc.ceoNm,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN MCustomer mc           ON wr.businessDataId = mc.id
        INNER JOIN MEmp me                ON wr.requestEmpCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00012'
               AND wr.businessTblId                 = 'M_CUSTOMER'
               AND wa.apprSt                        = '1'
               AND wa.apprEmpCd                     = :empCd
               AND (:customerCd IS NULL           OR :customerCd = ''
                                                  OR mc.customerCd LIKE %:customerCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL      OR :requestOfficeNm = ''
                                                  OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL         OR :requestEmpNm = ''
                                                  OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY mc.customerCd
      """)
  List<CustomerApprInfoDto> findCustomerInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("customerCd") String customerCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm);

  /**
   * 検索処理(承認済、差戻).
   *
   * @param listApprStatus       承認状態リスト
   * @param empCd                従業員コード
   * @param customerCd           顧客コード
   * @param requestDateFrom      申請日（開始）
   * @param requestDateTo        申請日（終了）
   * @param requestOfficeNm      申請事業所
   * @param requestEmpNm         申請者
   * @return 承認一覧（顧客管理）
   */
  @Query(
       """
            SELECT new com.daitoj.tkms.modules.apis0120.service.dto.CustomerApprInfoDto(
                  mc.customerCd,
                  CONCAT(mc.customerNm1, mc.customerNm2),
                  mc.tradingK,
                  mc.gyousyuGyoutai,
                  mc.ceoNm,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  il.itemValue,
                  wa.decisionComment)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
        INNER JOIN MCustomer mc           ON wr.businessDataId = mc.id
        INNER JOIN MEmp me                ON wr.requestEmpCd = me.empCd
        INNER JOIN MOffice mof            ON me.belongOfficeCd.officeCd = mof.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = 'C0001'
             WHERE wr.businessTypeCd.businessTypeCd = 'B00012'
               AND wr.businessTblId                 = 'M_CUSTOMER'
               AND wa.apprEmpCd                     = :empCd
               AND wa.apprSt IN :listApprStatus
               AND (:customerCd IS NULL           OR :customerCd = ''
                                                  OR mc.customerCd LIKE %:customerCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL      OR :requestOfficeNm = ''
                                                  OR mof.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL         OR :requestEmpNm = ''
                                                  OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY mc.customerCd
       """)
  List<CustomerApprInfoDto> findCustomerEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("customerCd") String customerCd,
      @Param("requestDateFrom") Instant requestDateFrom,
      @Param("requestDateTo") Instant requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm);
}
