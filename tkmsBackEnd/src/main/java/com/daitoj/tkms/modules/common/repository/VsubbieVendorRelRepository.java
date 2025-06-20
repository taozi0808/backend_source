package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.VSubbieVendorRel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 下請業者関係ビュー. */
@Repository
public interface VsubbieVendorRelRepository extends JpaRepository<VSubbieVendorRel, Long> {

  /**
   * 下請業者関係.
   *
   * @param parentPartnerVendorCd 上位業者コード
   * @return 下請業者関係リスト
   */
  List<VSubbieVendorRel> findAllByParentPartnerVendorCdOrderByPartnerVendorCd(String parentPartnerVendorCd);
}
