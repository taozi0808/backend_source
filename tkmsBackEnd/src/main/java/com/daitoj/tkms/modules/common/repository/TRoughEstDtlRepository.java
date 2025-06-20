package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TRoughEstDtl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** 概算明細リポジトリ */
public interface TRoughEstDtlRepository extends JpaRepository<TRoughEstDtl, Long> {

}
