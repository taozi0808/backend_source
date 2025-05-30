package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MPaymentTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 請求条件リポジトリ */
@Repository
public interface MPaymentTermRepository extends JpaRepository<MPaymentTerm, String> {}
