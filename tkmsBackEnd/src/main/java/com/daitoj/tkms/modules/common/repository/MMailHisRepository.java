package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MMailHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** メール送信履歴リポジトリ */
@Repository
public interface MMailHisRepository extends JpaRepository<MMailHis, Long> {}
