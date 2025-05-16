package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MNumberRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 採番ルールリポジトリ */
@Repository
public interface MNumberRuleRepository extends JpaRepository<MNumberRule, String> {

}
