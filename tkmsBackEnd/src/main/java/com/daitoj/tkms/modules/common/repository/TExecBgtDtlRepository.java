package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TExecBgtDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 実行予算明細リポジトリ */
@Repository
public interface TExecBgtDtlRepository extends JpaRepository<TExecBgtDtl, Long> {}
