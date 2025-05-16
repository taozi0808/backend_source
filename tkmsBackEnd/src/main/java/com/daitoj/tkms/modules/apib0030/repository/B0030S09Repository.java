package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TConstrSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 現場情報のリポジトリ */
@Repository
public interface B0030S09Repository extends JpaRepository<TConstrSite, Long> {}
