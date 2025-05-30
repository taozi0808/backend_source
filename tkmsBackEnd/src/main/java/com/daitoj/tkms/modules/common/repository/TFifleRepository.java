package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TFifle;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 添付ファイルリポジトリ */
@Repository
public interface TFifleRepository extends JpaRepository<TFifle, UUID> {}
