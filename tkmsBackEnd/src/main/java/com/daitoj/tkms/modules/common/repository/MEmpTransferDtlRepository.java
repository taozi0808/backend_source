package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MEmpTransferDtl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 従業員異動明細情報リポジトリ */
@Repository
public interface MEmpTransferDtlRepository extends JpaRepository<MEmpTransferDtl, Long> {

  /**
   * 従業員異動明細を取得
   *
   * @param hid 従業員異動HID
   * @return 従業員異動明細
   */
  List<MEmpTransferDtl> findByEmpTransferHid_Id(Long hid);
}
