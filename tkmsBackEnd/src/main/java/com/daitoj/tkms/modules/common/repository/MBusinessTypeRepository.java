package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MBusinessType;
import org.springframework.data.jpa.repository.JpaRepository;

/** 業務種類情報リポジトリ */
public interface MBusinessTypeRepository extends JpaRepository<MBusinessType, String> {}
