package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MCert;
import com.daitoj.tkms.domain.MOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 事業所情報リポジトリ */
@Repository
public interface MOfficeRepository extends JpaRepository<MOffice, String> {
  /**
   * 事業所情報を取得
   *
   * @return
   */
  List<MOffice> findAll();
}
