package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TEvSimBgt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 出来高シミュレーション予算項目情報リポジトリ */
@Repository
public interface TEvSimBgtRepository extends JpaRepository<TEvSimBgt, Long> {}
