package com.daitoj.tkms.modules.apig0010.repository;

import com.daitoj.tkms.domain.TExecBgtHdr;
import com.daitoj.tkms.modules.apig0010.service.dto.ExecBgInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 実行予算一覧のリポジトリ. */
@Repository
public interface G0010Repository extends JpaRepository<TExecBgtHdr, Long> {

  /**
   * 初期表示データ取得.
   *
   * @param belongOfficeCd 所属事業所コード
   * @return 実行予算一覧
   */
  @Query(
      """
                   SELECT new com.daitoj.tkms.modules.apig0010.service.dto.ExecBgInfoDto(
                         te.id,
                         te.constrSiteCd,
                         tc.constrSiteNm,
                         tc.constrSiteKnNm,
                         te.execBgtCd,
                         te.bgtYmReqDt,
                         tb.finalApprDt,
                         te.bgtCreateOrgId,
                         mo.orgNm,
                         te.bgtCreatePicCd,
                         me.empNm,
                         te.execBgtTotalAmt,
                         SUM(tebd.poAmt),
                         ta.assessTotalAmt)
                   FROM TExecBgtHdr te
              LEFT JOIN TConstrSite tc           ON te.constrSiteCd = tc.constrSiteCd
              LEFT JOIN TProjectSite tp          ON SUBSTRING(tc.constrSiteCd, 1, 7)
                                                    = tp.projectSiteCd
              LEFT JOIN MOrg mo                  ON te.execBgtCd = mo.orgCd
              LEFT JOIN MEmp  me                 ON te.bgtCreatePicCd = me.empCd
              LEFT JOIN TBusinessNewestAppr tb   ON tb.businessTblId = 'T_EXEC_BGT_HDR'
                                                AND tb.businessDataId = te.id
              LEFT JOIN TExecBgtDtl teb          ON teb.execBgtHid.id = te.id
              LEFT JOIN TExecBgtDtlPo tebd       ON tebd.execBgtDid.id = teb.id
              LEFT JOIN TAssessHdr ta            ON ta.constrSiteCd = tc.constrSiteCd
                                                AND ta.assessYm   = TO_CHAR(CURRENT_DATE, 'YYYYMM')
                  WHERE (:belongOfficeCd IS NULL OR tp.icOfficeCd.officeCd = :belongOfficeCd)
               GROUP BY  te.id,
                         te.constrSiteCd,
                         tc.constrSiteNm,
                         tc.constrSiteKnNm,
                         te.execBgtCd,
                         te.bgtYmReqDt,
                         tb.finalApprDt,
                         te.bgtCreateOrgId,
                         mo.orgNm,
                         te.bgtCreatePicCd,
                         me.empNm,
                         te.execBgtTotalAmt,
                         ta.assessTotalAmt
              """)
  List<ExecBgInfoDto> findInitInfo(String belongOfficeCd);

