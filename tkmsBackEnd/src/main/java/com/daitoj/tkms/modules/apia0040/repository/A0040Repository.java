package com.daitoj.tkms.modules.apia0040.repository;

import com.daitoj.tkms.domain.MNotice;
import com.daitoj.tkms.domain.MNoticeId;
import com.daitoj.tkms.modules.apia0040.service.dto.NoticeInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** ダッシュボードのリポジトリ. */
@Repository
public interface A0040Repository extends JpaRepository<MNotice, MNoticeId> {

  /**
   * 協力会社からのお知らせを取得する.
   *
   * @return 協力会社からのお知らせ
   */
  @Query(
      """
          SELECT new com.daitoj.tkms.modules.apia0040.service.dto.NoticeInfoDto(
              "80",
              mbn.noticeContentHtml)
            FROM MBpNotice mbn
           WHERE TO_DATE(mbn.effectiveStartDt, 'YYYYMMDD') <= CURRENT_DATE
             AND TO_DATE(COALESCE(NULLIF(mbn.effectiveEndDt, '')
                     , '99991231'), 'YYYYMMDD') >= CURRENT_DATE
        ORDER BY TO_DATE(mbn.effectiveStartDt, 'YYYYMMDD') DESC
           LIMIT 10
        """)
  List<NoticeInfoDto> findCooperatingNoticeInfo();

  /**
   * 「通知・通達・社報」を取得.
   *
   * @return 「通知・通達・社報」情報
   */
  @Query(
      """
          SELECT new com.daitoj.tkms.modules.apia0040.service.dto.NoticeInfoDto(
                tdn.id,
                 "1",
                 tdn.noticeTitle,
                 TO_CHAR(tbna.requestTs, 'YYYY/MM/DD'))
            FROM
                 TDsbNoticeGroupUser tdngu
      INNER JOIN TDsbNoticeGroup tdng
              ON tdng.id = tdngu.id.dsbNoticeGroupId
      INNER JOIN TNoticeSelGroup tnsg
              ON tnsg.dsbNoticeGroup.id = tdngu.id.dsbNoticeGroupId
      INNER JOIN TDsbNotice tdn
              ON tdn.id = tnsg.dsbNotice.id
             AND tdn.noticeK = '1'
      INNER JOIN TBusinessNewestAppr tbna
              ON tbna.newestWfRequestId = tnsg.dsbNotice.id
             AND tbna.businessDataSt = '3'
           WHERE tdngu.id.empCd = :empCd

      UNION

          SELECT new com.daitoj.tkms.modules.apia0040.service.dto.NoticeInfoDto(
                tdn.id,
                 "2",
                 tdn.noticeTitle,
                 TO_CHAR(tbna.requestTs, 'YYYY/MM/DD'))
            FROM TDsbNotice tdn
      INNER JOIN TBusinessNewestAppr tbna
              ON tbna.newestWfRequestId = tdn.id
             AND tbna.businessDataSt = '3'
           WHERE tdn.noticeK = '2'

      UNION

          SELECT new com.daitoj.tkms.modules.apia0040.service.dto.NoticeInfoDto(
                tdcn.id,
                 "3",
                 tdcn.compNewsTitle,
                 TO_CHAR(tbna.requestTs, 'YYYY/MM/DD'))
            FROM TDsbCompNews tdcn
      INNER JOIN TBusinessNewestAppr tbna
              ON tbna.newestWfRequestId = tdcn.id
             AND tbna.businessDataSt = '3'
      """)
  List<NoticeInfoDto> findNoticeInfo(@Param("empCd") String empCd);

  /**
   * 申請・承認待ちを取得.
   *
   * @return 申請・承認待ち
   */
  @Query(
      """
          SELECT new com.daitoj.tkms.modules.apia0040.service.dto.NoticeInfoDto(
                tdga.id,
                "4",
                tdga.genApplTitle,
                tbna.businessDataSt,
                TO_CHAR(tbna.requestTs, 'YYYY/MM/DD'))
            FROM TBusinessNewestAppr  tbna
      INNER JOIN TDsbGenAppl tdga
              ON tdga.id = tbna.newestWfRequestId
           WHERE tbna.businessDataSt != '3'
             AND tbna.appAccountK = '1'
             AND tbna.requestAppCd = :empCd

      UNION

          SELECT new com.daitoj.tkms.modules.apia0040.service.dto.NoticeInfoDto(
                tdga.id,
                "5",
                tdga.genApplTitle,
                twr.businessDataSt,
                TO_CHAR(twr.requestTs, 'YYYY/MM/DD'))
              FROM TWfApprStep twas
        INNER JOIN TWfRequest twr
                ON twr.id = twas.wfRequest.id
               AND twr.currentStep = twas.apprStepOrder
               AND twr.businessDataSt = '2'
               AND twr.currentStep = 1
        INNER JOIN TDsbGenAppl tdga
                ON tdga.id = twr.id
             WHERE twas.apprEmpCd = :empCd
               AND twas.apprSt = '1'

      UNION

          SELECT new com.daitoj.tkms.modules.apia0040.service.dto.NoticeInfoDto(
                tdga.id,
                "5",
                tdga.genApplTitle,
                twr.businessDataSt,
                TO_CHAR(prevStep.decisionTs, 'YYYY/MM/DD'))
              FROM TWfApprStep twas
        INNER JOIN TWfRequest twr
                ON twr.id = twas.wfRequest.id
               AND twr.currentStep = twas.apprStepOrder
               AND twr.businessDataSt = '2'
               AND twr.currentStep != 1
        INNER JOIN TWfApprStep prevStep
                ON prevStep.wfRequest.id = twas.wfRequest.id
               AND prevStep.apprStepOrder = twas.apprStepOrder - 1
        INNER JOIN TDsbGenAppl tdga
                ON tdga.id = twr.id
             WHERE twas.apprEmpCd = :empCd
               AND twas.apprSt = '1'
      """)
  List<NoticeInfoDto> findApplyInfo(@Param("empCd") String empCd);

}
