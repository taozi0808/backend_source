package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectResultDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 案件登録のリポジトリ */
@Repository
public interface B0030S01Repository extends JpaRepository<TProject, Long> {
  /**
   * 案件登録データ取得
   *
   * @param projectCd 案件コード
   * @return 案件情報
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apib0030.service.dto.ProjectResultDto(
                        ah.id,
                        ah.projectCd,
                        ah.hisNo,
                        ah.projectNm,
                        ah.projectKnNm,
                        ah.orderStCd,
                        null,
                        ah.customerBranchCd,
                        kh.customerCd,
                        CONCAT(kh.customerNm1, kh.customerNm2),
                        ah.expectAmt,
                        kh.customerPostNo,
                        kh.customerAddr1,
                        kh.customerAddr2,
                        ah.postNo,
                        ah.constrSiteAddr1,
                        ah.constrSiteAddr2,
                        ah.orderExpectedYmd,
                        ah.startHopeYmd,
                        si.orgCd,
                        si.orgNm,
                        ah.salesMgrCd,
                        jim.empNm,
                        ah.salesPicCd,
                        ji.empNm,
                        ah.progressCd,
                        null,
                        ah.projectK,
                        null,
                        ah.govPeoK,
                        null,
                        ah.estSubmitDueDt,
                        ah.siteArea,
                        ah.buildingArea,
                        ah.grossFloorArea,
                        ah.buildupArea,
                        ah.occupiedArea,
                        ah.constrArea,
                        ah.households,
                        ah.floorCnt,
                        ah.basementCnt,
                        ah.closingDay,
                        ah.paymentK,
                        null,
                        ah.paymentD,
                        ah.rejectionReason,
                        ah.salesDeptStartDt,
                        ah.compHopeYmd,
                        ah.designVender,
                        ah.designPicNm,
                        ah.greenSiteFlg,
                        ah.salesOrgId,
                        ah.delFlg,
                        ah.regTs,
                        ah.regUserId,
                        ah.regPgId,
                        ah.updTs,
                        ah.updUserId,
                        ah.updPgId)
                FROM TProject ah
          INNER JOIN MCustomer kh          ON ah.customerBranchCd = kh.customerBranchCd
          INNER JOIN MOrg si               ON ah.salesOrgId = si.id
           LEFT JOIN MEmp jim              ON ah.salesMgrCd = jim.empCd
           LEFT JOIN MEmp ji               ON ah.salesPicCd = ji.empCd
               WHERE ah.projectCd        = :projectCd
          """)
  Optional<ProjectResultDto> findAnkenInfo(String projectCd);

  /**
   * 案件コードより、案件情報を取得する
   *
   * @param projectCd 案件コード
   * @return 案件情報
   */
  Optional<TProject> findByProjectCd(String projectCd);
}
