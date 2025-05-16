package com.daitoj.tkms.modules.apib0030.repository;

import com.daitoj.tkms.domain.TRoughEstHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 概算ヘッダのリポジトリ */
@Repository
public interface B0030S07Repository extends JpaRepository<TRoughEstHdr, Long> {}
