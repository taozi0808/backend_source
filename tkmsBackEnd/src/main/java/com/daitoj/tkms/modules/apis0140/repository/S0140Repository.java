package com.daitoj.tkms.modules.apis0140.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apis0140.service.dto.VendorApprInfoDto;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 承認一覧（自社情報）のリポジトリ. */
@Repository
public interface S0140Repository extends JpaRepository<TProject, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param vendorCd 業者コード
   * @param workerCd 作業員コード
   * @param itemClassCd 項目分類コード
   * @param businessTypeCd 業務種類コード
   * @param apprSt 承認状態
   * @return 承認一覧（自社情報）
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apis0140.service.dto.VendorApprInfoDto(
                    mv.partnerVendorCd,
                    mv.compNm,
                    mv.compKnNm,
                    mv.branchNm,
                    mv.branchKnNm,
                    TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                    bn.finalApprDt,
                    il.itemValue,
                    wa.decisionComment,
                    il.id.itemCd)
                FROM TWfRequest wr
          INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                           AND wr.currentStep = wa.apprStepOrder
          INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                           AND bn.businessTblId = 'M_VENDOR'
          INNER JOIN MVendor mv             ON wr.businessDataId = mv.id
          INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                           AND il.id.itemClassCd = :itemClassCd
               WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
                 AND (wr.appAccountK                  = '2'
                                                      OR  wr.appAccountK = '3')
                 AND wa.apprSt                        = :apprSt
                 AND (wr.requestAppCd                 = :vendorCd
                                                      OR :workerCd IS NULL
                                                      OR :workerCd = ''
                                                      OR wr.requestAppCd = :workerCd)
            ORDER BY mv.partnerVendorCd
        """)
  List<VendorApprInfoDto> findInitInfo(
      @NotNull @Param("vendorCd") String vendorCd,
      @Param("workerCd") String workerCd,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

  /**
   * 検索処理(承認待).
   *
   * @param vendorCd 業者コード
   * @param partnerVendorCd 協力業者コード
   * @param workerCd 作業員コード
   * @param requestDateFrom 申請日（開始）
   * @param requestDateTo 申請日（終了）
   * @param itemClassCd 項目分類コード
   * @param businessTypeCd 業務種類コード
   * @param apprSt 承認状態
   * @return 承認一覧（自社情報）
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apis0140.service.dto.VendorApprInfoDto(
                    mv.partnerVendorCd,
                    mv.compNm,
                    mv.compKnNm,
                    mv.branchNm,
                    mv.branchKnNm,
                    TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                    bn.finalApprDt,
                    il.itemValue,
                    wa.decisionComment,
                    il.id.itemCd)
              FROM TWfRequest wr
          INNER JOIN TWfApprStep wa         ON wr.id = wa.id
                                           AND wr.currentStep = wa.apprStepOrder
          INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                           AND bn.businessTblId = 'M_VENDOR'
          INNER JOIN MVendor mv             ON wr.businessDataId = mv.id
          INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                           AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
                 AND (wr.appAccountK                  = '2'
                                                      OR  wr.appAccountK = '3')
                 AND wa.apprSt                        = :apprSt
                 AND (wr.requestAppCd                 = :vendorCd
                                                      OR :workerCd IS NULL
                                                      OR :workerCd = ''
                                                      OR wr.requestAppCd = :workerCd)
                 AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
                 AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
                 AND (:partnerVendorCd IS NULL        OR :partnerVendorCd = ''
                                                      OR mv.partnerVendorCd LIKE %:partnerVendorCd%)
            ORDER BY mv.partnerVendorCd
      """)
  List<VendorApprInfoDto> findVendorInfo(
      @NotNull @Param("vendorCd") String vendorCd,
      @Param("partnerVendorCd") String partnerVendorCd,
      @Param("workerCd") String workerCd,
      @Param("requestDateFrom") OffsetDateTime requestDateFrom,
      @Param("requestDateTo") OffsetDateTime requestDateTo,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("apprSt") String apprSt);

  /**
   * 検索処理(承認済、差戻).
   *
   * @param vendorCd 業者コード
   * @param partnerVendorCd 協力業者コード
   * @param workerCd 作業員コード
   * @param requestDateFrom 申請日（開始）
   * @param requestDateTo 申請日（終了）
   * @param itemClassCd 項目分類コード
   * @param businessTypeCd 業務種類コード
   * @param listApprStatus       承認状態リスト
   * @return 承認一覧（自社情報）
   */
  @Query(
      """
                SELECT new com.daitoj.tkms.modules.apis0140.service.dto.VendorApprInfoDto(
                    mv.partnerVendorCd,
                    mv.compNm,
                    mv.compKnNm,
                    mv.branchNm,
                    mv.branchKnNm,
                    TO_CHAR(wr.requestTs, 'YYYYMMDD'),
                    bn.finalApprDt,
                    il.itemValue,
                    wa.decisionComment,
                    il.id.itemCd)
              FROM TWfRequest wr
          INNER JOIN TWfApprStep wa         ON wr.id = wa.id
          INNER JOIN TBusinessNewestAppr bn ON wr.businessDataId = bn.businessDataId
                                           AND bn.businessTblId = 'M_VENDOR'
          INNER JOIN MVendor mv             ON wr.businessDataId = mv.id
          INNER JOIN MItemListSetting il    ON wa.apprSt = il.id.itemCd
                                           AND il.id.itemClassCd = :itemClassCd
             WHERE wr.businessTypeCd.businessTypeCd = :businessTypeCd
                 AND (wr.appAccountK                  = '2'
                                                      OR  wr.appAccountK = '3')
                 AND wa.apprSt                        = :listApprStatus
                 AND (wr.requestAppCd                 = :vendorCd
                                                      OR :workerCd IS NULL
                                                      OR :workerCd = ''
                                                      OR wr.requestAppCd = :workerCd)
                 AND COALESCE(:requestDateFrom, wr.requestTs) <= wr.requestTs
                 AND COALESCE(:requestDateTo, wr.requestTs) >= wr.requestTs
                 AND (:partnerVendorCd IS NULL        OR :partnerVendorCd = ''
                                                      OR mv.partnerVendorCd LIKE %:partnerVendorCd%)
            ORDER BY mv.partnerVendorCd
          """)
  List<VendorApprInfoDto> findVendorEndInfo(
      @NotNull @Param("vendorCd") String vendorCd,
      @Param("partnerVendorCd") String partnerVendorCd,
      @Param("workerCd") String workerCd,
      @Param("requestDateFrom") OffsetDateTime requestDateFrom,
      @Param("requestDateTo") OffsetDateTime requestDateTo,
      @Param("itemClassCd") String itemClassCd,
      @Param("businessTypeCd") String businessTypeCd,
      @Param("listApprStatus") List<String> listApprStatus);
}
