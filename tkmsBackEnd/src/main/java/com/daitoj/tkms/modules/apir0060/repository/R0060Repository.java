package com.daitoj.tkms.modules.apir0060.repository;

import com.daitoj.tkms.domain.MCustomer;
import com.daitoj.tkms.modules.apir0060.service.dto.CustomerBankBankBanchInfoDto;
import com.daitoj.tkms.modules.apir0060.service.dto.CustomerInfoDto;
import com.daitoj.tkms.modules.apir0060.service.dto.TProjectSiteInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

/**
 * 顧客管理のリポジトリ.
 */
@Repository
public interface R0060Repository extends JpaRepository<MCustomer, Long> {

    /**
     * 初期表示データ取得
     *
     * @param customerCd 顧客コード
     * @return 顧客情報
     */
    @Query(
        """
              SELECT new com.daitoj.tkms.modules.apir0060.service.dto.CustomerBankBankBanchInfoDto(
                       mc.customerCd,
                       mc.customerNm1,
                       mc.customerNm2,
                       mc.customerRyakusyou,
                       mc.customerKnNm,
                       mc.customerPicNm,
                       mc.customerPicKnNm,
                       mc.tradingK,
                       mc.customerPostNo,
                       mc.customerAddr1,
                       mc.customerAddr2,
                       mc.customerTelNo,
                       mc.customerPicPhoneNo,
                       mc.customerFaxNo,
                       mc.bankNm,
                       mc.bankBranchNm,
                       mc.depositType,
                       mc.bankAccountNo,
                       mc.bankAccountHolderNm,
                       mc.capital,
                       mc.employeeNumber,
                       mc.gyousyuGyoutai,
                       mc.ceoNm,
                       mc.compUrl,
                       mc.remarks
                  )
                FROM MCustomer mc
               WHERE mc.customerCd = :customerCd
         """)
    Optional<CustomerBankBankBanchInfoDto> getKokyaku(String customerCd);

  /**
   * 初期表示データ取得
   *
   * @param customerCd 顧客コード
   * @param effectiveStartDt サーバ.システム日付
   * @return 取引履歴リスト
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apir0060.service.dto.TProjectSiteInfoDto(
                       tpjs.projectSiteCd,
                       tpjs.projectSiteNm,
                       tpjs.inclTaxCoTotalAmt,
                       tcih.invoiceTotalAmt,
                       tpjs.constrCompYmd,
                       mcp1.constrProcessNm,
                       tp.orderExpectedYmd,
                       tpjs.constrStartYmd
                  )
                FROM TProjectSite tpjs
           lEFT JOIN TCustomerInvoiceHdr tcih
                  ON tpjs.projectCd = tcih.projectCd
                 AND :effectiveStartDt > tcih.invoiceDt
           LEFT JOIN LATERAL (
                        SELECT mcp.constrProcessNm as constrProcessNm
                          FROM TProjectSite tpjs1
                     INNER JOIN TConstrSite tcs
                             ON tcs.projectSite = tpjs1
                     INNER JOIN TConstrWbsHdr tcw
                             ON tcw.constrSiteCd = tcs.constrSiteCd
                     INNER JOIN TConstrWbsDtl tcwd
                             ON tcwd.constrWbsHid.id = tcw.id
                            AND tcwd.actStartDt IS NOT null
                            AND tcwd.actEndDt IS null
                     INNER JOIN MConstrProcess mcp
                             ON tcwd.constrProcessCd.constrProcessCd = mcp.constrProcessCd
                          WHERE tpjs1.projectCd = tpjs.projectCd
                       ORDER BY tcwd.actStartDt , mcp.displayOrder , tcwd.seqNo
                          LIMIT 1
                    ) as mcp1
           lEFT JOIN TProject tp
                  ON tp.projectCd = tpjs.projectCd
               WHERE tpjs.customerBranchCd = :customerCd
           ORDER BY tpjs.projectCd DESC
              LIMIT 10
         """)
  List<TProjectSiteInfoDto> getTProjectSiteInfoDto(
      String customerCd, String effectiveStartDt);

    /**
     * 初期表示データ取得CustomerInfoDto
     *
     * @param customerCd 顧客コード
     * @return 顧客情報
     */
    @Query(
        """
              SELECT new com.daitoj.tkms.modules.apir0060.service.dto.CustomerInfoDto
               (
                   mc.id,
                   mc.hisNo,
                   CASE WHEN tbna.businessDataSt IS NULL THEN '1' ELSE tbna.businessDataSt END
               )
                FROM MCustomer mc
           Left JOIN TBusinessNewestAppr tbna ON tbna.businessDataId = mc.id
                 AND tbna.businessTblId = "M_CUSTOMER"
               WHERE mc.customerCd = :customerCd
         """)
    Optional<CustomerInfoDto> findByMCustomer(String customerCd);





}
