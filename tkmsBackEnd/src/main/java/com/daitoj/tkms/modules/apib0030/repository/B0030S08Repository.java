package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TDetailedEstHdr;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 精積算情報のリポジトリ */
@Repository
public interface B0030S08Repository extends JpaRepository<TDetailedEstHdr, Long> {

  /**
   * 精積算情報を取得
   *
   * @param projectCd 案件コード
   * @return 精積算情報
   */
  List<TDetailedEstHdr> findByProjectCd(String projectCd);
}
