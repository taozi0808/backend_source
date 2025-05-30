package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TExecBgtHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 実行予算ヘッダリポジトリ */
@Repository
public interface TExecBgtHdrRepository extends JpaRepository<TExecBgtHdr, Long> {}
