package com.daitoj.tkms.modules.apin0030.repository;

import com.daitoj.tkms.domain.TConstrWbsHdr;
import com.daitoj.tkms.modules.apin0030.service.dto.ConstrWbsDtlDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 工事予実明細のリポジトリ */
@Repository
public interface N0030S02Repository extends JpaRepository<TConstrWbsHdr, Long> {
  /**
   * 工事予実明細取得
   *
   * @param tconstrWbsHdr　工事予実ヘッダ
   * @return 工事予実明細リスト
   */
//  @Query(
//    """
//      SELECT new com.daitoj.tkms.modules.apin0030.service.dto.ConstrWbsDtlDto(
//          cdl.id,
//          cdl.constrProcessCd,
//          cdl.constrProcessShowNm,
//          cdl.seqNo,
//          cdl.showFlg,
//          cdl.partnerVendorNm,
//          cdl.floor,
//          cdl.planStartDt,
//          cdl.actStartDt,
//          cdl.startDtEditK,
//          cdl.planEndDt,
//          cdl.actEndDt,
//          cdl.endDtK
//              )
//      FROM
//          TConstrWbsDtl cdl
//      WHERE
//          cdl.constrWbsHid = :tconstrWbsHdr
//    """)
//  List<ConstrWbsDtlDto> findKoujiYojitsuDtl(TConstrWbsHdr tconstrWbsHdr);
}
