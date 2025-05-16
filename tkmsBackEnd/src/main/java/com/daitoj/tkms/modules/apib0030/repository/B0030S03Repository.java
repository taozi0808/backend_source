package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TProjectPaymentTerms;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectPaymentTermsDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 案件請求条件情報のリポジトリ */
@Repository
public interface B0030S03Repository extends JpaRepository<TProjectPaymentTerms, Long> {

  /**
   * 案件請求条件情報を取得
   *
   * @param projectId 案件ID
   * @return 案件請求条件情報
   */
  @Query(
      """
          SELECT
              new com.daitoj.tkms.modules.apib0030.service.dto.ProjectPaymentTermsDto(
                   tp.projectId,
                   tp.seqNo,
                   tp.paymentTermsK,
                   null,
                   tp.paymentRatio,
                   tp.exclTaxPaymentAmt,
                   tp.inclTaxPaymentAmt,
                   tp.taxRateId,
                   null,
                   tp.salesTaxAmt,
                   tp.delFlg,
                   tp.regTs,
                   tp.regUserId,
                   tp.regPgId,
                   tp.updTs,
                   tp.updUserId,
                   tp.updPgId
                  )
           FROM TProjectPaymentTerms tp
          WHERE tp.projectId = :projectId
          """)
  List<ProjectPaymentTermsDto> findByProjectId(Long projectId);
}
