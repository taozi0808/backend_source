package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MMajorWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 大工事リポジトリ */
@Repository
public interface MMajorWorkRepository extends JpaRepository<MMajorWork, Long> {}
