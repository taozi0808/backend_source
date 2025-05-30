package com.daitoj.tkms.modules.apii0010.repository;

import com.daitoj.tkms.domain.TEvSim;
import com.daitoj.tkms.modules.apii0010.service.dto.DekidakaShimyurēsyonInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface I0010Repository  extends JpaRepository<TEvSim, Long> {

    /**
     * 初期表示データ取得
     *
     * @return 出来高シミュレーション一覧
     */

    @Query(
        """
            SELECT new com.daitoj.tkms.modules.apii0010.service.dto.DekidakaShimyurēsyonInfoDto(
                      tcs.constrSiteCd,
                      tcs.constrSiteNm,
                      treh.roughEstCd,
                      tp.projectNm,
                      tcs.constrSiteStartYmd,
                      tcs.constrSiteDeliveryYmd)
                FROM TEvSim tes
          INNER JOIN TRoughEstHdr treh   ON tes.roughEstHid = treh.id
          INNER JOIN TProject tp         ON treh.projectCd = tp.projectCd
          INNER JOIN TConstrSite tcs     ON tes.constrSiteCd = tcs.constrSiteCd
            """)
    Page<DekidakaShimyurēsyonInfoDto> findInitInfo(Pageable pageable);

  /**
   * 検索処理
   *
   * @param genbaCode 現場コード/概算コード
   * @param genbaName 現場名/案件名（ｶﾅ含む）
   * @param genbaChakkouYmdStart 現場着工日（開始）
   * @param genbaChakkouYmdEnd 現場着工日（終了）
   * @param genbaKankouYmdStart 現場完工日（開始）
   * @param genbaKankouYmdEnd 現場完工日（終了）
   * @return 出来高シミュレーション一覧
   */
  @Query(
      """
            SELECT
                      new com.daitoj.tkms.modules.apii0010.service.dto.DekidakaShimyurēsyonInfoDto(
                      tcs.constrSiteCd ,
                      tcs.constrSiteNm,
                      treh.roughEstCd,
                      tp.projectNm,
                      tcs.constrSiteStartYmd,
                      tcs.constrSiteDeliveryYmd)
                FROM TEvSim tes
          INNER JOIN TRoughEstHdr treh   ON tes.roughEstHid = treh.id
          INNER JOIN TProject tp         ON treh.projectCd = tp.projectCd
          INNER JOIN TConstrSite tcs     ON tes.constrSiteCd = tcs.constrSiteCd
               WHERE (:genbaCode IS NULL OR :genbaCode = ''
                                         OR tcs.constrSiteCd   LIKE %:genbaCode%
                                         OR treh.roughEstCd    LIKE %:genbaCode%)
                 AND (:genbaName IS NULL OR :genbaName = ''
                                         OR tcs.constrSiteNm   LIKE %:genbaName%
                                         OR tcs.constrSiteKnNm LIKE %:genbaName%
                                         OR tp.projectNm       LIKE %:genbaName%
                                         OR tp.projectKnNm     LIKE %:genbaName%)
                 AND (:genbaChakkouYmdStart IS NULL OR :genbaChakkouYmdStart = ''
                                                    OR :genbaChakkouYmdStart <= tcs.constrSiteStartYmd)
                 AND (:genbaChakkouYmdEnd   IS NULL OR :genbaChakkouYmdEnd = ''
                                                    OR :genbaChakkouYmdEnd >= tcs.constrSiteStartYmd)
                 AND (:genbaKankouYmdStart  IS NULL OR :genbaKankouYmdStart = ''
                                                    OR :genbaKankouYmdStart <= tcs.constrSiteDeliveryYmd)
                 AND (:genbaKankouYmdEnd    IS NULL OR :genbaKankouYmdEnd = ''
                                                    OR :genbaKankouYmdEnd >= tcs.constrSiteDeliveryYmd)
          """)
  Page<DekidakaShimyurēsyonInfoDto> findDekidakaShimyurēsyonInfo(
      String genbaCode,
      String genbaName,
      String genbaChakkouYmdStart,
      String genbaChakkouYmdEnd,
      String genbaKankouYmdStart,
      String genbaKankouYmdEnd,
      Pageable pageable);
}
