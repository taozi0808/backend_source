package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TRoughEstHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 概算ヘッダのリポジトリ */
@Repository
public interface B0030S07Repository extends JpaRepository<TRoughEstHdr, Long> {

  /**
   * 概算ヘッダを取得
   *
   * @param projectCd 案件コード
   * @param buildingCd 棟コード
   * @return 概算ヘッダ
   */
  TRoughEstHdr findByProjectCdAndBuildingCd(String projectCd, String buildingCd);
}
