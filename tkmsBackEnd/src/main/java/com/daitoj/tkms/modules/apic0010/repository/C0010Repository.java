package com.daitoj.tkms.modules.apic0010.repository;

import com.daitoj.tkms.domain.TRoughEstHdr;
import com.daitoj.tkms.modules.apic0010.service.dto.GaisanInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 概算一覧のリポジトリ */
@Repository
public interface C0010Repository extends JpaRepository<TRoughEstHdr, Long> {

  /**
   * 初期表示データ取得
   *
   * @return 概算一覧
   */
  @Query(
      """
         SELECT new com.daitoj.tkms.modules.apic0010.service.dto.GaisanInfoDto(
             treh.id,
             tp.projectCd,
             tp.hisNo,
             treh.roughEstCd,
             tp.projectNm,
             mc.customerCd,
             CONCAT(mc.customerNm1, mc.customerNm2),
             TO_CHAR(tp.estSubmitDueTs, 'YYYYMMDD'),
             treh.roughEstTotalAmt,
             tp.startHopeYmd,
             tp.compHopeYmd,
             mo.orgCd,
             mo.orgNm,
             treh.roughEstPicCd,
             me.empNm
             )
          FROM TRoughEstHdr treh
    INNER JOIN TProject tp  ON treh.projectCd = tp.projectCd
    INNER JOIN MCustomer mc ON mc.customerBranchCd  = tp.customerBranchCd
    LEFT  JOIN MOrg mo      ON mo.id          = treh.roughEstOrgId
    LEFT  JOIN MEmp me      ON me.empCd       = treh.roughEstPicCd
      """)
  Page<GaisanInfoDto> getGaisanInfo(Pageable pageable);

  /**
   * 検索処理
   *
   * @param projectCd 案件コード
   * @param projectNm 案件名（ｶﾅ含む）
   * @param roughEstCd 概算コード
   * @param customerCd 顧客コード
   * @param customerName 顧客名（ｶﾅ含む）
   * @param estSubmitDueDtStart 見積提出期限（開始）
   * @param estSubmitDueDtEnd 見積提出期限（終了）
   * @param orgNm 概算部門
   * @param empNm 概算担当者
   * @return 概算一覧
   */
  @Query(
      """
          SELECT new com.daitoj.tkms.modules.apic0010.service.dto.GaisanInfoDto(
              treh.id,
              tp.projectCd,
              tp.hisNo,
              treh.roughEstCd,
              tp.projectNm,
              mc.customerCd,
              CONCAT(mc.customerNm1, mc.customerNm2),
              TO_CHAR(tp.estSubmitDueTs, 'YYYYMMDD'),
              treh.roughEstTotalAmt,
              tp.startHopeYmd,
              tp.compHopeYmd,
              mo.orgCd,
              mo.orgNm,
              treh.roughEstPicCd,
              me.empNm
              )
           FROM TRoughEstHdr treh
     INNER JOIN TProject tp  ON treh.projectCd = tp.projectCd
     INNER JOIN MCustomer mc ON mc.customerBranchCd  = tp.customerBranchCd
     LEFT  JOIN MOrg mo      ON mo.id          = treh.roughEstOrgId
     LEFT  JOIN MEmp me      ON me.empCd       = treh.roughEstPicCd
          WHERE (:projectCd   IS NULL OR tp.projectCd    LIKE %:projectCd%)
            AND (:projectNm   IS NULL OR tp.projectNm LIKE %:projectNm%
             OR tp.projectKnNm    LIKE %:projectNm%)
            AND (:roughEstCd  IS NULL OR treh.roughEstCd LIKE %:roughEstCd%)
            AND (:customerCd IS NULL OR mc.customerCd LIKE %:customerCd%)
            AND (:customerName IS NULL OR mc.customerNm1 LIKE %:customerName%
             OR mc.customerNm2 LIKE %:customerName%
             OR mc.customerKnNm LIKE %:customerName%)
            AND (:estSubmitDueDtStart IS NULL OR :estSubmitDueDtStart <= TO_CHAR(tp.estSubmitDueTs, 'YYYYMMDD'))
            AND (:estSubmitDueDtEnd IS NULL OR TO_CHAR(tp.estSubmitDueTs, 'YYYYMMDD') <= :estSubmitDueDtEnd)
            AND (:orgNm IS NULL OR mo.orgNm LIKE %:orgNm%)
            AND (:empNm IS NULL OR me.empNm LIKE %:empNm%)
            AND (:gaisanSakusei = '0' OR treh.roughEstCreateFlg = '1')
      """)
  Page<GaisanInfoDto> searchGaisanInfo(
      @Param("projectCd") String projectCd,
      @Param("projectNm") String projectNm,
      @Param("roughEstCd") String roughEstCd,
      @Param("customerCd") String customerCd,
      @Param("customerName") String customerName,
      @Param("estSubmitDueDtStart") String estSubmitDueDtStart,
      @Param("estSubmitDueDtEnd") String estSubmitDueDtEnd,
      @Param("orgNm") String orgNm,
      @Param("empNm") String empNm,
      @Param("gaisanSakusei") String gaisanSakusei,
      Pageable pageable);
}
