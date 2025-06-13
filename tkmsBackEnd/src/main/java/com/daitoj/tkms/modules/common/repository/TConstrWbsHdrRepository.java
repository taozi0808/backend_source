package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TConstrWbsHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 工事予実ヘッダリポジトリ. */
@Repository
public interface TConstrWbsHdrRepository extends JpaRepository<TConstrWbsHdr, Long> {
}
