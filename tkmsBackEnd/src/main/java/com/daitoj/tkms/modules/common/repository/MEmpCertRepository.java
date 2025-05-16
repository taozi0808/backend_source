package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MEmpCert;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 従業員資格情報リポジトリ */
@Repository
public interface MEmpCertRepository extends JpaRepository<MEmpCert, Long> {

  /**
   * 従業員資格情報を取得
   *
   * @param id 従業員ID
   * @return
   */
  List<MEmpCert> findByEmp_Id(Long id);
}