  /**
   * 検索処理.
   *
   * @param execBgtCd 実行予算コード
   * @param constrSiteCd 現場コード
   * @param bgtYmRegDtStart 予算申請日（開始）
   * @param bgtYmRegDtEnd 予算申請日（終了）
   * @param finalApprDtStart 予算承認日（開始）
   * @param finalApprDtEnd 予算承認日（終了）
   * @param orgNm 予算作成部門
   * @param empNm 予算作成者
   * @param belongOfficeCd 所属事業所コード
   */
  @Query(
      """
           SELECT new com.daitoj.tkms.modules.apig0010.service.dto.ExecBgInfoDto(
               te.id,
               te.constrSiteCd,
               tc.constrSiteNm,
               tc.constrSiteKnNm,
               te.execBgtCd,
               te.bgtYmReqDt,
               tb.finalApprDt,
               te.bgtCreateOrgId,
               mo.orgNm,
               te.bgtCreatePicCd,
               me.empNm,
               te.execBgtTotalAmt,
               SUM(tebd.poAmt),
               ta.assessTotalAmt)
             FROM TExecBgtHdr te
        LEFT JOIN TConstrSite tc           ON te.constrSiteCd = tc.constrSiteCd
        LEFT JOIN TProjectSite tp          ON SUBSTRING(tc.constrSiteCd, 1, 7) = tp.projectSiteCd
        LEFT JOIN MOrg mo                  ON te.execBgtCd = mo.orgCd
        LEFT JOIN MEmp  me                 ON te.bgtCreatePicCd = me.empCd
        LEFT JOIN TBusinessNewestAppr tb   ON tb.businessTblId = 'T_EXEC_BGT_HDR'
                                          AND tb.businessDataId = te.id
        LEFT JOIN TExecBgtDtl teb          ON teb.execBgtHid.id = te.id
        LEFT JOIN TExecBgtDtlPo tebd       ON tebd.execBgtDid.id = teb.id
        LEFT JOIN TAssessHdr ta            ON ta.constrSiteCd = tc.constrSiteCd
                                          AND ta.assessYm   = TO_CHAR(CURRENT_DATE, 'YYYYMM')
            WHERE (:execBgtCd        IS NULL OR :execBgtCd = ''         OR te.execBgtCd
                                                                      LIKE %:execBgtCd%)
              AND (:constrSiteCd     IS NULL OR :constrSiteCd = ''      OR te.constrSiteCd
                                                                      LIKE %:constrSiteCd%)
              AND (:bgtYmRegDtStart  IS NULL OR :bgtYmRegDtStart = ''   OR :bgtYmRegDtStart
                                                                        <= te.bgtYmReqDt)
              AND (:bgtYmRegDtEnd    IS NULL OR :bgtYmRegDtEnd = ''     OR te.bgtYmReqDt
                                                                        <= :bgtYmRegDtEnd)
              AND (:finalApprDtStart IS NULL OR :finalApprDtStart = ''  OR :finalApprDtStart
                                                                        <= tb.finalApprDt)
              AND (:finalApprDtEnd   IS NULL OR :finalApprDtEnd = ''    OR tb.finalApprDt
                                                                        <= :finalApprDtEnd)
              AND (:orgNm            IS NULL OR :orgNm = ''             OR mo.orgNm
                                                                      LIKE %:orgNm)
              AND (:empNm            IS NULL OR :empNm = ''             OR me.empNm
                                                                      LIKE %:empNm)
              AND (:belongOfficeCd   IS NULL OR :belongOfficeCd = ''    OR tp.icOfficeCd.officeCd
                                                                      LIKE %:belongOfficeCd)
              AND (:yosanSakuseiZumiFlg != '0' OR tp.constrCompYmd >= TO_CHAR(CURRENT_DATE, 'YYYYMM'))
         GROUP BY te.id,
                  te.constrSiteCd,
                  tc.constrSiteNm,
                  tc.constrSiteKnNm,
                  te.execBgtCd,
                  te.bgtYmReqDt,
                  tb.finalApprDt,
                  te.bgtCreateOrgId,
                  mo.orgNm,
                  te.bgtCreatePicCd,
                  me.empNm,
                  te.execBgtTotalAmt,
                  ta.assessTotalAmt
        """)
  List<ExecBgInfoDto> findJikkouyosanInfo(
      @Param("execBgtCd") String execBgtCd,
      @Param("constrSiteCd") String constrSiteCd,
      @Param("bgtYmRegDtStart") String bgtYmRegDtStart,
      @Param("bgtYmRegDtEnd") String bgtYmRegDtEnd,
      @Param("finalApprDtStart") String finalApprDtStart,
      @Param("finalApprDtEnd") String finalApprDtEnd,
      @Param("orgNm") String orgNm,
      @Param("empNm") String empNm,
      @Param("belongOfficeCd") String belongOfficeCd,
      @Param("yosanSakuseiZumiFlg") String yosanSakuseiZumiFlg);
}
