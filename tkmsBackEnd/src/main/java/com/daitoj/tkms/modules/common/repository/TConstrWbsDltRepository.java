package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TConstrWbsDtl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 工事予実リポジトリ */
@Repository
public interface TConstrWbsDltRepository extends JpaRepository<TConstrWbsDtl, Long> {

  /**
   * 工事予実ヘッダIDより工事予実明細データを検出する
   *
   * @param constrWbsHid 工事予実ヘッダID
   * @return 工事予実明細リスト
   */
  List<TConstrWbsDtl> findAllByConstrWbsHidIdOrderBySeqNo(Long constrWbsHid);
}
