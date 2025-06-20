package com.daitoj.tkms.modules.apig0030.repository;

import com.daitoj.tkms.domain.TExecBgtDtlPo;
import com.daitoj.tkms.modules.apig0030.service.dto.ExecBgtDtlPoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 実行予算明細発注情報のリポジトリ. */
@Repository
public interface G0030S02Repository extends JpaRepository<TExecBgtDtlPo, Long> {
  /**
   * 実行予算明細IDより実行予算明細発注リストを取得する.
   *
   * @param execBgtHid 実行予算ヘッダ明細Id
   * @return 実行予算明細発注リスト
   */
  @Query(
      """
            SELECT new com.daitoj.tkms.modules.apig0030.service.dto.ExecBgtDtlPoDto(
              ebdp.id,
              ebdp.execBgtDid.id,
              ebdp.poNo,
              ebdp.poAmt,
              ebdp.partnerVendorCd,
              e.empNm,
              SUM(ad.mtdAssessAmt),
              ebdp.updTs
            )
            FROM
              TExecBgtDtlPo ebdp
              INNER JOIN TExecBgtDtl ebd ON ebd.id = ebdp.execBgtDid.id
              LEFT JOIN MEmp e ON e.empCd = ebdp.partnerVendorCd
              LEFT JOIN TAssessDtl ad ON ad.poNo = ebdp.poNo and ad.majorWorkCd = ebd.majorWorkCd AND ad.minorWorkCd = ebd.minorWorkCd
            WHERE
              ebd.execBgtHid.id = :execBgtHid
            GROUP BY ebdp.id, ebdp.execBgtDid.id, ebdp.execBgtDid.id, ebdp.partnerVendorCd, e.empNm, ebdp.updTs
        """)
  List<ExecBgtDtlPoDto> findAllByExecBgtHid(Long execBgtHid);
}
