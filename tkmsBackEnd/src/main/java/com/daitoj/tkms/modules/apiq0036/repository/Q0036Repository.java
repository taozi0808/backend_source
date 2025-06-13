package com.daitoj.tkms.modules.apiq0036.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.domain.TProjectSite;
import com.daitoj.tkms.modules.apiq0036.service.dto.SagyouinInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 業員名簿業者一覧のリポジトリ */
@Repository
public interface Q0036Repository extends JpaRepository<TProjectSite, Long> {

  /**
   * 初期表示データ取得
   *
   * @param bukkenCode 物件コード
   * @return 作業員名簿業者一覧
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apiq0036.service.dto.SagyouinInfoDto(
                     tph.poNo,
                     tph.poTotalAmt,
                     LISTAGG(mmw.majorWorkNm, '、') WITHIN GROUP (ORDER BY mmw.majorWorkNm),
                     mv.partnerVendorCd,
                     mv.compNm,
                     tph.constrPeriodStratYmd,
                     tph.constrPeriodEndYmd
                              )
                FROM TProjectSite tps
          INNER JOIN TConstrSite tcs   ON tps.id = tcs.projectSite.id
          INNER JOIN TPoHdr tph        ON tph.constrSite.id = tcs.id
          INNER JOIN TPoDtl tpd        ON tpd.poHid.id = tph.id
          INNER JOIN MMajorWork mmw    ON mmw.majorWorkCd = tpd.majorWorkCd
          INNER JOIN MVendor mv        ON tph.vendorId = mv.id
            WHERE   (:bukkenCode       IS NULL
                     OR :bukkenCode = ''
                     OR tps.projectSiteCd = :bukkenCode)
            GROUP BY tph.poNo,
                     tph.poTotalAmt,
                     mmw.majorWorkNm,
                     mv.partnerVendorCd,
                     mv.compNm,
                     tph.constrPeriodStratYmd,
                     tph.constrPeriodEndYmd
          """)
  Page<SagyouinInfoDto> findInitInfo(String bukkenCode, Pageable pageable);


    /**
     * 検索処理
     *
     * @param daikousyu 大工事
     * @param gyoushaCode 協力業者コード
     * @param chuBunshoNo 注文書No
     * @param gaitouChakushuNichiStart 該当着手日（開始）
     * @param gaitouChakushuNichiEnd  該当着手日（終了）
     * @param gaitouBikiWataruNichiStart 該当引渡日（開始）
     * @param gaitouBikiWataruNichiEnd 該当引渡日（終了）
     * @return 作業員名簿業者情報リスト
     */
  @Query(
      """
                   SELECT new com.daitoj.tkms.modules.apiq0036.service.dto.SagyouinInfoDto(
                             tph.poNo,
                             tph.poTotalAmt,
                             LISTAGG(mmw.majorWorkNm, '、') WITHIN GROUP (ORDER BY mmw.majorWorkNm),
                             mv.partnerVendorCd,
                             mv.compNm,
                             tph.constrPeriodStratYmd,
                             tph.constrPeriodEndYmd
                                      )
                        FROM TProjectSite tps
                  INNER JOIN TConstrSite tcs   ON tps.id = tcs.projectSite.id
                  INNER JOIN TPoHdr tph        ON tph.constrSite.id = tcs.id
                  INNER JOIN TPoDtl tpd        ON tpd.poHid.id = tph.id
                  INNER JOIN MMajorWork mmw    ON mmw.majorWorkCd = tpd.majorWorkCd
                  INNER JOIN MVendor mv        ON tph.vendorId = mv.id
                       WHERE (:daikousyu                   IS NULL    OR :daikousyu = ''
                                                                      OR mmw.majorWorkNm  LIKE  %:daikousyu%)
                         AND (:gyoushaCode                 IS NULL    OR :gyoushaCode = ''
                                                                      OR  mv.vendorCd LIKE  %:gyoushaCode%)


                         AND (:chuBunshoNo                 IS NULL    OR :chuBunshoNo = ''
                                                                      OR  tph.poNo LIKE  %:chuBunshoNo%)
                         AND (:gaitouChakushuNichiStart    IS NULL    OR :gaitouChakushuNichiStart = ''
                                                                      OR :gaitouChakushuNichiStart <= tph.constrPeriodStratYmd)
                         AND (:gaitouChakushuNichiEnd      IS NULL    OR :gaitouChakushuNichiEnd = ''
                                                                      OR :gaitouChakushuNichiEnd >= tph.constrPeriodStratYmd)
                         AND (:gaitouBikiWataruNichiStart  IS NULL    OR :gaitouBikiWataruNichiStart = ''
                                                                      OR :gaitouBikiWataruNichiStart <= tph.constrPeriodEndYmd)
                         AND (:gaitouBikiWataruNichiEnd    IS NULL    OR :gaitouBikiWataruNichiEnd = ''
                                                                      OR :gaitouBikiWataruNichiEnd >= tph.constrPeriodEndYmd)
                    GROUP BY tph.poNo,
                             tph.poTotalAmt,
                             mmw.majorWorkNm,
                             mv.partnerVendorCd,
                             mv.compNm,
                             tph.constrPeriodStratYmd,
                             tph.constrPeriodEndYmd
              """)
  Page<SagyouinInfoDto> searchSagyouInfoList(
      String daikousyu,
      String gyoushaCode,
      String chuBunshoNo,
      String gaitouChakushuNichiStart,
      String gaitouChakushuNichiEnd,
      String gaitouBikiWataruNichiStart,
      String gaitouBikiWataruNichiEnd,
      Pageable pageable
  );
}
