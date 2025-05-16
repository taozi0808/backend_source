//package com.daitoj.tkms.modules.apim0010.repository;
//
//import com.daitoj.tkms.domain.TConstrSiteExpHdr;
//import com.daitoj.tkms.modules.apim0010.service.dto.GenbakeihiInfoDto;
//import java.time.LocalDate;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
///** 現場経費一覧のリポジトリ */
//@Repository
//public interface M0010Repository extends JpaRepository<TConstrSiteExpHdr, Long> {
//
//  /**
//   * 初期表示データ取得
//   *
//   * @param jigyousyoCode 所属事業所コード
//   * @param jigyousyoName 所属事業所名
//   * @return 現場経費一覧
//   */
//  // TODO
//  // ※（ｶﾅ含む）の項目については、半角全角を区別しないこととする。
//  @Query(
//      """
//              SELECT new com.daitoj.tkms.modules.apim0010.service.dto.GenbakeihiInfoDto(
//                    tc.constrSiteCd,
//                    tc.constrSiteNm,
//                    tc.constrSiteCyakusyuYmd,
//                    tc.constrSiteHikiwatashiYmd,
//                    ji.empNm,
//                    th.constrSiteCd,
//                    th.expReqDt,
//                    me.empNm,
//                    th.constrSiteCd,
//                    th.expAprDt,
//                    th.totalAmt)
//                FROM TConstrSiteExpHdr th
//          INNER JOIN TConstrSite tc        ON th.constrSiteCd = tc.constrSiteCd
//          INNER JOIN TProjectSite tp       ON tc.projectSite.projectSiteCd = tp.projectSiteCd
//          LEFT  JOIN MEmp ji               ON th.projectCd = ji.empCd
//          LEFT  JOIN MEmp me               ON th.projectCd = me.empCd
//               WHERE (:jigyousyoName  IS NULL  OR tp.icOfficeCd.officeCd = :jigyousyoCode)
//            ORDER BY th.constrSiteCd
//          """)
//  Page<GenbakeihiInfoDto> findInitInfo(
//      @Param("jigyousyoCode") String jigyousyoCode,
//      @Param("jigyousyoName") String jigyousyoName,
//      Pageable pageable);
//
//  /**
//   * 検索処理
//   *
//   * @param jigyousyoCode 所属事業所コード
//   * @param jigyousyoName 所属事業所名
//   * @param genbaCode 現場コード
//   * @param genbaName 現場名
//   * @param shinseisya 申請者
//   * @param gcYmdStart 現場着手日（開始）
//   * @param gcYmdEnd 現場着手日（終了）
//   * @param ghYmdStart 現場引渡日（開始）
//   * @param ghYmdEnd 現場引渡日（終了）
//   * @return 現場経費一覧
//   */
//  @Query(
//      """
//              SELECT new com.daitoj.tkms.modules.apim0010.service.dto.GenbakeihiInfoDto(
//                    tc.constrSiteCd,
//                    tc.constrSiteNm,
//                    tc.constrSiteCyakusyuYmd,
//                    tc.constrSiteHikiwatashiYmd,
//                    ji.empNm,
//                    th.constrSiteCd,
//                    th.expReqDt,
//                    me.empNm,
//                    th.constrSiteCd,
//                    th.expAprDt,
//                    th.totalAmt)
//                FROM TConstrSiteExpHdr th
//          INNER JOIN TConstrSite tc        ON th.constrSiteCd = tc.constrSiteCd
//          INNER JOIN TProjectSite tp       ON tc.projectSite.projectSiteCd = tp.projectSiteCd
//          LEFT  JOIN MEmp ji               ON th.projectCd = ji.empCd
//          LEFT  JOIN MEmp me               ON th.projectCd = me.empCd
//               WHERE (:jigyousyoName  IS NULL  OR tp.icOfficeCd.officeCd = :jigyousyoCode)
//                 AND (:genbaCode      IS NULL  OR th.constrSiteCd   LIKE  %:genbaCode%)
//                 AND (:genbaName      IS NULL  OR tc.constrSiteNm   LIKE  %:genbaName%
//                                               OR tc.constrSiteKnNm LIKE  %:genbaName%)
//                 AND (:shinseisya     IS NULL  OR ji.empCd          LIKE  %:shinseisya%)
//                 AND COALESCE(:gcYmdStart, tc.constrSiteCyakusyuYmd) <= tc.constrSiteCyakusyuYmd
//                 AND tc.constrSiteCyakusyuYmd <= COALESCE(:gcYmdEnd, tc.constrSiteCyakusyuYmd)
//                 AND COALESCE(:ghYmdStart, tc.constrSiteHikiwatashiYmd) <= tc.constrSiteHikiwatashiYmd
//                 AND tc.constrSiteHikiwatashiYmd <= COALESCE(:ghYmdEnd, tc.constrSiteHikiwatashiYmd)
//            ORDER BY tc.constrSiteCd
//          """)
//  Page<GenbakeihiInfoDto> findGenbakeihiInfo(
//      @Param("jigyousyoCode") String jigyousyoCode,
//      @Param("jigyousyoName") String jigyousyoName,
//      @Param("genbaCode") String genbaCode,
//      @Param("genbaName") String genbaName,
//      @Param("shinseisya") String shinseisya,
//      @Param("gcYmdStart") LocalDate gcYmdStart,
//      @Param("gcYmdEnd") LocalDate gcYmdEnd,
//      @Param("ghYmdStart") LocalDate ghYmdStart,
//      @Param("ghYmdEnd") LocalDate ghYmdEnd,
//      Pageable pageable);
//}
