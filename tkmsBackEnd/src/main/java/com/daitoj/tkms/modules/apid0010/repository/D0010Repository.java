package com.daitoj.tkms.modules.apid0010.repository;

import com.daitoj.tkms.domain.TDetailedEstHdr;
import com.daitoj.tkms.modules.apid0010.service.dto.DetailedEstInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 精積算一覧のリポジトリ. */
@Repository
public interface D0010Repository extends JpaRepository<TDetailedEstHdr, Long> {

  /**
   * 初期表示データ取得.
   *
   * @return 精積算一覧
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apid0010.service.dto.DetailedEstInfoDto(
                   tdeh.id,
                   tdeh.detailedEstCd,
                   tdeh.projectCd,
                     tp.projectNm,
                   treh.roughEstCd,
                     mc.customerCd,
                     CONCAT(
                        COALESCE(mc.customerNm1, ''),
                        COALESCE(mc.customerNm2, '')
                     ),
                   tdeh.detailedEstTotalAmt,
                   treh.roughEstTotalAmt,
                   treh.roughEstYmd,
                     tb.finalApprDt,
                     mo.orgCd,
                     mo.orgNm,
                     me.empCd,
                     me.empNm)
              FROM TDetailedEstHdr tdeh
        INNER JOIN TProject               tp ON tdeh.projectCd              = tp.projectCd
        INNER JOIN TRoughEstHdr         treh ON tdeh.projectCd              = treh.projectCd
                                            AND treh.buildingCd             = tdeh.buildingCd
        INNER JOIN MCustomer              mc ON SUBSTRING(tp.customerBranchCd, 1, 6) = mc.customerCd
         LEFT JOIN MOrg                   mo ON tdeh.detailedEstOrgId       = mo.id
         LEFT JOIN MEmp                   me ON tdeh.detailedEstPicCd       = me.empCd
         LEFT JOIN TBusinessNewestAppr    tb ON tdeh.id = tb.businessDataId
                                            AND tb.businessTblId = 'T_DETAILED_EST_HDR'
             WHERE tdeh.detailedEstCreateFlg = :detailedEstCreateFlg
          ORDER BY tdeh.projectCd, tdeh.detailedEstCd
      """)
  List<DetailedEstInfoDto> findInitInfo(@Param("detailedEstCreateFlg") String detailedEstCreateFlg);

  /**
   * 検索処理.
   *
   * @param projectCd 案件コード
   * @param customerCd 顧客コード
   * @param roughEstCd 概算コード
   * @param detailedEstCd 精積算コード
   * @param detailedEstOrgNm 精積算部門名
   * @param detailedEstPicNm 精積算担当者名
   * @param detailedEstCreateFlg 精積算作成済フラグ
   * @return 精積算情報
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apid0010.service.dto.DetailedEstInfoDto(
                   tdeh.id,
                   tdeh.detailedEstCd,
                   tdeh.projectCd,
                     tp.projectNm,
                   treh.roughEstCd,
                     mc.customerCd,
                     CONCAT(
                        COALESCE(mc.customerNm1, ''),
                        COALESCE(mc.customerNm2, '')
                     ),
                   tdeh.detailedEstTotalAmt,
                   treh.roughEstTotalAmt,
                   treh.roughEstYmd,
                     tb.finalApprDt,
                     mo.orgCd,
                     mo.orgNm,
                     me.empCd,
                     me.empNm)
              FROM TDetailedEstHdr tdeh
        INNER JOIN TProject               tp  ON tdeh.projectCd  = tp.projectCd
        INNER JOIN TRoughEstHdr         treh  ON tdeh.projectCd  = treh.projectCd
                                             AND treh.buildingCd = tdeh.buildingCd
        INNER JOIN MCustomer              mc  ON SUBSTRING(tp.customerBranchCd, 1, 6) = mc.customerCd
         LEFT JOIN MOrg                   mo  ON tdeh.detailedEstOrgId       = mo.id
         LEFT JOIN MEmp                   me  ON tdeh.detailedEstPicCd       = me.empCd
         LEFT JOIN TBusinessNewestAppr    tb  ON tdeh.id = tb.businessDataId
                                             AND tb.businessTblId = 'T_DETAILED_EST_HDR'
             WHERE tdeh.detailedEstCreateFlg = :detailedEstCreateFlg
               AND (:projectCd        IS NULL OR :projectCd        = '' OR tp.projectCd LIKE %:projectCd%)
               AND (:customerCd       IS NULL OR :customerCd       = '' OR mc.customerCd LIKE %:customerCd%)
               AND (:roughEstCd       IS NULL OR :roughEstCd       = '' OR treh.roughEstCd LIKE %:roughEstCd%)
               AND (:detailedEstCd    IS NULL OR :detailedEstCd    = '' OR tdeh.detailedEstCd LIKE %:detailedEstCd%)
               AND (:detailedEstOrgNm IS NULL OR :detailedEstOrgNm = '' OR mo.orgNm LIKE %:detailedEstOrgNm%)
               AND (:detailedEstPicNm IS NULL OR :detailedEstPicNm = '' OR me.empNm LIKE %:detailedEstPicNm%)
          ORDER BY tdeh.projectCd, tdeh.detailedEstCd
        """)
  List<DetailedEstInfoDto> findDetailedEstInfo(
      @Param("projectCd") String projectCd,
      @Param("customerCd") String customerCd,
      @Param("roughEstCd") String roughEstCd,
      @Param("detailedEstCd") String detailedEstCd,
      @Param("detailedEstOrgNm") String detailedEstOrgNm,
      @Param("detailedEstPicNm") String detailedEstPicNm,
      @Param("detailedEstCreateFlg") String detailedEstCreateFlg);

}
