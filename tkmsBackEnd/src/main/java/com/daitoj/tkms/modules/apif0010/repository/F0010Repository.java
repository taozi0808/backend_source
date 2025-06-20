package com.daitoj.tkms.modules.apif0010.repository;

import com.daitoj.tkms.domain.TProjectSite;
import com.daitoj.tkms.modules.apif0010.service.dto.ProjectSiteInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 物件一覧のリポジトリ. */
@Repository
public interface F0010Repository extends JpaRepository<TProjectSite, Long> {

  /**
   * 初期表示データ取得.
   *
   * @return 物件一覧
   */
  @Query(
      """
             SELECT new com.daitoj.tkms.modules.apif0010.service.dto.ProjectSiteInfoDto(
                 tps.projectSiteCd,
                 tps.projectSiteNm,
                 tps.projectSiteKnNm,
                 tps.icOfficeCd.officeCd,
                 mof.officeNm,
                 mc.customerCd,
                 CONCAT(
                  COALESCE(mc.customerNm1, ''),
                  COALESCE(mc.customerNm2, '')
                 ),
                 mc.customerKnNm,
                 tps.orderYmd,
                 tps.inclTaxCoTotalAmt,
                 mo1.orgCd,
                 mo1.orgNm,
                 me1.empCd,
                 me1.empNm,
                 me2.empCd,
                 me2.empNm,
                 me3.empCd,
                 me3.empNm,
                 mc.customerRyakusyou,
                 tps.customerBranchCd,
                 tp.projectK,
                 mi.itemValue,
                 tps.constrStartYmd,
                 tps.constrCompYmd,
                 tps.projectCd,
                 tp.projectNm,
                 tcs.structureK,
                 mi1.itemValue,
                  tp.floorCnt,
                  tp.households,
                 CONCAT(
                    COALESCE(tps.projectSiteAddr1, ''),
                    COALESCE(tps.projectSiteAddr2, '')
                 ),
                 tps.constrSitePicCd1,
                 me4.empNm,
                 tps.constrSitePicCd2,
                 me5.empNm,
                 tps.constrSitePicCd3,
                 me6.empNm,
                 tps.constrSitePicCd4,
                 me7.empNm,
                 tps.constrSitePicCd5,
                 me8.empNm,
                 me4.compPhoneNo,
                 me5.compPhoneNo,
                 tps.constrSiteDirectorCd,
                 me9.empNm,
                 tps.deputyDirectorCd,
                 me10.empNm,
                 tps.viceDirectorCd,
                 me11.empNm,
                 tps.constrSiteSupvCd,
                 me12.empNm,
                 sub2.actStartDt,
                 sub1.actStartDt)
               FROM TProjectSite tps
          LEFT JOIN MCustomer mc         ON mc.customerBranchCd = tps.customerBranchCd
          LEFT JOIN MOrg     mo1         ON mo1.id                  = tps.constrOrgId
          LEFT JOIN MEmp     me1         ON me1.empCd               = tps.constMgrCd
          LEFT JOIN MEmp     me2         ON me2.empCd               = tps.ftEngineerCd
          LEFT JOIN MEmp     me3         ON me3.empCd               = tps.constrPicCd
          LEFT JOIN MEmp     me4         ON me4.empCd               = tps.constrSitePicCd1
          LEFT JOIN MEmp     me5         ON me5.empCd               = tps.constrSitePicCd2
          LEFT JOIN MEmp     me6         ON me6.empCd               = tps.constrSitePicCd3
          LEFT JOIN MEmp     me7         ON me7.empCd               = tps.constrSitePicCd4
          LEFT JOIN MEmp     me8         ON me8.empCd               = tps.constrSitePicCd5
          LEFT JOIN MEmp     me9         ON me9.empCd               = tps.constrSiteDirectorCd
          LEFT JOIN MEmp     me10        ON me10.empCd              = tps.deputyDirectorCd
          LEFT JOIN MEmp     me11        ON me11.empCd              = tps.viceDirectorCd
          LEFT JOIN MEmp     me12        ON me12.empCd              = tps.constrSiteSupvCd
          LEFT JOIN TProject  tp         ON tp.projectCd            = tps.projectCd
          LEFT JOIN MOffice  mof         ON tps.icOfficeCd.officeCd = mof.officeCd
          LEFT JOIN TConstrSite tcs      ON tcs.projectSite.projectSiteCd = tps.projectSiteCd
          LEFT JOIN MItemListSetting mi  ON mi.id.itemCd = tp.projectK
                                        AND mi.id.itemClassCd = 'D0002'
          LEFT JOIN MItemListSetting mi1 ON mi1.id.itemCd = tcs.structureK
                                        AND mi1.id.itemClassCd = 'D0022'
          LEFT JOIN (
                  SELECT
                        tps.projectSiteCd AS projectSiteCd,
                        CASE
                            WHEN SUM(CASE WHEN tcwd.actStartDt IS NULL THEN 1 ELSE 0 END) > 0
                            THEN NULL
                            ELSE MAX(tcwd.actStartDt)
                         END AS actStartDt
                    FROM TConstrWbsHdr tcwh
              INNER JOIN TConstrSite tcs
                      ON tcs.constrSiteCd = tcwh.constrSiteCd
              INNER JOIN TProjectSite tps
                      ON tps.projectSiteCd = tcs.projectSite.projectSiteCd
              INNER JOIN TConstrWbsDtl tcwd
                      ON tcwd.constrWbsHid.id = tcwh.id
                   WHERE tcwd.constrProcessCd.constrProcessCd = '000006'
                GROUP BY tps.projectSiteCd
          ) sub1 ON sub1.projectSiteCd = tps.projectSiteCd
          LEFT JOIN (
                  SELECT
                        tps.projectSiteCd AS projectSiteCd,
                        MIN(tcwd.actStartDt) actStartDt
                    FROM TConstrWbsHdr tcwh
              INNER JOIN TConstrSite tcs
                      ON tcs.constrSiteCd = tcwh.constrSiteCd
              INNER JOIN TProjectSite tps
                      ON tps.projectSiteCd = tcs.projectSite.projectSiteCd
              INNER JOIN TConstrWbsDtl tcwd
                      ON tcwd.constrWbsHid.id = tcwh.id
                   WHERE tcwd.constrProcessCd.constrProcessCd = '000001'
                GROUP BY tps.projectSiteCd
          ) sub2 ON sub2.projectSiteCd = tps.projectSiteCd
              WHERE :belongOfficeCd     IS NULL OR tps.icOfficeCd.officeCd = :belongOfficeCd
           ORDER BY tps.projectSiteCd
      """)
  List<ProjectSiteInfoDto> findInitInfo(@Param("belongOfficeCd") String belongOfficeCd);

