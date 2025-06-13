package com.daitoj.tkms.modules.apiq0035.repository;

import com.daitoj.tkms.domain.TConstrSite;
import com.daitoj.tkms.modules.apiq0035.service.dto.GenbaInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 作業員名簿物件一覧のリポジトリ.
 */
@Repository
public interface Q0035Repository extends JpaRepository<TConstrSite, Long> {

  /**
   * 初期表示データ取得.
   *
   * @return 物件情報一覧
   */
  @Query(
      """
             SELECT new com.daitoj.tkms.modules.apiq0035.service.dto.GenbaInfoDto(
                     tp.id,
                     tp.projectSiteCd,
                     tp.projectSiteNm,
                     tp.ftEngineerCd,
                     me.empNm,
                     tc.constrSiteStartYmd,
                     tc.constrSiteDeliveryYmd,
                     CASE WHEN tw.id IS NULL THEN '0' ELSE '1' END,
                     tp.projectSiteKnNm,
                     me.empKnNm)
                FROM TProjectSite tp
          INNER JOIN TConstrSite tc    ON tp.id = tc.projectSite.id
          INNER JOIN MEmp me           ON tp.ftEngineerCd = me.empCd
           LEFT JOIN TWorkerListHdr tw ON tp.projectSiteCd = tw.projectSiteCd
            ORDER BY tp.projectSiteCd
          """)
  List<GenbaInfoDto> findInitInfo();

  /**
   * 検索処理.
   *
   * @param projectSiteCd             物件コード
   * @param constrSiteStartYmdFrom    現場着手日From
   * @param constrSiteStartYmdTo      現場着手日To
   * @param constrSiteDeliveryYmdFrom 現場引渡日From
   * @param constrSiteDeliveryYmdTo   現場引渡日To
   * @param docSubmissionStatus       書類提出状況
   * @return 現場情報一覧
   */
  @Query(
      """
             SELECT new com.daitoj.tkms.modules.apiq0035.service.dto.GenbaInfoDto(
                    tp.id,
                    tp.projectSiteCd,
                    tp.projectSiteNm,
                    tp.ftEngineerCd,
                    me.empNm,
                    tc.constrSiteStartYmd,
                    tc.constrSiteDeliveryYmd,
                    CASE WHEN tw.id IS NULL THEN '0' ELSE '1' END,
                    tp.projectSiteKnNm,
                    me.empKnNm)
                FROM TProjectSite tp
          INNER JOIN TConstrSite tc    ON tp.id = tc.projectSite.id
          INNER JOIN MEmp me           ON tp.ftEngineerCd = me.empCd
           LEFT JOIN TWorkerListHdr tw ON tp.projectSiteCd = tw.projectSiteCd
               WHERE (:projectSiteCd IS NULL
                      OR :projectSiteCd = ''
                      OR tp.projectSiteCd LIKE %:projectSiteCd%)
                 AND (:constrSiteStartYmdFrom IS NULL
                      OR :constrSiteStartYmdFrom = ''
                      OR :constrSiteStartYmdFrom <= tc.constrSiteStartYmd)
                 AND (:constrSiteStartYmdTo IS NULL
                      OR :constrSiteStartYmdTo = ''
                      OR tc.constrSiteStartYmd <= :constrSiteStartYmdTo)
                 AND (:constrSiteDeliveryYmdFrom IS NULL
                      OR :constrSiteDeliveryYmdFrom = ''
                      OR :constrSiteDeliveryYmdFrom  <= tc.constrSiteDeliveryYmd)
                 AND (:constrSiteDeliveryYmdTo IS NULL
                      OR :constrSiteDeliveryYmdTo = ''
                      OR  tc.constrSiteDeliveryYmd <= :constrSiteDeliveryYmdTo)
                 AND (:docSubmissionStatus IS NULL
                      OR :docSubmissionStatus = '0'
                      OR NOT EXISTS(
                              SELECT 1 FROM TWorkerListHdr tw
                                      WHERE tw.projectSiteCd = tp.projectSiteCd))
            ORDER BY tp.projectSiteCd
          """)
  List<GenbaInfoDto> findSateiInfo(
      @Param("projectSiteCd") String projectSiteCd,
      @Param("constrSiteStartYmdFrom") String constrSiteStartYmdFrom,
      @Param("constrSiteStartYmdTo") String constrSiteStartYmdTo,
      @Param("constrSiteDeliveryYmdFrom") String constrSiteDeliveryYmdFrom,
      @Param("constrSiteDeliveryYmdTo") String constrSiteDeliveryYmdTo,
      @Param("docSubmissionStatus") String docSubmissionStatus);
}
