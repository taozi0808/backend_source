package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 顧客情報リポジトリ */
@Repository
public interface MCustomerRepository extends JpaRepository<MCustomer, Long> {}
