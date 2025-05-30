package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TEvSim;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 出来高シミュレーション情報リポジトリ */
@Repository
public interface TEvSimRepository extends JpaRepository<TEvSim, Long> {

  /**
   * 概算コードに紐づく出来高シミュレーションデータを取得
   *
   * @param roughEstHid 概算コード
   * @return 出来高シミュレーション
   */
  List<TEvSim> findByRoughEstHid(Long roughEstHid);
}
