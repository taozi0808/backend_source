package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 機能情報リポジトリ */
@Repository
public interface MProgramRepository extends JpaRepository<MProgram, String> {}
