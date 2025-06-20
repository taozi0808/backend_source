package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TRoughEstHdr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** 概算ヘッダリポジトリ */
public interface TRoughEstHdrRepository extends JpaRepository<TRoughEstHdr, String> {

  /**
   * 概算コード、概算ヘッダを取得
   *
   * @param roughEstCd 概算コード
   *
   * @return
   */
  TRoughEstHdr findByRoughEstCd(String roughEstCd);
}
