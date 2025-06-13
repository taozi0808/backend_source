package com.daitoj.tkms.modules.apim0010.repository;

import com.daitoj.tkms.domain.TConstrSiteExpHdr;
import com.daitoj.tkms.modules.apim0010.service.dto.GenbakeihiInfoDto;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 現場経費一覧のリポジトリ */
@Repository
public interface M0010Repository extends JpaRepository<TConstrSiteExpHdr, Long> {

  /**
   * 初期表示データ取得
   *
   * @param jigyousyoCode 所属事業所コード
   * @param jigyousyoName 所属事業所名
   * @return 現場経費一覧
   */
  // ※（ｶﾅ含む）の項目については、半角全角を区別しないこととする。
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apim0010.service.dto.GenbakeihiInfoDto(
                    th.constrSiteCd,
                    tc.constrSiteNm,
                    tc.constrSiteStartYmd,
                    tc.constrSiteDeliveryYmd,
                    ji.empNm,
                    tbna.requestAppCd,
                    th.expReqDt,
                    me.empNm,
                    tbna.finalApprEmpCd,
                    tbna.finalApprDt,
                    th.totalAmt)
                FROM TConstrSiteExpHdr th
          INNER JOIN TConstrSite tc           ON th.constrSiteCd = tc.constrSiteCd
          INNER JOIN TProjectSite tp          ON tc.projectSite.projectSiteCd = tp.projectSiteCd
          LEFT  JOIN TBusinessNewestAppr tbna ON tbna.businessDataId = th.id
                 AND tbna.businessTblId = "T_CONSTR_SITE_EXP_HDR"
          LEFT  JOIN MEmp ji                  ON tbna.requestAppCd   = ji.empCd
          LEFT  JOIN MEmp me                  ON tbna.finalApprEmpCd = me.empCd
               WHERE (:jigyousyoName  IS NULL  OR tp.icOfficeCd.officeCd = :jigyousyoCode)
            ORDER BY th.constrSiteCd
          """)
  Page<GenbakeihiInfoDto> findInitInfo(
      @Param("jigyousyoCode") String jigyousyoCode,
      @Param("jigyousyoName") String jigyousyoName,
      Pageable pageable);

  /**
   * 検索処理
   *
   * @param jigyousyoCode 所属事業所コード
   * @param jigyousyoName 所属事業所名
   * @param genbaCode 現場コード
   * @param genbaName 現場名
   * @param shinseisya 申請者
   * @param gcYmdStart 現場着手日（開始）
   * @param gcYmdEnd 現場着手日（終了）
   * @param ghYmdStart 現場引渡日（開始）
   * @param ghYmdEnd 現場引渡日（終了）
   * @return 現場経費一覧
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apim0010.service.dto.GenbakeihiInfoDto(
                    th.constrSiteCd,
                    tc.constrSiteNm,
                    tc.constrSiteStartYmd,
                    tc.constrSiteDeliveryYmd,
                    ji.empNm,
                    tbna.requestAppCd,
                    th.expReqDt,
                    me.empNm,
                    tbna.finalApprEmpCd,
                    tbna.finalApprDt,
                    th.totalAmt)
                FROM TConstrSiteExpHdr th
          INNER JOIN TConstrSite tc        ON th.constrSiteCd = tc.constrSiteCd
          INNER JOIN TProjectSite tp       ON tc.projectSite.projectSiteCd = tp.projectSiteCd
          LEFT  JOIN TBusinessNewestAppr tbna ON tbna.businessDataId = th.id
                 AND tbna.businessTblId = "T_CONSTR_SITE_EXP_HDR"
          LEFT  JOIN MEmp ji                  ON tbna.requestAppCd   = ji.empCd
          LEFT  JOIN MEmp me                  ON tbna.finalApprEmpCd = me.empCd
               WHERE (:jigyousyoName  IS NULL  OR tp.icOfficeCd.officeCd = :jigyousyoCode)
                 AND (:genbaCode      IS NULL  OR th.constrSiteCd   LIKE  %:genbaCode%)
                 AND (:genbaName      IS NULL  OR tc.constrSiteNm   LIKE  %:genbaName%
                                               OR tc.constrSiteKnNm LIKE  %:genbaName%)
                 AND (:shinseisya     IS NULL  OR ji.empNm          LIKE  %:shinseisya%)
                 AND COALESCE(:gcYmdStart, tc.constrSiteStartYmd) <= tc.constrSiteStartYmd
                 AND tc.constrSiteStartYmd <= COALESCE(:gcYmdEnd, tc.constrSiteStartYmd)
                 AND COALESCE(:ghYmdStart, tc.constrSiteDeliveryYmd) <= tc.constrSiteDeliveryYmd
                 AND tc.constrSiteDeliveryYmd <= COALESCE(:ghYmdEnd, tc.constrSiteDeliveryYmd)
            ORDER BY tc.constrSiteCd
          """)
  Page<GenbakeihiInfoDto> findGenbakeihiInfo(
      @Param("jigyousyoCode") String jigyousyoCode,
      @Param("jigyousyoName") String jigyousyoName,
      @Param("genbaCode") String genbaCode,
      @Param("genbaName") String genbaName,
      @Param("shinseisya") String shinseisya,
      @Param("gcYmdStart") String gcYmdStart,
      @Param("gcYmdEnd") String gcYmdEnd,
      @Param("ghYmdStart") String ghYmdStart,
      @Param("ghYmdEnd") String ghYmdEnd,
      Pageable pageable);
}
