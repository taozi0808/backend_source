package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 役職情報リポジトリ */
@Repository
public interface MPositionRepository extends JpaRepository<MPosition, String> {

  /**
   * 役職情報を取得
   *
   * @param positionCd 役職コード
   * @return 役職情報
   */
  MPosition findByPositionCd(String positionCd);
}
