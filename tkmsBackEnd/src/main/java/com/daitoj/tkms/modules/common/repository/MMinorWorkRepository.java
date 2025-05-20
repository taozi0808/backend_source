package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MMinorWork;
import com.daitoj.tkms.domain.MMinorWorkId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 小工事情報リポジトリ */
@Repository
public interface MMinorWorkRepository extends JpaRepository<MMinorWork, MMinorWorkId> {

  /**
   * 小工事情報を取得
   *
   * @param majorWorkCd 大工事
   * @return 小工事情報
   */
  List<MMinorWork> findById_MajorWorkCdOrderById_MinorWorkCd(String majorWorkCd);
}
