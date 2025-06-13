package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MOrgRev;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 組織改定情報リポジトリ */
@Repository
public interface MOrgRevRepository extends JpaRepository<MOrgRev, Long> {
  /**
   * 適用開始日リストを取得
   *
   * @param effectiveStartDt 適用開始日
   * @return 適用開始日リスト
   */
  List<MOrgRev> findByEffectiveStartDtGreaterThanOrderByEffectiveStartDt(String effectiveStartDt);

  /**
   * 適用開始日リストを取得
   *
   * @return 適用開始日リスト
   */
  List<MOrgRev> findAllByOrderByEffectiveStartDtDesc();
}
