package com.daitoj.tkms.modules.apis0110.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0110.service.dto.SubconNotifApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 承認一覧（再下請負通知書）のリポジトリ.
 */
@Repository
public interface S0110Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param empCd          従業員コード
   * @param itemClassCd    項目分類コード
   * @param businessTypeCd 業務種類コード
   * @param apprSt         承認状態
   * @return 承認一覧（再下請負通知書）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0110.service.dto.SubconNotifApprInfoDto(
                  sn.parentPartnerVendorCd,
                  mvp.compNm,
                  mvp.compKnNm,
                  sn.childPartnerVendorCd,
                  mvc.compNm,
                  mvc.compKnNm,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = 'T_SUBCON_NOTIF'
        INNER JOIN TSubconNotif sn        ON wr.businessDataId = sn.id
        INNER JOIN MVendor mvp            ON sn.parentPartnerVendorCd = mvp.partnerVendorCd
        INNER JOIN MVendor mvc            ON sn.childPartnerVendorCd = mvc.partnerVendorCd
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wr.appAccountK                   = '1'
               AND wa.apprSt                        = :apprSt
               AND wr.requestAppCd                  = :empCd
          ORDER BY sn.parentPartnerVendorCd,sn.childPartnerVendorCd
      """)
  List<SubconNotifApprInfoDto> findInitInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

  /**
   * 検索処理(承認待).
   *
   * @param empCd                        従業員コード
   * @param parentPartnerVendorCd        上位協力業者コード
   * @param childPartnerVendorCd         下位協力業者コード
   * @param requestDateFrom              申請日（開始）
   * @param requestDateTo                申請日（終了）
   * @param requestOfficeNm              申請事業所
   * @param requestEmpNm                 申請者
   * @param itemClassCd                  項目分類コード
   * @param businessTypeCd               業務種類コード
   * @param apprSt                       承認状態
   * @return 承認一覧（再下請負通知書）
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apis0110.service.dto.SubconNotifApprInfoDto(
                  sn.parentPartnerVendorCd,
                  mvp.compNm,
                  mvp.compKnNm,
                  sn.childPartnerVendorCd,
                  mvc.compNm,
                  mvc.compKnNm,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                         AND wr.currentStep = wa.apprStepOrder
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = 'T_SUBCON_NOTIF'
        INNER JOIN TSubconNotif sn        ON wr.businessDataId = sn.id
        INNER JOIN MVendor mvp            ON sn.parentPartnerVendorCd = mvp.partnerVendorCd
        INNER JOIN MVendor mvc            ON sn.childPartnerVendorCd = mvc.partnerVendorCd
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mo             ON mo.officeCd = me.belongOfficeCd.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wr.appAccountK                   = '1'
               AND wa.apprSt                        = :apprSt
               AND wr.requestAppCd                  = :empCd
               AND (:parentPartnerVendorCd          IS NULL
                     OR :parentPartnerVendorCd      = ''
                     OR sn.parentPartnerVendorCd    LIKE %:parentPartnerVendorCd%)
               AND (:childPartnerVendorCd           IS NULL
                     OR :childPartnerVendorCd       = ''
                     OR sn.childPartnerVendorCd     LIKE %:childPartnerVendorCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL        OR :requestOfficeNm = ''
                                                    OR mo.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL           OR :requestEmpNm = ''
                                                    OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY sn.parentPartnerVendorCd,sn.childPartnerVendorCd
      """)
  List<SubconNotifApprInfoDto> findRequestSubconNotifInfo(
      @NotNull @Param("empCd") String empCd,
      @Param("parentPartnerVendorCd") String parentPartnerVendorCd,
      @Param("childPartnerVendorCd") String childPartnerVendorCd,
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
   * @param empCd                        従業員コード
   * @param parentPartnerVendorCd        上位協力業者コード
   * @param childPartnerVendorCd         下位協力業者コード
   * @param requestDateFrom              申請日（開始）
   * @param requestDateTo                申請日（終了）
   * @param requestOfficeNm              申請事業所
   * @param requestEmpNm                 申請者
   * @param itemClassCd                  項目分類コード
   * @param businessTypeCd               業務種類コード
   * @return 承認一覧（再下請負通知書）
   */
  @Query(
       """
              SELECT new com.daitoj.tkms.modules.apis0110.service.dto.SubconNotifApprInfoDto(
                  sn.parentPartnerVendorCd,
                  mvp.compNm,
                  mvp.compKnNm,
                  sn.childPartnerVendorCd,
                  mvc.compNm,
                  mvc.compKnNm,
                  me.empNm,
                  TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                  bn.finalApprDt,
                  il.itemValue,
                  wa.decisionComment,
                  il.id.itemCd)
              FROM TWfRequest wr
        INNER JOIN TWfApprStep wa         ON wr.id = wa.id
        INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                         AND bn.businessTblId = 'T_SUBCON_NOTIF'
        INNER JOIN TSubconNotif sn        ON wr.businessDataId = sn.id
        INNER JOIN MVendor mvp            ON sn.parentPartnerVendorCd = mvp.partnerVendorCd
        INNER JOIN MVendor mvc            ON sn.childPartnerVendorCd = mvc.partnerVendorCd
        INNER JOIN MEmp me                ON wr.requestAppCd = me.empCd
        INNER JOIN MOffice mo             ON mo.officeCd = me.belongOfficeCd.officeCd
        INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                         AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
               AND wr.appAccountK                   = '1'
               AND wa.apprSt                        IN :listApprStatus
               AND wr.requestAppCd                  = :empCd
               AND (:parentPartnerVendorCd          IS NULL
                     OR :parentPartnerVendorCd      = ''
                     OR sn.parentPartnerVendorCd    LIKE %:parentPartnerVendorCd%)
               AND (:childPartnerVendorCd           IS NULL
                     OR :childPartnerVendorCd       = ''
                     OR sn.childPartnerVendorCd     LIKE %:childPartnerVendorCd%)
               AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
               AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
               AND (:requestOfficeNm IS NULL        OR :requestOfficeNm = ''
                                                    OR mo.officeNm LIKE %:requestOfficeNm%)
               AND (:requestEmpNm IS NULL           OR :requestEmpNm = ''
                                                    OR me.empNm LIKE %:requestEmpNm%)
          ORDER BY sn.parentPartnerVendorCd,sn.childPartnerVendorCd
       """)
  List<SubconNotifApprInfoDto> findRequestSubconNotifEndInfo(
      @Param("listApprStatus") List<String> listApprStatus,
      @NotNull @Param("empCd") String empCd,
      @Param("parentPartnerVendorCd") String parentPartnerVendorCd,
      @Param("childPartnerVendorCd") String childPartnerVendorCd,
      @Param("requestDateFrom") OffsetDateTime requestDateFrom,
      @Param("requestDateTo") OffsetDateTime requestDateTo,
      @Param("requestOfficeNm") String requestOfficeNm,
      @Param("requestEmpNm") String requestEmpNm,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd);
}
