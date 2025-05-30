package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TWfRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** WF申請情報リポジトリ */
@Repository
public interface TWfRequestRepository extends JpaRepository<TWfRequest, Long> {}
