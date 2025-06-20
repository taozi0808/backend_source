package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TExecBgtDtl;
import com.daitoj.tkms.domain.TExecBgtHdr;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 実行予算明細リポジトリ. */
@Repository
public interface TExecBgtDtlRepository extends JpaRepository<TExecBgtDtl, Long> {
  /**
   * 実行予算ヘッダIDより実行予算明細リストを取得する.
   *
   * @param execBgtHid 実行予算ヘッダID
   * @return 実行予算明細リスト
   */
  List<TExecBgtDtl> findAllByExecBgtHid(TExecBgtHdr execBgtHid);
}