  /**
   * 検索処理.
   *
   * @param projectSiteCd 物件コード
   * @param customerCd 顧客コード
   * @param orderYmdFrom 受注年月(開始)
   * @param orderYmdTo 受注年月(終了)
   * @param icOfficeNm 所属事業所名
   * @param constrStartYmdFrom 着手日(開始)
   * @param constrStartYmdTo 着手日(終了)
   * @param constrCompYmdFrom 完工日(開始)
   * @param constrCompYmdTo 完工日(終了)
   * @param constrOrgNm 工事部門
   * @param constrMgrNm 工事管理職
   * @param ftEngineerNm 専任技術者
   * @param constrPicNm 施工担当者
   * @return 物件情報
   */
  @Query(
      """
             SELECT new com.daitoj.tkms.modules.apif0010.service.dto.ProjectSiteInfoDto(
                 tps.projectSiteCd,
                 tps.projectSiteNm,
                 tps.projectSiteKnNm,
                 tps.icOfficeCd.officeCd,
                 mof.officeNm,
                 mc.customerCd,
                 CONCAT(
                  COALESCE(mc.customerNm1, ''),
                  COALESCE(mc.customerNm2, '')
                 ),
                 mc.customerKnNm,
                 tps.orderYmd,
                 tps.inclTaxCoTotalAmt,
                 mo1.orgCd,
                 mo1.orgNm,
                 me1.empCd,
                 me1.empNm,
                 me2.empCd,
                 me2.empNm,
                 me3.empCd,
                 me3.empNm,
                 mc.customerRyakusyou,
                 tps.customerBranchCd,
                 tp.projectK,
                 mi.itemValue,
                 tps.constrStartYmd,
                 tps.constrCompYmd,
                 tps.projectCd,
                 tp.projectNm,
                 tcs.structureK,
                 mi1.itemValue,
                  tp.floorCnt,
                  tp.households,
                 CONCAT(
                    COALESCE(tps.projectSiteAddr1, ''),
                    COALESCE(tps.projectSiteAddr2, '')
                 ),
                 tps.constrSitePicCd1,
                 me4.empNm,
                 tps.constrSitePicCd2,
                 me5.empNm,
                 tps.constrSitePicCd3,
                 me6.empNm,
                 tps.constrSitePicCd4,
                 me7.empNm,
                 tps.constrSitePicCd5,
                 me8.empNm,
                 me4.compPhoneNo,
                 me5.compPhoneNo,
                 tps.constrSiteDirectorCd,
                 me9.empNm,
                 tps.deputyDirectorCd,
                 me10.empNm,
                 tps.viceDirectorCd,
                 me11.empNm,
                 tps.constrSiteSupvCd,
                 me12.empNm,
                 sub2.actStartDt,
                 sub1.actStartDt)
               FROM TProjectSite tps
          LEFT JOIN MCustomer mc         ON mc.customerBranchCd     = tps.customerBranchCd
          LEFT JOIN MOrg     mo1         ON mo1.id                  = tps.constrOrgId
          LEFT JOIN MEmp     me1         ON me1.empCd               = tps.constMgrCd
          LEFT JOIN MEmp     me2         ON me2.empCd               = tps.ftEngineerCd
          LEFT JOIN MEmp     me3         ON me3.empCd               = tps.constrPicCd
          LEFT JOIN MEmp     me4         ON me4.empCd               = tps.constrSitePicCd1
          LEFT JOIN MEmp     me5         ON me5.empCd               = tps.constrSitePicCd2
          LEFT JOIN MEmp     me6         ON me6.empCd               = tps.constrSitePicCd3
          LEFT JOIN MEmp     me7         ON me7.empCd               = tps.constrSitePicCd4
          LEFT JOIN MEmp     me8         ON me8.empCd               = tps.constrSitePicCd5
          LEFT JOIN MEmp     me9         ON me9.empCd               = tps.constrSiteDirectorCd
          LEFT JOIN MEmp     me10        ON me10.empCd              = tps.deputyDirectorCd
          LEFT JOIN MEmp     me11        ON me11.empCd              = tps.viceDirectorCd
          LEFT JOIN MEmp     me12        ON me12.empCd              = tps.constrSiteSupvCd
          LEFT JOIN TProject  tp         ON tp.projectCd            = tps.projectCd
          LEFT JOIN MOffice  mof         ON tps.icOfficeCd.officeCd = mof.officeCd
          LEFT JOIN TConstrSite tcs      ON tcs.projectSite.projectSiteCd = tps.projectSiteCd
          LEFT JOIN MItemListSetting mi  ON mi.id.itemCd = tp.projectK
                                        AND mi.id.itemClassCd = 'D0002'
          LEFT JOIN MItemListSetting mi1 ON mi1.id.itemCd = tcs.structureK
                                        AND mi1.id.itemClassCd = 'D0022'
          LEFT JOIN (
                  SELECT
                        tps.projectSiteCd AS projectSiteCd,
                        CASE
                            WHEN SUM(CASE WHEN tcwd.actStartDt IS NULL THEN 1 ELSE 0 END) > 0
                            THEN NULL
                            ELSE MAX(tcwd.actStartDt)
                         END AS actStartDt
                    FROM TConstrWbsHdr tcwh
              INNER JOIN TConstrSite tcs
                      ON tcs.constrSiteCd = tcwh.constrSiteCd
              INNER JOIN TProjectSite tps
                      ON tps.projectSiteCd = tcs.projectSite.projectSiteCd
              INNER JOIN TConstrWbsDtl tcwd
                      ON tcwd.constrWbsHid.id = tcwh.id
                   WHERE tcwd.constrProcessCd.constrProcessCd = '000006'
                GROUP BY tps.projectSiteCd
          ) sub1 ON sub1.projectSiteCd = tps.projectSiteCd
          LEFT JOIN (
                  SELECT
                        tps.projectSiteCd AS projectSiteCd,
                        MIN(tcwd.actStartDt) actStartDt
                    FROM TConstrWbsHdr tcwh
              INNER JOIN TConstrSite tcs
                      ON tcs.constrSiteCd = tcwh.constrSiteCd
              INNER JOIN TProjectSite tps
                      ON tps.projectSiteCd = tcs.projectSite.projectSiteCd
              INNER JOIN TConstrWbsDtl tcwd
                      ON tcwd.constrWbsHid.id = tcwh.id
                   WHERE tcwd.constrProcessCd.constrProcessCd = '000001'
                GROUP BY tps.projectSiteCd
          ) sub2 ON sub2.projectSiteCd = tps.projectSiteCd
              WHERE (:projectSiteCd      IS NULL OR    tps.projectSiteCd   LIKE %:projectSiteCd%)
                AND (:customerCd         IS NULL OR        mc.customerCd   LIKE %:customerCd%)
                AND (:orderYmdFrom       IS NULL OR :orderYmdFrom = ''
                                                 OR tps.orderYmd >= :orderYmdFrom)
                AND (:orderYmdTo         IS NULL OR :orderYmdTo   = ''
                                                 OR tps.orderYmd <= :orderYmdTo)
                AND (:icOfficeNm         IS NULL OR         mof.officeNm   LIKE %:icOfficeNm%)
                AND (:constrStartYmdFrom IS NULL OR :constrStartYmdFrom = ''
                                                 OR tps.constrStartYmd >= :constrStartYmdFrom)
                AND (:constrStartYmdTo   IS NULL OR :constrStartYmdTo   = ''
                                                 OR tps.constrStartYmd <= :constrStartYmdTo)
                AND (:constrCompYmdFrom  IS NULL OR :constrCompYmdFrom  = ''
                                                 OR tps.constrCompYmd  <= :constrCompYmdFrom)
                AND (:constrCompYmdTo    IS NULL OR :constrCompYmdTo    = ''
                                                 OR tps.constrCompYmd  <= :constrCompYmdTo)
                AND (:constrOrgNm        IS NULL OR mo1.orgNm              LIKE %:constrOrgNm%)
                AND (:constrMgrNm        IS NULL OR me1.empNm              LIKE %:constrMgrNm%)
                AND (:ftEngineerNm       IS NULL OR me2.empNm              LIKE %:ftEngineerNm%)
                AND (:constrPicNm        IS NULL OR me3.empNm              LIKE %:constrPicNm%)
                AND (:belongOfficeCd     IS NULL OR tps.icOfficeCd.officeCd = :belongOfficeCd)
           ORDER BY tps.projectSiteCd
      """)
  List<ProjectSiteInfoDto> findProjectSiteInfo(
      @Param("projectSiteCd") String projectSiteCd,
      @Param("customerCd") String customerCd,
      @Param("orderYmdFrom") String orderYmdFrom,
      @Param("orderYmdTo") String orderYmdTo,
      @Param("icOfficeNm") String icOfficeNm,
      @Param("constrStartYmdFrom") String constrStartYmdFrom,
      @Param("constrStartYmdTo") String constrStartYmdTo,
      @Param("constrCompYmdFrom") String constrCompYmdFrom,
      @Param("constrCompYmdTo") String constrCompYmdTo,
      @Param("constrOrgNm") String constrOrgNm,
      @Param("constrMgrNm") String constrMgrNm,
      @Param("ftEngineerNm") String ftEngineerNm,
      @Param("constrPicNm") String constrPicNm,
      @Param("belongOfficeCd") String belongOfficeCd);

}
