package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MEmpTransferHdr;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 従業員異動ヘッダ情報リポジトリ */
@Repository
public interface MEmpTransferHdrRepository extends JpaRepository<MEmpTransferHdr, Long> {

  /**
   * 従業員の異動情報を取得
   *
   * @param empId 従業員ID
   * @return 従業員異動ヘッダ情報
   */
  List<MEmpTransferHdr> findByEmp_IdOrderByEffectiveStartDt(Long empId);

  /**
   * 従業員の異動情報を取得
   *
   * @param empId 従業員ID
   * @param effectiveStartDt 適用開始日付
   * @return 従業員異動ヘッダ情報
   */
  @EntityGraph(attributePaths = {"dtlList", "dtlList.org"})
  Optional<MEmpTransferHdr> findByEmp_IdAndEffectiveStartDt(Long empId, String effectiveStartDt);
}
