package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TDetailedEstDtl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 精積算明細情報のリポジトリ */
@Repository
public interface B0030S10Repository extends JpaRepository<TDetailedEstDtl, Long> {

  /**
   * 精積算明細を取得
   *
   * @param id 精積算ヘッダI
   * @return 精積算明細
   */
  List<TDetailedEstDtl> findByDetailedEstHid_Id(Long id);
}
