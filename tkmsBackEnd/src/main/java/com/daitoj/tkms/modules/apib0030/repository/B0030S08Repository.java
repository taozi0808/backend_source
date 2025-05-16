package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TDetailedEstHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 精積算情報のリポジトリ */
@Repository
public interface B0030S08Repository extends JpaRepository<TDetailedEstHdr, Long> {}
