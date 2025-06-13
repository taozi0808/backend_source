package com.daitoj.tkms.modules.apin0010.repository;

import com.daitoj.tkms.domain.TConstrWbsHdr;
import com.daitoj.tkms.modules.apin0010.service.dto.ConstrWbsInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/** 工事予実一覧のリポジトリ. */
@Repository
public interface N0010Repository extends JpaRepository<TConstrWbsHdr, Long> {

  /**
   * 初期表示データ取得.
   *
   * @return 工事予実一覧
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apin0010.service.dto.ConstrWbsInfoDto(
                   tcwh.id,
                   tcs.constrSiteCd,
                   tcs.constrSiteNm,
                   tcs.constrSiteStartYmd,
                   tcs.constrSiteDeliveryYmd,
                   tcwh.wbsCreateDt,
                   tcwh.createPicCd,
                   me.empNm,
                   tcs.constrSiteKnNm)
              FROM TConstrWbsHdr tcwh
        INNER JOIN TConstrSite               tcs ON tcwh.constrSiteCd    = tcs.constrSiteCd
        INNER JOIN TProjectSite              tps ON tcs.projectSite      = tps
        INNER JOIN MEmp                       me ON tcwh.createPicCd     = me.empCd
             WHERE (:belongOfficeCd         IS NULL
                     OR tps.icOfficeCd.officeCd    LIKE %:belongOfficeCd%)

        """)
  List<ConstrWbsInfoDto> findInitInfo(String belongOfficeCd);

  /**
   * 検索処理.
   *
   * @return 工事予実一覧
   */
  @Query(
      """
        SELECT new com.daitoj.tkms.modules.apin0010.service.dto.ConstrWbsInfoDto(
                        tcwh.id,
                        tcs.constrSiteCd,
                        tcs.constrSiteNm,
                        tcs.constrSiteStartYmd,
                        tcs.constrSiteDeliveryYmd,
                        tcwh.wbsCreateDt,
                        tcwh.createPicCd,
                        me.empNm,
                        tcs.constrSiteKnNm)
                   FROM TConstrWbsHdr tcwh
             INNER JOIN TConstrSite               tcs ON tcwh.constrSiteCd    = tcs.constrSiteCd
             INNER JOIN TProjectSite              tps ON tcs.projectSite      = tps
             INNER JOIN MEmp                       me ON tcwh.createPicCd     = me.empCd
                  WHERE (:belongOfficeCd      IS NULL
                          OR tps.icOfficeCd.officeCd LIKE %:belongOfficeCd%)
                    AND (:constrSiteCd        IS NULL
                          OR :constrSiteCd        = ''
                          OR tcwh.constrSiteCd          LIKE %:constrSiteCd%)
                    AND (:wbsCreateDtFrom IS NULL
                            OR :wbsCreateDtFrom = '' OR :wbsCreateDtFrom <= tcwh.wbsCreateDt)
                    AND (:wbsCreateDtTo IS NULL
                            OR :wbsCreateDtTo = '' OR :wbsCreateDtTo >= tcwh.wbsCreateDt)
                    AND (:constrSiteStartYmdFrom IS NULL
                            OR :constrSiteStartYmdFrom = ''
                            OR :constrSiteStartYmdFrom <= tcs.constrSiteStartYmd)
                    AND (:constrSiteStartYmdTo IS NULL
                            OR :constrSiteStartYmdTo = ''
                            OR :constrSiteStartYmdTo >= tcs.constrSiteStartYmd)
                    AND (:constrSiteDeliveryYmdFrom IS NULL
                            OR :constrSiteDeliveryYmdFrom = ''
                            OR :constrSiteDeliveryYmdFrom <= tcs.constrSiteDeliveryYmd)
                    AND (:constrSiteDeliveryYmdTo IS NULL
                            OR :constrSiteDeliveryYmdTo = ''
                            OR :constrSiteDeliveryYmdTo >= tcs.constrSiteDeliveryYmd)
                    AND (:empNm               IS NULL OR :empNm = ''
                                                      OR me.empNm LIKE %:empNm%)
        """)
  List<ConstrWbsInfoDto> findConstrWbsInfo(
      String belongOfficeCd,
      String constrSiteCd,
      String wbsCreateDtFrom,
      String wbsCreateDtTo,
      String constrSiteStartYmdFrom,
      String constrSiteStartYmdTo,
      String constrSiteDeliveryYmdFrom,
      String constrSiteDeliveryYmdTo,
      String empNm);
}
