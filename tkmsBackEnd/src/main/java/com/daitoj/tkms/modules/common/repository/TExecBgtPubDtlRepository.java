package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TExecBgtHdr;
import com.daitoj.tkms.domain.TExecBgtPubDtl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 実行予算公共明細リポジトリ. */
@Repository
public interface TExecBgtPubDtlRepository extends JpaRepository<TExecBgtPubDtl, Long> {
  /**
   * 実行予算ヘッダIDより実行予算公共明細リストを取得する.
   *
   * @param execBgtHid 実行予算ヘッダID
   * @return 実行予算公共明細リスト
   */
  List<TExecBgtPubDtl> findAllByExecBgtHid(TExecBgtHdr execBgtHid);
}
