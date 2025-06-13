package com.daitoj.tkms.modules.apil0010.repository;

import com.daitoj.tkms.domain.TCustomerInvoiceHdr;
import com.daitoj.tkms.modules.apil0010.service.dto.CustomerInvoiceHdrInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 請求一覧のリポジトリ. */
@Repository
public interface L0010Repository extends JpaRepository<TCustomerInvoiceHdr, Long> {

  /**
   * 初期表示.
   *
   * @param belongOfficeCd 所属事業所コード
   * @return 請求情報
   */
  @Query(
      """
                SELECT new com.daitoj.tkms.modules.apil0010.service.dto.CustomerInvoiceHdrInfoDto(
                    tc.id,
                    tps.projectSiteCd,
                    tps.projectSiteNm,
                    mc.customerCd,
                    CONCAT(
                        COALESCE(mc.customerNm1, ''),
                        COALESCE(mc.customerNm2, '')
                    ),
                    tps.constrStartYmd,
                    tps.constrCompYmd,
                    tc.invoiceNo,
                    mp.paymentTermsNm,
                    tc.invoiceDt,
                    tps.inclTaxCoTotalAmt,
                     tc.invoiceTotalAmt,
                    tci.paymentAmt,
                     tc.hisNo)
                  FROM TCustomerInvoiceHdr tc
             LEFT JOIN TProjectSite tps
                    ON tc.projectSiteCd = tps.projectSiteCd
             LEFT JOIN MCustomer mc
                    ON SUBSTRING(tps.customerBranchCd, 1, 6) = mc.customerCd
             LEFT JOIN TCustomerInvoiceDtl tci
                    ON tc.id = tci.id
             LEFT JOIN MPaymentTerm mp
                    ON mp.paymentTermsCd = tci.paymentTermsCd
                 WHERE (:belongOfficeCd IS NULL
                    OR tps.icOfficeCd.officeCd = :belongOfficeCd)
              ORDER BY mc.customerCd, tps.projectSiteCd
      """)
  List<CustomerInvoiceHdrInfoDto> findInitInfo(@Param("belongOfficeCd") String belongOfficeCd);

  /**
   * 検索処理.
   *
   * @param belongOfficeCd 所属事務所コード
   * @param customerCd 顧客コード
   * @param customerNm 顧客名
   * @param projectSiteCd 物件コード
   * @param projectSiteNm 物件名
   * @param invoiceNo 請求書No
   * @param constrStartYmdFrom 物件着手日(開始)
   * @param constrStartYmdTo 物件着手日(終了)
   * @param constrCompYmdFrom 物件引渡日(開始)
   * @param constrCompYmdTo 物件引渡日(終了)
   * @return 請求書情報
   */
  @Query(
      """
                SELECT new com.daitoj.tkms.modules.apil0010.service.dto.CustomerInvoiceHdrInfoDto(
                    tc.id,
                    tps.projectSiteCd,
                    tps.projectSiteNm,
                    mc.customerCd,
                    CONCAT(
                        COALESCE(mc.customerNm1, ''),
                        COALESCE(mc.customerNm2, '')
                    ),
                    tps.constrStartYmd,
                    tps.constrCompYmd,
                    tc.invoiceNo,
                    mp.paymentTermsNm,
                    tc.invoiceDt,
                    tps.inclTaxCoTotalAmt,
                     tc.invoiceTotalAmt,
                    tci.paymentAmt,
                     tc.hisNo)
                  FROM TCustomerInvoiceHdr tc
             LEFT JOIN TProjectSite tps
                    ON tc.projectSiteCd = tps.projectSiteCd
             LEFT JOIN MCustomer mc
                    ON SUBSTRING(tps.customerBranchCd, 1, 6) = mc.customerCd
             LEFT JOIN TCustomerInvoiceDtl tci
                    ON tc.id = tci.id
             LEFT JOIN MPaymentTerm mp
                    ON mp.paymentTermsCd = tci.paymentTermsCd
                 WHERE (:belongOfficeCd     IS NULL
                    OR tps.icOfficeCd.officeCd = :belongOfficeCd)
                   AND (:customerCd         IS NULL
                    OR mc.customerCd                          LIKE %:customerCd%)
                   AND (:customerNm         IS NULL
                    OR CONCAT(mc.customerNm1, mc.customerNm2) LIKE %:customerNm%)
                   AND (:projectSiteCd      IS NULL
                    OR tps.projectSiteCd                      LIKE %:projectSiteCd%)
                   AND (:projectSiteNm      IS NULL
                    OR tps.projectSiteNm                      LIKE %:projectSiteNm%)
                   AND (:invoiceNo          IS NULL
                    OR tc.invoiceNo                           LIKE %:invoiceNo%)
                   AND (:constrStartYmdFrom IS NULL
                    OR :constrStartYmdFrom = '' OR tps.constrStartYmd >= :constrStartYmdFrom)
                   AND (:constrStartYmdTo   IS NULL
                    OR :constrStartYmdTo = ''   OR tps.constrStartYmd <= :constrStartYmdTo)
                   AND (:constrCompYmdFrom  IS NULL
                    OR :constrCompYmdFrom = ''  OR tps.constrCompYmd  >= :constrCompYmdFrom)
                   AND (:constrCompYmdTo    IS NULL
                    OR :constrCompYmdTo = ''    OR tps.constrCompYmd  <= :constrCompYmdTo)
                   AND ( (:displayItem1 = '0' AND :displayItem2 = '0' AND :displayItem3 = '0') OR
                       (:displayItem1 = '1' AND (tc.invoiceTotalAmt = 0
                                             OR tc.invoiceTotalAmt IS NULL))
                         OR (:displayItem2 = '1' AND tc.invoiceTotalAmt < tps.inclTaxCoTotalAmt)
                         OR (:displayItem3 = '1' AND tc.invoiceTotalAmt = tps.inclTaxCoTotalAmt) )
              ORDER BY mc.customerCd, tps.projectSiteCd
      """)
  List<CustomerInvoiceHdrInfoDto> findCustomerInvoiceInfo(
      @Param("belongOfficeCd") String belongOfficeCd,
      @Param("customerCd") String customerCd,
      @Param("customerNm") String customerNm,
      @Param("projectSiteCd") String projectSiteCd,
      @Param("projectSiteNm") String projectSiteNm,
      @Param("invoiceNo") String invoiceNo,
      @Param("constrStartYmdFrom") String constrStartYmdFrom,
      @Param("constrStartYmdTo") String constrStartYmdTo,
      @Param("constrCompYmdFrom") String constrCompYmdFrom,
      @Param("constrCompYmdTo") String constrCompYmdTo,
      @Param("displayItem1") String displayItem1,
      @Param("displayItem2") String displayItem2,
      @Param("displayItem3") String displayItem3);
}
