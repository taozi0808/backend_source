package com.daitoj.tkms.modules.apir0040.repository;

import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.modules.apir0040.service.dto.EmpInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 社員一覧のリポジトリ. */
@Repository
public interface R0040Repository extends JpaRepository<MEmp, Long> {

  /**
   * 初期表示データ取得.
   *
   * @return 社員一覧
   */
  @Query(
      """
                      SELECT new com.daitoj.tkms.modules.apir0040.service.dto.EmpInfoDto(
                          me.id,
                          me.empCd,
                          me.empNm,
                        mis1.itemValue,
                          me.empJobTypeCd,
                        mis2.itemValue,
                         mo1.orgCd,
                         mo1.orgNm,
                    meo1_min.minSeqNo,
                         mo2.orgCd,
                         mo2.orgNm,
                    meo2_min.minSeqNo,
                          me.positionCd.positionCd,
                          mp.positionNm)
                        FROM MEmp      me
                   LEFT JOIN MItemListSetting mis1  ON me.sex = mis1.id.itemCd
                                                   AND mis1.id.itemClassCd = 'D0013'
                   LEFT JOIN MItemListSetting mis2  ON me.empJobTypeCd = mis2.id.itemCd
                                                   AND mis2.id.itemClassCd = 'D0014'
                   LEFT JOIN (
                       SELECT meo.emp.id as empId, MIN(meo.seqNo) as minSeqNo
                         FROM MEmpOrg meo
                        WHERE meo.orgK = '1'
                     GROUP BY meo.emp.id
                   ) meo1_min ON meo1_min.empId = me.id
                   LEFT JOIN MEmpOrg          meo1  ON meo1.emp.id = me.id AND meo1.orgK = '1'
                                                   AND meo1.seqNo = meo1_min.minSeqNo
                   LEFT JOIN MOrg             mo1   ON mo1.id = meo1.org.id
                   LEFT JOIN (
                       SELECT meo.emp.id as empId, MIN(meo.seqNo) as minSeqNo
                        FROM MEmpOrg meo
                       WHERE meo.orgK = '2'
                    GROUP BY meo.emp.id
                   ) meo2_min ON meo2_min.empId = me.id
                   LEFT JOIN MEmpOrg          meo2  ON meo2.emp.id = me.id
                                                   AND meo2.orgK = '2'
                                                   AND meo2.seqNo = meo2_min.minSeqNo
                   LEFT JOIN MOrg             mo2   ON mo2.id = meo2.org.id
                   LEFT JOIN MPosition         mp   ON mp.positionCd = me.positionCd.positionCd
                       WHERE me.terminationYmd IS NULL OR me.terminationYmd = ''
                          OR to_date(me.terminationYmd, 'YYYYMMDD') >= current_date
                    ORDER BY me.empCd
      """)
  List<EmpInfoDto> findInitInfo();

  /**
   * 検索処理.
   *
   * @param empCd 社員コード
   * @param sex 性別
   * @param jobTypeNm 職種名
   * @param belongOrgNm 所属部
   * @param belongSectionNm 所属課
   * @param positionCd 役職コード
   * @param showResign 退職者を検索条件とする
   * @return 社員情報
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apir0040.service.dto.EmpInfoDto(
                  me.id,
                  me.empCd,
                  me.empNm,
                mis1.itemValue,
                  me.empJobTypeCd,
                mis2.itemValue,
                 mo1.orgCd,
                 mo1.orgNm,
            meo1_min.minSeqNo,
                 mo2.orgCd,
                 mo2.orgNm,
            meo2_min.minSeqNo,
                  me.positionCd.positionCd,
                  mp.positionNm)
                FROM MEmp      me
           LEFT JOIN MItemListSetting mis1  ON me.sex = mis1.id.itemCd
                                           AND mis1.id.itemClassCd = 'D0013'
           LEFT JOIN MItemListSetting mis2  ON me.empJobTypeCd = mis2.id.itemCd
                                           AND mis2.id.itemClassCd = 'D0014'
           LEFT JOIN (
               SELECT meo.emp.id as empId, MIN(meo.seqNo) as minSeqNo
                 FROM MEmpOrg meo
                WHERE meo.orgK = '1'
             GROUP BY meo.emp.id
           ) meo1_min ON meo1_min.empId = me.id
           LEFT JOIN MEmpOrg          meo1  ON meo1.emp.id = me.id AND meo1.orgK = '1'
                                           AND meo1.seqNo = meo1_min.minSeqNo
           LEFT JOIN MOrg             mo1   ON mo1.id = meo1.org.id
           LEFT JOIN (
               SELECT meo.emp.id as empId, MIN(meo.seqNo) as minSeqNo
                FROM MEmpOrg meo
               WHERE meo.orgK = '2'
            GROUP BY meo.emp.id
           ) meo2_min ON meo2_min.empId = me.id
           LEFT JOIN MEmpOrg          meo2  ON meo2.emp.id = me.id
                                           AND meo2.orgK = '2'
                                           AND meo2.seqNo = meo2_min.minSeqNo
           LEFT JOIN MOrg             mo2   ON mo2.id = meo2.org.id
           LEFT JOIN MPosition         mp   ON mp.positionCd = me.positionCd.positionCd
               WHERE (:showResign = '1'     OR (me.terminationYmd IS NULL OR me.terminationYmd = ''
                                     OR to_date(me.terminationYmd, 'YYYYMMDD') >= current_date))
                 AND (:empCd           IS NULL OR :empCd = ''     OR me.empCd LIKE %:empCd%)
                 AND (:sex             IS NULL OR :sex = ''       OR mis1.id.itemCd = :sex)
                 AND (:jobTypeNm       IS NULL OR :jobTypeNm = ''
                                               OR mis2.itemValue LIKE %:jobTypeNm%)
                 AND (:belongOrgNm     IS NULL OR :belongOrgNm = ''
                                               OR mo1.orgNm LIKE %:belongOrgNm%)
                 AND (:belongSectionNm IS NULL OR :belongSectionNm = ''
                                               OR mo2.orgNm LIKE %:belongSectionNm%)
                 AND (:positionCd      IS NULL OR :positionCd = ''
                                               OR me.positionCd.positionCd = :positionCd)
            ORDER BY me.empCd
      """)
  List<EmpInfoDto> findEmpInfo(
      @Param("empCd") String empCd,
      @Param("sex") String sex,
      @Param("jobTypeNm") String jobTypeNm,
      @Param("belongOrgNm") String belongOrgNm,
      @Param("belongSectionNm") String belongSectionNm,
      @Param("positionCd") String positionCd,
      @Param("showResign") String showResign);

}
