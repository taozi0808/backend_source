package com.daitoj.tkms.modules.apio0010.repository;

import com.daitoj.tkms.domain.MVendor;
import com.daitoj.tkms.modules.apio0010.service.dto.VendorInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** 業者一覧のリポジトリ. */
@Repository
public interface O0010Repository extends JpaRepository<MVendor, Long> {

  /**
   * 初期表示データを取得.
   *
   * @return 業者情報
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apio0010.service.dto.VendorInfoDto(
                    mv.id,
                    mv.vendorCd,
                    mv.branchCd,
                    mv.compNm,
                    mv.compKnNm,
                    mv.branchNm,
                    mv.vendorAddr1,
                    mv.vendorAddr2,
                    mv.ceoNm,
                    mv.vendorTelNo,
                    ml1.itemValue,
                    ml2.itemValue,
                    ml3.itemValue,
                    ml4.itemValue,
                    ml5.itemValue,
                    mv.demolPermFlg,
                    mv.securityCertFlg,
                    CASE
                          WHEN mvip.id IS NULL THEN '0'
                          ELSE '1'
                    END,
                    mvip.iwPermDt)
              FROM MVendor mv
        LEFT  JOIN MVendorIndustry  mvi    ON mv.id = mvi.vendor.id
        LEFT  JOIN MItemListSetting ml1    ON mvi.industryCd1 = ml1.id.itemCd
                                          AND ml1.id.itemClassCd = 'D0019'
        LEFT  JOIN MItemListSetting ml2    ON mvi.industryCd2 = ml2.id.itemCd
                                          AND ml2.id.itemClassCd = 'D0019'
        LEFT  JOIN MItemListSetting ml3    ON mvi.industryCd3 = ml3.id.itemCd
                                          AND ml3.id.itemClassCd = 'D0019'
        LEFT  JOIN MItemListSetting ml4    ON mvi.industryCd4 = ml4.id.itemCd
                                          AND ml4.id.itemClassCd = 'D0019'
        LEFT  JOIN MItemListSetting ml5    ON mvi.industryCd5 = ml5.id.itemCd
                                          AND ml5.id.itemClassCd = 'D0019'
        LEFT  JOIN MVendorIwPerm mvip      ON mv.id = mvip.vendor.id
          ORDER BY mv.vendorCd
      """)
  List<VendorInfoDto> findInitInfo();

  /**
   * 業者データを取得.
   *
   * @param vendorCd 業者コード
   * @param branchCd 支店コード
   * @param jobType 業種
   * @param demolPerm 解体登録
   * @param securityCert 警備認定
   * @param iwPerm 産廃許可
   * @return 業者情報
   */
  @Query(
      """
              SELECT new com.daitoj.tkms.modules.apio0010.service.dto.VendorInfoDto(
                  mv.id,
                  mv.vendorCd,
                  mv.branchCd,
                  mv.compNm,
                  mv.compKnNm,
                  mv.branchNm,
                  mv.vendorAddr1,
                  mv.vendorAddr2,
                  mv.ceoNm,
                  mv.vendorTelNo,
                  ml1.itemValue,
                  ml2.itemValue,
                  ml3.itemValue,
                  ml4.itemValue,
                  ml5.itemValue,
                  mv.demolPermFlg,
                  mv.securityCertFlg,
                  CASE
                    WHEN mvip.id IS NULL THEN '0'
                    ELSE '1'
                  END,
                  mvip.iwPermDt)
                FROM MVendor mv
          LEFT  JOIN MVendorIndustry   mvi     ON mv.id = mvi.vendor.id
          LEFT  JOIN MItemListSetting  ml1     ON mvi.industryCd1 = ml1.id.itemCd
                                              AND ml1.id.itemClassCd = 'D0019'
          LEFT  JOIN MItemListSetting  ml2     ON mvi.industryCd2 = ml2.id.itemCd
                                              AND ml2.id.itemClassCd = 'D0019'
          LEFT  JOIN MItemListSetting  ml3     ON mvi.industryCd3 = ml3.id.itemCd
                                              AND ml3.id.itemClassCd = 'D0019'
          LEFT  JOIN MItemListSetting  ml4     ON mvi.industryCd4 = ml4.id.itemCd
                                              AND ml4.id.itemClassCd = 'D0019'
          LEFT  JOIN MItemListSetting  ml5     ON mvi.industryCd5 = ml5.id.itemCd
                                              AND ml5.id.itemClassCd = 'D0019'
          LEFT  JOIN MVendorIwPerm mvip        ON mvip.vendor.id  = mv.id
               WHERE (:vendorCd IS NULL    OR mv.vendorCd LIKE %:vendorCd%)
                 AND (:branchCd IS NULL    OR mv.branchCd LIKE %:branchCd%)
                 AND (:jobType  IS NULL    OR :jobType = ''
                                           OR mvi.industryCd1 = :jobType
                                           OR mvi.industryCd2 = :jobType
                                           OR mvi.industryCd3 = :jobType
                                           OR mvi.industryCd4 = :jobType
                                           OR mvi.industryCd5 = :jobType)
                 AND (:demolPerm != '1'    OR mv.demolPermFlg = :demolPerm)
                 AND (:securityCert != '1' OR mv.securityCertFlg = :securityCert)
                 AND (:iwPerm != '1' OR mvip.id IS NOT NULL)
          ORDER BY mv.vendorCd
      """)
  List<VendorInfoDto> findVendorInfo(
      @Param("vendorCd") String vendorCd,
      @Param("branchCd") String branchCd,
      @Param("jobType") String jobType,
      @Param("demolPerm") String demolPerm,
      @Param("securityCert") String securityCert,
      @Param("iwPerm") String iwPerm);

}
