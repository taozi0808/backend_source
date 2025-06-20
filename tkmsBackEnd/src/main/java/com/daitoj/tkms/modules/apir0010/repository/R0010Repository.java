package com.daitoj.tkms.modules.apir0010.repository;

import com.daitoj.tkms.modules.apir0010.service.dto.MPositionDto;
import com.daitoj.tkms.modules.apir0010.service.dto.MorGInfoDto;
import com.daitoj.tkms.modules.apir0010.service.dto.R0010S01Dto;
import com.daitoj.tkms.modules.apir0010.service.dto.R0010S04Dto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/** 権限情報のリポジトリ */
@Repository
public interface R0010Repository extends JpaRepository<MPositionDto, String> {

  /**
   * 初期表示データ取得
   *
   * @return 部署選択肢リスト
   */
  @Query(
      """
         SELECT new com.daitoj.tkms.modules.apir0010.service.dto.MorGInfoDto(
                mo.id,
                mo.orgCd,
                mo.orgNm
              )
           FROM MOrgRev mor
     INNER JOIN MOrg mo
             ON mo.orgRevId = mor.id
          WHERE COALESCE(:effectiveStartDt, mor.effectiveStartDt) = mor.effectiveStartDt
       ORDER BY mo.displayOrder
      """)
  List<MorGInfoDto> getMorgRevInfoDto(String effectiveStartDt);

  /**
   * 初期表示データ取得
   *
   * @return 概算一覧
   */
  @Query(
      """
           SELECT new com.daitoj.tkms.modules.apir0010.service.dto.R0010S01Dto(
                  mo.id,
                  mo.orgCd,
                  mo.orgNm,
                  momi.menuItem.id,
                  momi.referPerm
                )
             FROM MOrg mo
       INNER JOIN MOrgRev mor
               ON mo.orgRevId = mor.id
       LEFT  JOIN MOrgMenuItem momi
               ON momi.mOrg = mo
            WHERE COALESCE(:effectiveStartDt, mor.effectiveStartDt) = mor.effectiveStartDt
         ORDER BY mo.displayOrder
        """)
  List<R0010S01Dto> getMOrgMenuItemInfoDto(String effectiveStartDt);

    /**
     * 役職情報取得
     *
     * @return 役職情報
     */
  List<MPositionDto> findByDelFlgOrderByPositionCd(String delFlg);

  /**
   * 役職情報取得
   *
   * @return 役職情報
   */
  @Query(
      """
             SELECT new com.daitoj.tkms.modules.apir0010.service.dto.R0010S04Dto(
                    mp.positionCd,
                    mp.positionNm,
                    CASE
                        WHEN me.count != 0
                            THEN '1'
                        ELSE '0'
                        END,
                   mp.regTs,
                   mp.regUserId
                            )
               FROM MPositionDto mp
         LEFT  JOIN (
                        SELECT
                            mep.positionCd.positionCd as positionCd
                            , count(mep.empCd) as count
                        From
                            MEmp mep
                            GROUP BY mep.positionCd.positionCd
                    ) me
                 ON me.positionCd = mp.positionCd
          """)
  List<R0010S04Dto> findGetFlg();
}
