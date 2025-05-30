package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TProjectPreWorkDtl;
import com.daitoj.tkms.modules.apib0030.service.dto.DetailedEstDtlDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectPreWorkDtlDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 先行作業明細のリポジトリ */
@Repository
public interface B0030S06Repository extends JpaRepository<TProjectPreWorkDtl, Long> {

  /**
   * 先行作業明細を取得
   *
   * @param projectId 案件ID
   * @return 先行作業明細
   */
  @Query(
      """
          SELECT
              new com.daitoj.tkms.modules.apib0030.service.dto.ProjectPreWorkDtlDto(
                        tp.project.id,
                        tp.seqNo,
                        tp.majorWorkCd,
                        ma.majorWorkNm,
                        tp.minorWorkCd,
                        mi.minorWorkNm,
                        tp.detailedEstDid.id,
                        tp.estYmd,
                        tp.outline,
                        tp.preConstrContent,
                        tp.constrCost,
                        tp.paymentTerms,
                        tp.sonota,
                        tp.fileId,
                        file.fileNm,
                        tp.preWorkProcessK,
                        tp.poHid.id,
                        tp.workSeqNo)
           FROM TProjectPreWorkDtl tp
      LEFT JOIN MMajorWork ma ON ma.majorWorkCd = tp.majorWorkCd
      LEFT JOIN MMinorWork mi ON mi.id.majorWorkCd = tp.majorWorkCd
                             AND mi.id.minorWorkCd = tp.minorWorkCd
      LEFT JOIN TFifle file   ON tp.fileId = file.id
          WHERE tp.project.id = :projectId
      """)
  List<ProjectPreWorkDtlDto> findByProjectId(Long projectId);

  /**
   * 大工事情報を取得
   *
   * @param projectCd 案件コード
   * @return 精積算情報
   */
  @Query(
      """
                SELECT
                    new com.daitoj.tkms.modules.apib0030.service.dto.DetailedEstDtlDto(
                              null,
                              td.majorWorkCd,
                              ma.majorWorkNm,
                              null,
                              null,
                              null,
                              null)
                  FROM TDetailedEstHdr th
            INNER JOIN TDetailedEstDtl td ON td.detailedEstHid = th
            LEFT  JOIN MMajorWork ma      ON ma.majorWorkCd = td.majorWorkCd
            LEFT  JOIN MMinorWork mi      ON mi.id.majorWorkCd = td.majorWorkCd
                                         AND mi.id.minorWorkCd = td.minorWorkCd
                 WHERE th.projectCd = :projectCd
            """)
  List<DetailedEstDtlDto> findMajorWorkByProjectCd(String projectCd);

  /**
   * 小工事情報を取得
   *
   * @param minorWorkCd 大工事コード
   * @return 精積算情報
   */
  @Query(
      """
              SELECT
                  new com.daitoj.tkms.modules.apib0030.service.dto.DetailedEstDtlDto(
                            td.id,
                            null,
                            null,
                            td.minorWorkCd,
                            mi.minorWorkNm,
                            td.workSeqNo,
                            td.spec)
                FROM TDetailedEstHdr th
          INNER JOIN TDetailedEstDtl td ON td.detailedEstHid = th
          LEFT  JOIN MMinorWork mi      ON mi.id.majorWorkCd = td.majorWorkCd
                                       AND mi.id.minorWorkCd = td.minorWorkCd
               WHERE th.projectCd = :projectCd
                 AND td.majorWorkCd = :minorWorkCd
          """)
  List<DetailedEstDtlDto> findMinorWorkByProjectCd(String projectCd, String minorWorkCd);

}
