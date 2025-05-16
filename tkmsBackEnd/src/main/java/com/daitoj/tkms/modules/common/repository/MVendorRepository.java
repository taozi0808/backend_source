package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 業者情報リポジトリ */
@Repository
public interface MVendorRepository extends JpaRepository<MVendor, Long> {
  /**
   * 業者情報を取得する
   *
   * @param loginId ユーザーID
   * @return 業者情報
   */
  MVendor findByLogin_loginId(String loginId);
}
